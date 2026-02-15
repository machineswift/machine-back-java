package com.machine.service.data.file.material.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4DataCodeClient;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.data.file.material.dto.input.DataMaterialCategoryCreateInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialCategoryUpdateInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialCategoryUpdateParentInputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialCategoryDetailOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialCategoryListOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.service.data.file.material.dao.IDataMaterialCategoryDao;
import com.machine.service.data.file.material.dao.IDataMaterialCategoryRelationDao;
import com.machine.service.data.file.material.dao.mapper.entity.DataMaterialCategoryEntity;
import com.machine.service.data.file.material.service.IDataMaterialCategoryService;
import com.machine.starter.redis.cache.data.RedisCacheDataMaterialCategory;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.machine.sdk.common.constant.CommonDataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_VIRTUAL_NODE;
import static com.machine.sdk.common.constant.CommonDataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_ROOT_PARENT_ID;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.LOCK_DATA_MATERIAL_CATEGORY_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_TREE_KEY;

@Slf4j
@Service
public class DataMaterialCategoryServiceImpl implements IDataMaterialCategoryService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisCacheDataMaterialCategory materialCategoryCache;

    @Autowired
    private IDataMaterialCategoryDao materialCategoryDao;

    @Autowired
    private IDataMaterialCategoryRelationDao materialCategoryRelationDao;

    @Autowired
    private IDataLeaf4DataCodeClient leaf4DataCodeClient;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataMaterialCategoryCreateInputDto inputDto) {

        String parentId = inputDto.getParentId();

        if (DATA_MATERIAL_CATEGORY_VIRTUAL_NODE.equals(parentId)) {
            throw new DataBusinessException("data.materialCategory.service.create.virtualNode", "不能选择【未分类】");
        }

        //验证 parentId 是否存在
        DataMaterialCategoryEntity entityById = materialCategoryDao.getById(parentId);
        if (null == entityById) {
            throw new DataBusinessException("data.materialCategory.service.create.parentIdNotExists", "父ID不存在");
        }

        //验证名称在同一层级是否存在
        DataMaterialCategoryEntity entityByName = materialCategoryDao.getByParentIdAndName(parentId, inputDto.getName());
        if (null != entityByName) {
            throw new DataBusinessException("data.materialCategory.service.create.nameAlreadyExists", "名称已经存在");
        }

        //验证父级分类是否棒定了素材
        Long count = materialCategoryRelationDao.selectCountByCategoryId(parentId);
        if (count.intValue() > 0) {
            throw new DataBusinessException("data.materialCategory.service.create.parentHasBindMaterial",
                    "已关联素材，不支持再创建子类，请重选素材分类后重试");
        }

        DataMaterialCategoryEntity insertEntity = new DataMaterialCategoryEntity();
        insertEntity.setParentId(parentId);
        insertEntity.setName(inputDto.getName());

        //生成编码
        insertEntity.setCode(leaf4DataCodeClient.materialCategoryCode());
        insertEntity.setSort(inputDto.getSort());
        return materialCategoryDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        DataMaterialCategoryEntity entity = materialCategoryDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        if (DATA_MATERIAL_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                DATA_MATERIAL_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new DataBusinessException("data.materialCategory.service.delete.rootMaterialCategory", "根分类不能删除");
        }

        //递归获取所有的子节点
        Set<String> idSet = new HashSet<>();
        idSet.add(entity.getId());
        idSet.addAll(materialCategoryCache.recursionListSubId(entity.getId()));

        //是否关联的素材信息
        Long count = materialCategoryRelationDao.selectCountByCategoryIdSet(idSet);
        if (count.intValue() > 0) {
            throw new DataBusinessException("data.materialCategory.service.delete.associationMaterial",
                    "此分类存在素材不能被删除，请先移除或删除所有素材再试");
        }

        int result = 0;
        for (String id : idSet) {
            result = result + materialCategoryDao.delete(id);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataMaterialCategoryUpdateInputDto inputDto) {
        DataMaterialCategoryEntity entity = materialCategoryDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (DATA_MATERIAL_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                DATA_MATERIAL_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new DataBusinessException("data.materialCategory.service.update.rootMaterialCategory", "根分类不能修改");
        }

        //验证名称在同一层级是否存在
        DataMaterialCategoryEntity entityByName = materialCategoryDao.getByParentIdAndName(entity.getParentId(), inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new DataBusinessException("data.materialCategory.service.update.nameAlreadyExists", "分类名称已经存在");
        }

        DataMaterialCategoryEntity updateEntity = new DataMaterialCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setName(inputDto.getName());
        updateEntity.setSort(inputDto.getSort());
        return materialCategoryDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(DataMaterialCategoryUpdateParentInputDto inputDto) {
        if (inputDto.getParentId().endsWith(DATA_MATERIAL_CATEGORY_VIRTUAL_NODE)) {
            throw new DataBusinessException("data.materialCategory.service.updateParent.virtualNode", "不能选择未分类节点");
        }

        DataMaterialCategoryEntity entity = materialCategoryDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (DATA_MATERIAL_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                DATA_MATERIAL_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new DataBusinessException("data.materialCategory.service.updateParent.rootMaterialCategory", "根分类不能修改");
        }

        //验证名称在同一层级是否存在
        DataMaterialCategoryEntity entityByName = materialCategoryDao.getByParentIdAndName(inputDto.getParentId(), entity.getName());
        if (null != entityByName) {
            throw new DataBusinessException("data.materialCategory.service.updateParent.nameAlreadyExists", "分类名称已经存在");
        }

        //验证父节点是否存在
        DataMaterialCategoryEntity parentEntity = materialCategoryDao.getById(inputDto.getId());
        if (null == parentEntity) {
            throw new DataBusinessException("data.materialCategory.service.updateParent.parentNotExists", "父分类不存在");
        }

        //验证父Id是否在当前节点下面
        Set<String> recursionIdSet = materialCategoryCache.recursionListSubId(inputDto.getId());
        if (recursionIdSet.contains(inputDto.getParentId())) {
            throw new DataBusinessException("data.materialCategory.service.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }

        DataMaterialCategoryEntity updateEntity = new DataMaterialCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setParentId(inputDto.getParentId());
        return materialCategoryDao.update(updateEntity);
    }

    @Override
    public DataMaterialCategoryDetailOutputDto detail(IdRequest request) {
        DataMaterialCategoryEntity entity = materialCategoryDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialCategoryDetailOutputDto.class);
    }

    @Override
    public List<DataMaterialCategoryListOutputDto> listAll() {
        List<DataMaterialCategoryEntity> entityList = materialCategoryDao.listAll();
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataMaterialCategoryListOutputDto.class);
    }

    @Override
    public DataMaterialCategoryTreeSimpleOutputDto treeAllSimple() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(DATA_MATERIAL_CATEGORY_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_MATERIAL_CATEGORY_TREE_DATA + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return JSONUtil.toBean(treeJson, DataMaterialCategoryTreeSimpleOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_DATA_MATERIAL_CATEGORY_TREE);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(DATA_MATERIAL_CATEGORY_TREE_KEY);
            if (StrUtil.isNotBlank(keyCode)) {
                String treeJson = customerRedisCommands.get(DATA_MATERIAL_CATEGORY_TREE_DATA + keyCode);
                if (StrUtil.isNotBlank(treeJson)) {
                    return JSONUtil.toBean(treeJson, DataMaterialCategoryTreeSimpleOutputDto.class);
                }
            }

            //重新生成树的动态key
            keyCode = leaf4RedisClient.dataMaterialCategoryTree();
            customerRedisCommands.set(DATA_MATERIAL_CATEGORY_TREE_KEY, keyCode, 24 * 60 * 60);

            //查询DB组装树
            List<DataMaterialCategoryEntity> entityList = materialCategoryDao.listAll();
            if (CollectionUtil.isEmpty(entityList)) {
                return null;
            }
            List<DataMaterialCategoryTreeSimpleOutputDto> outputDtoList =
                    JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataMaterialCategoryTreeSimpleOutputDto.class);
            DataMaterialCategoryTreeSimpleOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            //Tree 数据缓存到redis
            customerRedisCommands.set(
                    DATA_MATERIAL_CATEGORY_TREE_DATA + keyCode,
                    JSONUtil.toJsonStr(treeOutputDto),
                    24 * 60 * 60 + 60);

            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }
}
