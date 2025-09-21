package com.machine.service.data.attachment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryCreateInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryUpdateInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryUpdateParentInputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryDetailOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryListOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryTreeSimpleOutputDto;
import com.machine.client.data.leaf.IDataLeaf4DataCodeClient;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.service.data.attachment.dao.IDataAttachmentCategoryDao;
import com.machine.service.data.attachment.dao.IDataAttachmentCategoryRelationDao;
import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentCategoryEntity;
import com.machine.service.data.attachment.service.IDataAttachmentCategoryService;
import com.machine.starter.redis.cache.RedisCacheDataAttachmentCategory;
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

import static com.machine.sdk.common.constant.CommonDataConstant.AttachmentCategory.DATA_ATTACHMENT_CATEGORY_ROOT_PARENT_ID;
import static com.machine.sdk.common.constant.CommonDataConstant.AttachmentCategory.DATA_ATTACHMENT_CATEGORY_VIRTUAL_NODE;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.LOCK_DATA_ATTACHMENT_CATEGORY_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.AttachmentCategory.DATA_ATTACHMENT_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.AttachmentCategory.DATA_ATTACHMENT_CATEGORY_TREE_KEY;

@Slf4j
@Service
public class DataAttachmentCategoryServiceImpl implements IDataAttachmentCategoryService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisCacheDataAttachmentCategory attachmentCategoryCache;

    @Autowired
    private IDataAttachmentCategoryDao attachmentCategoryDao;

    @Autowired
    private IDataAttachmentCategoryRelationDao attachmentCategoryRelationDao;

    @Autowired
    private IDataLeaf4DataCodeClient leaf4DataCodeClient;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataAttachmentCategoryCreateInputDto inputDto) {

        String parentId = inputDto.getParentId();

        if (DATA_ATTACHMENT_CATEGORY_VIRTUAL_NODE.equals(parentId)) {
            throw new DataBusinessException("data.attachmentCategory.service.create.virtualNode", "不能选择【未分类】");
        }

        //验证 parentId 是否存在
        DataAttachmentCategoryEntity entityById = attachmentCategoryDao.getById(parentId);
        if (null == entityById) {
            throw new DataBusinessException("data.attachmentCategory.service.create.parentIdNotExists", "父ID不存在");
        }

        //验证名称在同一层级是否存在
        DataAttachmentCategoryEntity entityByName = attachmentCategoryDao.getByParentIdAndName(parentId, inputDto.getName());
        if (null != entityByName) {
            throw new DataBusinessException("data.attachmentCategory.service.create.nameAlreadyExists", "名称已经存在");
        }

        //验证父级分类是否棒定了附件
        Long count = attachmentCategoryRelationDao.selectCountByCategoryId(parentId);
        if (count.intValue() > 0) {
            throw new DataBusinessException("data.attachmentCategory.service.create.parentHasBindAttachment",
                    "已关联附件，不支持再创建子类，请重选附件分类后重试");
        }

        DataAttachmentCategoryEntity insertEntity = new DataAttachmentCategoryEntity();
        insertEntity.setParentId(parentId);
        insertEntity.setName(inputDto.getName());

        //生成编码
        insertEntity.setCode(leaf4DataCodeClient.attachmentCategoryCode());
        insertEntity.setSort(inputDto.getSort());
        return attachmentCategoryDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        DataAttachmentCategoryEntity entity = attachmentCategoryDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        if (DATA_ATTACHMENT_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                DATA_ATTACHMENT_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new DataBusinessException("data.attachmentCategory.service.delete.rootAttachmentCategory", "根分类不能删除");
        }

        //递归获取所有的子节点
        Set<String> idSet = new HashSet<>();
        idSet.add(entity.getId());
        idSet.addAll(attachmentCategoryCache.recursionListSubId(entity.getId()));

        //是否关联的附件信息
        Long count = attachmentCategoryRelationDao.selectCountByCategoryIdSet(idSet);
        if (count.intValue() > 0) {
            throw new DataBusinessException("data.attachmentCategory.service.delete.associationAttachment",
                    "此分类存在附件不能被删除，请先移除或删除所有附件再试");
        }

        int result = 0;
        for (String id : idSet) {
            result = result + attachmentCategoryDao.delete(id);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataAttachmentCategoryUpdateInputDto inputDto) {
        DataAttachmentCategoryEntity entity = attachmentCategoryDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (DATA_ATTACHMENT_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                DATA_ATTACHMENT_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new DataBusinessException("data.attachmentCategory.service.update.rootAttachmentCategory", "根分类不能修改");
        }

        //验证名称在同一层级是否存在
        DataAttachmentCategoryEntity entityByName = attachmentCategoryDao.getByParentIdAndName(entity.getParentId(), inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new DataBusinessException("data.attachmentCategory.service.update.nameAlreadyExists", "分类名称已经存在");
        }

        DataAttachmentCategoryEntity updateEntity = new DataAttachmentCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setName(inputDto.getName());
        updateEntity.setSort(inputDto.getSort());
        return attachmentCategoryDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(DataAttachmentCategoryUpdateParentInputDto inputDto) {
        if (inputDto.getParentId().endsWith(DATA_ATTACHMENT_CATEGORY_VIRTUAL_NODE)) {
            throw new DataBusinessException("data.attachmentCategory.service.updateParent.virtualNode", "不能选择未分类节点");
        }

        DataAttachmentCategoryEntity entity = attachmentCategoryDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (DATA_ATTACHMENT_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                DATA_ATTACHMENT_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new DataBusinessException("data.attachmentCategory.service.updateParent.rootAttachmentCategory", "根分类不能修改");
        }

        //验证名称在同一层级是否存在
        DataAttachmentCategoryEntity entityByName = attachmentCategoryDao.getByParentIdAndName(inputDto.getParentId(), entity.getName());
        if (null != entityByName) {
            throw new DataBusinessException("data.attachmentCategory.service.updateParent.nameAlreadyExists", "分类名称已经存在");
        }

        //验证父节点是否存在
        DataAttachmentCategoryEntity parentEntity = attachmentCategoryDao.getById(inputDto.getId());
        if (null == parentEntity) {
            throw new DataBusinessException("data.attachmentCategory.service.updateParent.parentNotExists", "父分类不存在");
        }

        //验证父Id是否在当前节点下面
        Set<String> recursionIdSet = attachmentCategoryCache.recursionListSubId(inputDto.getId());
        if (recursionIdSet.contains(inputDto.getParentId())) {
            throw new DataBusinessException("data.attachmentCategory.service.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }

        DataAttachmentCategoryEntity updateEntity = new DataAttachmentCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setParentId(inputDto.getParentId());
        return attachmentCategoryDao.update(updateEntity);
    }

    @Override
    public DataAttachmentCategoryDetailOutputDto detail(IdRequest request) {
        DataAttachmentCategoryEntity entity = attachmentCategoryDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataAttachmentCategoryDetailOutputDto.class);
    }

    @Override
    public List<DataAttachmentCategoryListOutputDto> listAll() {
        List<DataAttachmentCategoryEntity> entityList = attachmentCategoryDao.listAll();
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataAttachmentCategoryListOutputDto.class);
    }

    @Override
    public DataAttachmentCategoryTreeSimpleOutputDto treeAllSimple() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(DATA_ATTACHMENT_CATEGORY_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_ATTACHMENT_CATEGORY_TREE_DATA + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return JSONUtil.toBean(treeJson, DataAttachmentCategoryTreeSimpleOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_DATA_ATTACHMENT_CATEGORY_TREE);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(DATA_ATTACHMENT_CATEGORY_TREE_KEY);
            if (StrUtil.isNotBlank(keyCode)) {
                String treeJson = customerRedisCommands.get(DATA_ATTACHMENT_CATEGORY_TREE_DATA + keyCode);
                if (StrUtil.isNotBlank(treeJson)) {
                    return JSONUtil.toBean(treeJson, DataAttachmentCategoryTreeSimpleOutputDto.class);
                }
            }

            //重新生成树的动态key
            keyCode = leaf4RedisClient.dataAttachmentCategoryTree();
            customerRedisCommands.set(DATA_ATTACHMENT_CATEGORY_TREE_KEY, keyCode, 24 * 60 * 60);

            //查询DB组装树
            List<DataAttachmentCategoryEntity> entityList = attachmentCategoryDao.listAll();
            if (CollectionUtil.isEmpty(entityList)) {
                return null;
            }
            List<DataAttachmentCategoryTreeSimpleOutputDto> outputDtoList =
                    JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataAttachmentCategoryTreeSimpleOutputDto.class);
            DataAttachmentCategoryTreeSimpleOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            //Tree 数据缓存到redis
            customerRedisCommands.set(
                    DATA_ATTACHMENT_CATEGORY_TREE_DATA + keyCode,
                    JSONUtil.toJsonStr(treeOutputDto),
                    24 * 60 * 60 + 60);

            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }
}
