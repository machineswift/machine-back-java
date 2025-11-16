package com.machine.service.data.tag.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4DataCodeClient;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.data.tag.dto.input.DataTagCategoryCreateInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateParentInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateSortInputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryListOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.service.data.tag.dao.IDataTagCategoryDao;
import com.machine.service.data.tag.dao.IDataTagDao;
import com.machine.service.data.tag.dao.mapper.entity.DataTagCategoryEntity;
import com.machine.service.data.tag.service.IDataTagCategoryService;
import com.machine.starter.redis.cache.data.RedisCacheDataTagCategory;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.machine.sdk.common.constant.CommonDataConstant.TagCategory.DATA_TAG_CATEGORY_ROOT_PARENT_ID;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.LOCK_DATA_TAG_CATEGORY_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.TagCategory.DATA_TAG_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.TagCategory.DATA_TAG_CATEGORY_TREE_KEY;

@Slf4j
@Service
public class DataTagCategoryServiceImpl implements IDataTagCategoryService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisCacheDataTagCategory dataTagCategoryCache;

    @Autowired
    private IDataTagDao tagDao;

    @Autowired
    private IDataTagCategoryDao tagCategoryDao;

    @Autowired
    private IDataLeaf4DataCodeClient leafClient;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataTagCategoryCreateInputDto inputDto) {
        //验证 parentId 是否存在
        DataTagCategoryEntity entityById = tagCategoryDao.getById(inputDto.getParentId());
        if (null == entityById) {
            throw new DataBusinessException("data.tagCategory.service.create.parentIdNotExists", "父ID不存在");
        }

        //验证名称在同一层级是否存在
        DataTagCategoryEntity entityByName = tagCategoryDao.getByParentIdAndName(inputDto.getParentId(), inputDto.getName());
        if (null != entityByName) {
            throw new DataBusinessException("data.tagCategory.service.create.nameAlreadyExists", "名称已经存在");
        }

        DataTagCategoryEntity insertEntity = new DataTagCategoryEntity();
        insertEntity.setParentId(inputDto.getParentId());
        insertEntity.setName(inputDto.getName());
        insertEntity.setType(entityById.getType());
        //生成编码
        insertEntity.setCode(leafClient.tagCategoryCode());
        insertEntity.setSort(inputDto.getSort());
        return tagCategoryDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        DataTagCategoryEntity entity = tagCategoryDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        if (DATA_TAG_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                DATA_TAG_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new DataBusinessException("data.tagCategory.service.delete.rootNode", "根节点不能删除");
        }

        //判断是否有子节点
        if (dataTagCategoryCache.recursionListSubId(entity.getType(), entity.getId()).size() > 1) {
            throw new DataBusinessException("data.tagCategory.service.delete.hasChildrenNode", "有子节点不能删除");
        }

        //获取智能标签分类是否关联标签
        if (CollectionUtil.isNotEmpty(tagDao.selectByCategoryId(entity.getId()))) {
            throw new DataBusinessException("data.tagCategory.service.delete.associationTag", "关联标签不能删除");
        }

        return tagCategoryDao.delete(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataTagCategoryUpdateInputDto inputDto) {
        DataTagCategoryEntity entity = tagCategoryDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (DATA_TAG_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                DATA_TAG_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new DataBusinessException("data.tagCategory.service.update.rootNode", "根节点不能修改");
        }

        //验证名称在同一层级是否存在
        DataTagCategoryEntity entityByName = tagCategoryDao.getByParentIdAndName(entity.getParentId(), inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new DataBusinessException("data.tagCategory.service.update.nameAlreadyExists", "名称已经存在");
        }

        DataTagCategoryEntity updateEntity = new DataTagCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setName(inputDto.getName());
        updateEntity.setSort(inputDto.getSort());
        return tagCategoryDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSort(DataTagCategoryUpdateSortInputDto inputDto) {
        DataTagCategoryEntity dbEntity = tagCategoryDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }

        if (dbEntity.getSort().equals(inputDto.getSort())) {
            //相同直接返回
            return 0;
        }

        DataTagCategoryEntity updateEntity = new DataTagCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setSort(inputDto.getSort());
        return tagCategoryDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(DataTagCategoryUpdateParentInputDto inputDto) {
        DataTagCategoryEntity dbEntity = tagCategoryDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (inputDto.getParentId().equals(dbEntity.getParentId())) {
            //相同直接返回
            return 0;
        }

        if (DATA_TAG_CATEGORY_ROOT_PARENT_ID.equals(dbEntity.getParentId()) ||
                DATA_TAG_CATEGORY_ROOT_PARENT_ID.equals(dbEntity.getId())) {
            throw new DataBusinessException("data.tagCategory.service.updateParent.rootNode", "根节点不能修改");
        }

        //验证名称在同一层级是否存在
        DataTagCategoryEntity entityByName = tagCategoryDao.getByParentIdAndName(inputDto.getParentId(), dbEntity.getName());
        if (null != entityByName && !entityByName.getId().equals(dbEntity.getId())) {
            throw new DataBusinessException("data.tagCategory.service.updateParent.nameAlreadyExists", "名称已经存在");
        }

        //验证父部门是否存在
        DataTagCategoryEntity parentEntity = tagCategoryDao.getById(inputDto.getParentId());
        if (null == parentEntity) {
            throw new DataBusinessException("data.tagCategory.service.updateParent.parentNotExists", "父节点不存在");
        }

        //验证父Id是否在当前节点下面
        Set<String> recursionIdSet = dataTagCategoryCache.recursionListSubId(dbEntity.getType(), inputDto.getId());
        if (recursionIdSet.contains(inputDto.getParentId())) {
            throw new DataBusinessException("data.tagCategory.service.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }

        DataTagCategoryEntity updateEntity = new DataTagCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setParentId(inputDto.getParentId());
        return tagCategoryDao.update(updateEntity);
    }

    @Override
    public DataTagCategoryDetailOutputDto detail(IdRequest request) {
        DataTagCategoryEntity entity = tagCategoryDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataTagCategoryDetailOutputDto.class);
    }

    @Override
    public List<DataTagCategoryListOutputDto> listAllByType(ProfileSubjectTypeEnum type) {
        List<DataTagCategoryEntity> entityList = tagCategoryDao.listAllByType(type);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataTagCategoryListOutputDto.class);
    }

    @Override
    public DataTagCategoryTreeSimpleOutputDto treeAllSimple(ProfileSubjectTypeEnum type) {
        String typeName = type.getName();
        //获取树的动态key
        String keyCode = customerRedisCommands.get(DATA_TAG_CATEGORY_TREE_KEY + typeName);

        //如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_TAG_CATEGORY_TREE_DATA + typeName + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return JSONUtil.toBean(treeJson, DataTagCategoryTreeSimpleOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_DATA_TAG_CATEGORY_TREE + typeName);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(DATA_TAG_CATEGORY_TREE_KEY + typeName);
            if (StrUtil.isNotBlank(keyCode)) {
                String treeJson = customerRedisCommands.get(DATA_TAG_CATEGORY_TREE_DATA + typeName + keyCode);
                if (StrUtil.isNotBlank(treeJson)) {
                    return JSONUtil.toBean(treeJson, DataTagCategoryTreeSimpleOutputDto.class);
                }
            }

            //重新生成树的动态key
            keyCode = leaf4RedisClient.dataTagCategoryTree(type);
            customerRedisCommands.set(DATA_TAG_CATEGORY_TREE_KEY + typeName, keyCode, 24 * 60 * 60);

            //查询DB组装树
            List<DataTagCategoryEntity> entityList = tagCategoryDao.listAllByType(type);
            if (CollectionUtil.isEmpty(entityList)) {
                return null;
            }
            List<DataTagCategoryTreeSimpleOutputDto> outputDtoList =
                    JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataTagCategoryTreeSimpleOutputDto.class);
            DataTagCategoryTreeSimpleOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            //Tree 数据缓存到redis
            customerRedisCommands.set(
                    DATA_TAG_CATEGORY_TREE_DATA + typeName + keyCode,
                    JSONUtil.toJsonStr(treeOutputDto),
                    24 * 60 * 60 + 60);

            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }

}