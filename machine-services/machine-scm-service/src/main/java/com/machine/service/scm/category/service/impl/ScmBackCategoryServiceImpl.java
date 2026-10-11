package com.machine.service.scm.category.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.data.leaf.IDataLeaf4ScmCodeClient;
import com.machine.client.scm.category.dto.ScmBackCategoryAttributeGroupItemDto;
import com.machine.client.scm.category.dto.ScmBackCategoryAttributeItemDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateAttributeGroupInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateAttributeInputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryListOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.exception.scm.ScmBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.service.scm.category.dao.IScmBackCategoryDao;
import com.machine.service.scm.category.dao.IScmBackCategoryFrontRelationDao;
import com.machine.service.scm.category.dao.mapper.entity.ScmBackCategoryEntity;
import com.machine.service.scm.category.service.IScmBackCategoryService;
import com.machine.service.scm.attribute.dao.IScmBackCategoryAttributeGroupRelationDao;
import com.machine.service.scm.attribute.dao.IScmBackCategoryAttributeRelationDao;
import com.machine.service.scm.attribute.dao.IScmAttributeDao;
import com.machine.service.scm.attribute.dao.IScmAttributeGroupDao;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmBackCategoryAttributeGroupRelationEntity;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmBackCategoryAttributeRelationEntity;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeEntity;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupEntity;
import com.machine.starter.redis.cache.scm.RedisScmBackCategoryCache;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonConstant.EMPTY_OBJECT;
import static com.machine.sdk.base.constant.CommonScmConstant.BackCategory.SCM_BACK_CATEGORY_ROOT_PARENT_ID;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Scm.LOCK_SCM_BACK_CATEGORY_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.BackCategory.SCM_BACK_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.BackCategory.SCM_BACK_CATEGORY_TREE_KEY;

@Slf4j
@Service
public class ScmBackCategoryServiceImpl implements IScmBackCategoryService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisScmBackCategoryCache cacheScmBackCategory;

    @Autowired
    private IDataLeaf4ScmCodeClient leaf4ScmCodeClient;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Autowired
    private IScmBackCategoryDao backCategoryDao;

    @Autowired
    private IScmBackCategoryFrontRelationDao backCategoryFrontRelationDao;

    @Autowired
    private IScmBackCategoryAttributeGroupRelationDao backCategoryAttributeGroupRelationDao;

    @Autowired
    private IScmBackCategoryAttributeRelationDao backCategoryAttributeRelationDao;

    @Autowired
    private IScmAttributeGroupDao attributeGroupDao;

    @Autowired
    private IScmAttributeDao attributeDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmBackCategoryCreateInputDto inputDto) {
        // 验证 parentId 是否存在
        ScmBackCategoryEntity entityById = backCategoryDao.getById(inputDto.getParentId());
        if (null == entityById) {
            throw new ScmBusinessException("scm.backCategory.service.create.parentIdNotExists", "父ID不存在");
        }

        // 验证名称在同一层级是否存在
        long countByName = backCategoryDao.countByNameAndParentId(inputDto.getParentId(), inputDto.getName());
        if (countByName > 0) {
            throw new ScmBusinessException("scm.backCategory.service.create.nameAlreadyExists", "名称已经存在");
        }

        if (backCategoryFrontRelationDao.countByBackCategoryIdSet(Set.of(inputDto.getParentId())) > 0) {
            throw new ScmBusinessException("scm.backCategory.service.create.parentHasFrontCategory",
                    "父节点已关联前台分类，不能再新增子分类");
        }

        ScmBackCategoryEntity insertEntity = new ScmBackCategoryEntity();
        insertEntity.setParentId(inputDto.getParentId());
        insertEntity.setName(inputDto.getName());
        // 生成编码
        insertEntity.setCode(leaf4ScmCodeClient.backCategoryCode());
        insertEntity.setSort(inputDto.getSort());
        return backCategoryDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        String id = request.getId();
        ScmBackCategoryEntity entity = backCategoryDao.getById(id);
        if (null == entity) {
            return 0;
        }

        if (SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.delete.rootNode", "根节点不能删除");
        }

        // 判断是否有子节点
        Set<String> recursionSubIdSet = cacheScmBackCategory.recursionSubId(id);
        if (recursionSubIdSet.size() > 1) {
            throw new ScmBusinessException("scm.backCategory.service.delete.hasChildrenNode", "有子节点不能删除");
        }

        // 判断是否关联前端分类
        long countByBackIdSet = backCategoryFrontRelationDao.countByBackCategoryIdSet(recursionSubIdSet);
        if (countByBackIdSet > 0) {
            throw new ScmBusinessException("scm.backCategory.service.delete.associationFrontCategory", "关联前台分类不能删除");
        }

        // 判断是否关联属性分组
        if (backCategoryAttributeGroupRelationDao.countByBackCategoryIdSet(recursionSubIdSet) > 0) {
            throw new ScmBusinessException("scm.backCategory.service.delete.associationAttributeGroup", "关联属性分组不能删除");
        }

        // 判断是否关联属性
        if (backCategoryAttributeRelationDao.countByBackCategoryIdSet(recursionSubIdSet) > 0) {
            throw new ScmBusinessException("scm.backCategory.service.delete.associationAttribute", "关联属性不能删除");
        }

        return backCategoryDao.deleteById(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmBackCategoryUpdateInputDto inputDto) {
        ScmBackCategoryEntity entity = backCategoryDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.update.rootNode", "根节点不能修改");
        }

        // 验证名称在同一层级是否存在
        ScmBackCategoryEntity entityByName = backCategoryDao.getByNameAndParentId(entity.getParentId(),
                inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.update.nameAlreadyExists", "名称已经存在");
        }

        ScmBackCategoryEntity updateEntity = new ScmBackCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setName(inputDto.getName());
        updateEntity.setSort(inputDto.getSort());
        return backCategoryDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(ScmBackCategoryUpdateParentInputDto inputDto) {
        ScmBackCategoryEntity dbEntity = backCategoryDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (inputDto.getParentId().equals(dbEntity.getParentId())) {
            // 相同直接返回
            return 0;
        }

        if (SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(dbEntity.getParentId()) ||
                SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(dbEntity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.updateParent.rootNode", "根节点不能修改");
        }

        // 验证名称在同一层级是否存在
        ScmBackCategoryEntity entityByName = backCategoryDao.getByNameAndParentId(inputDto.getParentId(),
                dbEntity.getName());
        if (null != entityByName && !entityByName.getId().equals(dbEntity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.updateParent.nameAlreadyExists", "名称已经存在");
        }

        // 验证父节点是否存在
        ScmBackCategoryEntity parentEntity = backCategoryDao.getById(inputDto.getParentId());
        if (null == parentEntity) {
            throw new ScmBusinessException("scm.backCategory.service.updateParent.parentNotExists", "父节点不存在");
        }

        if (backCategoryFrontRelationDao.countByBackCategoryIdSet(Set.of(inputDto.getParentId())) > 0) {
            throw new ScmBusinessException("scm.backCategory.service.updateParent.parentHasFrontCategory",
                    "目标父节点已关联前台分类，不能作为父节点");
        }

        // 验证父Id是否在当前节点下面
        Set<String> recursionSubIdSet = cacheScmBackCategory.recursionSubId(inputDto.getId());
        if (recursionSubIdSet.contains(inputDto.getParentId())) {
            throw new ScmBusinessException("scm.backCategory.service.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }

        // 分组在分类全路径中唯一
        Set<String> moveGroupIdSet = new HashSet<>(
                backCategoryAttributeGroupRelationDao.listGroupIdByBackCategoryIdSet(recursionSubIdSet));
        if (CollectionUtil.isNotEmpty(moveGroupIdSet)) {
            ScmBackCategoryTreeSimpleOutputDto tree = cacheScmBackCategory.treeSimple();
            Set<String> parentChainIdSet = TreeUtil.getParentNodeList(tree, inputDto.getParentId()).stream()
                    .map(ScmBackCategoryTreeSimpleOutputDto::getId)
                    .collect(Collectors.toSet());
            moveGroupIdSet.retainAll(
                    backCategoryAttributeGroupRelationDao.listGroupIdByBackCategoryIdSet(parentChainIdSet));
            if (CollectionUtil.isNotEmpty(moveGroupIdSet)) {
                throw new ScmBusinessException("scm.backCategory.service.updateParent.parentHasAttributeGroup",
                        "分组在分类全路径中不能重复");
            }
        }
        return backCategoryDao.updateParentId(inputDto.getId(), inputDto.getParentId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAttributeGroup(ScmBackCategoryUpdateAttributeGroupInputDto inputDto) {
        ScmBackCategoryEntity dbEntity = backCategoryDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(dbEntity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.updateAttributeGroup.rootNode", "根节点不能修改");
        }

        // 按分组ID去重
        Map<String, ScmBackCategoryAttributeGroupItemDto> itemMap = new LinkedHashMap<>();
        for (ScmBackCategoryAttributeGroupItemDto item : inputDto.getAttributeGroupItemList()) {
            if (null != itemMap.put(item.getGroupId(), item)) {
                throw new ScmBusinessException("scm.backCategory.service.updateAttributeGroup.groupRepeat",
                        "属性分组重复");
            }
        }

        if (CollectionUtil.isNotEmpty(itemMap)) {
            // 验证分组是否存在
            List<ScmAttributeGroupEntity> groupEntityList = attributeGroupDao.listByIdSet(itemMap.keySet());
            if (groupEntityList.size() != itemMap.size()) {
                throw new ScmBusinessException("scm.backCategory.service.updateAttributeGroup.groupNotExists",
                        "属性分组不存在");
            }

            // 分组在分类全路径中唯一
            Set<String> relationBackCategoryIdSet =
                    backCategoryAttributeGroupRelationDao.listByGroupIdSet(itemMap.keySet()).stream()
                            .map(ScmBackCategoryAttributeGroupRelationEntity::getBackCategoryId)
                            .collect(Collectors.toSet());
            if (CollectionUtil.isNotEmpty(relationBackCategoryIdSet)) {
                Set<String> forbidIdSet = new HashSet<>(cacheScmBackCategory.recursionSubId(inputDto.getId()));
                forbidIdSet.addAll(TreeUtil.getParentNodeList(cacheScmBackCategory.treeSimple(), inputDto.getId())
                        .stream()
                        .map(ScmBackCategoryTreeSimpleOutputDto::getId)
                        .collect(Collectors.toSet()));
                forbidIdSet.remove(inputDto.getId());
                forbidIdSet.retainAll(relationBackCategoryIdSet);
                if (CollectionUtil.isNotEmpty(forbidIdSet)) {
                    throw new ScmBusinessException("scm.backCategory.service.updateAttributeGroup.groupHasInPath",
                            "分组在分类全路径中不能重复");
                }
            }
        }

        // 全量替换
        backCategoryAttributeGroupRelationDao.deleteByBackCategoryId(inputDto.getId());
        if (CollectionUtil.isNotEmpty(itemMap)) {
            List<ScmBackCategoryAttributeGroupRelationEntity> entityList = new ArrayList<>(itemMap.size());
            for (ScmBackCategoryAttributeGroupItemDto item : itemMap.values()) {
                ScmBackCategoryAttributeGroupRelationEntity relationEntity =
                        new ScmBackCategoryAttributeGroupRelationEntity();
                relationEntity.setGroupId(item.getGroupId());
                relationEntity.setBackCategoryId(inputDto.getId());
                relationEntity.setRequiredFlag(item.getRequiredFlag());
                relationEntity.setDisplayFlag(item.getDisplayFlag());
                relationEntity.setSearchableFlag(item.getSearchableFlag());
                relationEntity.setSort(item.getSort());
                relationEntity.setFeatures(item.getFeatures());
                entityList.add(relationEntity);
            }
            backCategoryAttributeGroupRelationDao.batchInsert(entityList);
        }
        return itemMap.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAttribute(ScmBackCategoryUpdateAttributeInputDto inputDto) {
        ScmBackCategoryEntity dbEntity = backCategoryDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(dbEntity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.updateAttribute.rootNode", "根节点不能修改");
        }

        // 按属性ID去重
        Map<String, ScmBackCategoryAttributeItemDto> itemMap = new LinkedHashMap<>();
        for (ScmBackCategoryAttributeItemDto item : inputDto.getAttributeItemList()) {
            if (null != itemMap.put(item.getAttributeId(), item)) {
                throw new ScmBusinessException("scm.backCategory.service.updateAttribute.attributeRepeat",
                        "属性重复");
            }
        }

        if (CollectionUtil.isNotEmpty(itemMap)) {
            // 验证属性是否存在
            List<ScmAttributeEntity> attributeEntityList = attributeDao.listByIdSet(itemMap.keySet());
            if (attributeEntityList.size() != itemMap.size()) {
                throw new ScmBusinessException("scm.backCategory.service.updateAttribute.attributeNotExists",
                        "属性不存在");
            }
        }

        // 全量替换
        backCategoryAttributeRelationDao.deleteByBackCategoryId(inputDto.getId());
        if (CollectionUtil.isNotEmpty(itemMap)) {
            List<ScmBackCategoryAttributeRelationEntity> entityList = new ArrayList<>(itemMap.size());
            for (ScmBackCategoryAttributeItemDto item : itemMap.values()) {
                ScmBackCategoryAttributeRelationEntity relationEntity =
                        new ScmBackCategoryAttributeRelationEntity();
                relationEntity.setAttributeId(item.getAttributeId());
                relationEntity.setBackCategoryId(inputDto.getId());
                relationEntity.setRequiredFlag(item.getRequiredFlag());
                relationEntity.setDisplayFlag(item.getDisplayFlag());
                relationEntity.setSearchableFlag(item.getSearchableFlag());
                relationEntity.setSort(item.getSort());
                relationEntity.setFeatures(item.getFeatures());
                entityList.add(relationEntity);
            }
            backCategoryAttributeRelationDao.batchInsert(entityList);
        }
        return itemMap.size();
    }

    @Override
    public ScmBackCategoryDetailOutputDto getById(IdRequest request) {
        ScmBackCategoryEntity entity = backCategoryDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmBackCategoryDetailOutputDto.class);
    }

    @Override
    public List<ScmBackCategoryListOutputDto> listAll() {
        List<ScmBackCategoryEntity> entityList = backCategoryDao.listAll();
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), ScmBackCategoryListOutputDto.class);
    }

    @Override
    public Tuples.Tuple2<String, ScmBackCategoryTreeSimpleOutputDto> treeAllSimple() {
        // 获取树的动态key
        String keyCode = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_KEY);

        // 如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_DATA + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return Tuples.of(keyCode, JSONUtil.toBean(treeJson, ScmBackCategoryTreeSimpleOutputDto.class));
            }
        }

        // 缓存击穿
        RLock lock = redissonClient.getLock(LOCK_SCM_BACK_CATEGORY_TREE);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_KEY);
            if (StrUtil.isNotBlank(keyCode)) {
                String treeJson = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_DATA + keyCode);
                if (StrUtil.isNotBlank(treeJson)) {
                    return Tuples.of(keyCode, JSONUtil.toBean(treeJson, ScmBackCategoryTreeSimpleOutputDto.class));
                }
            }

            // 重新生成树的动态key
            keyCode = leaf4RedisClient.scmBackCategoryTree();
            customerRedisCommands.setex(SCM_BACK_CATEGORY_TREE_KEY, keyCode, 24 * 60 * 60);

            // 查询DB组装树
            List<ScmBackCategoryEntity> entityList = backCategoryDao.listAll();
            if (CollectionUtil.isEmpty(entityList)) {
                // Tree 数据缓存到redis
                customerRedisCommands.setex(SCM_BACK_CATEGORY_TREE_DATA + keyCode, EMPTY_OBJECT, 24 * 60 * 60 + 60);
                return Tuples.of(keyCode, new ScmBackCategoryTreeSimpleOutputDto());
            }

            List<ScmBackCategoryTreeSimpleOutputDto> outputDtoList = new ArrayList<>();
            for (ScmBackCategoryEntity entity : entityList) {
                ScmBackCategoryTreeSimpleOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity),
                        ScmBackCategoryTreeSimpleOutputDto.class);
                outputDtoList.add(outputDto);
            }

            ScmBackCategoryTreeSimpleOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            // Tree 数据缓存到redis
            customerRedisCommands.setex(SCM_BACK_CATEGORY_TREE_DATA + keyCode, JSONUtil.toJsonStr(treeOutputDto),
                    24 * 60 * 60 + 60);
            return Tuples.of(keyCode, treeOutputDto);
        } finally {
            lock.unlock();
        }
    }

}