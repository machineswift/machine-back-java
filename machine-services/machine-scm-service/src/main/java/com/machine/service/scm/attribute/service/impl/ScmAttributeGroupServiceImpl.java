package com.machine.service.scm.attribute.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.leaf.IDataLeaf4ScmCodeClient;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.client.scm.attribute.dto.ScmAttributeGroupAttributeItemDto;
import com.machine.client.scm.attribute.dto.ScmAttributeGroupBackCategoryItemDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryListInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryPageInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateAttributeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateBackCategoryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateStatusInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupItemOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupSimpleListOutputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.exception.scm.ScmBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.service.scm.attribute.dao.IScmAttributeDao;
import com.machine.service.scm.attribute.dao.IScmBackCategoryAttributeGroupRelationDao;
import com.machine.service.scm.attribute.dao.IScmAttributeGroupDao;
import com.machine.service.scm.attribute.dao.IScmAttributeGroupAttributeRelationDao;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmBackCategoryAttributeGroupRelationEntity;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupEntity;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupAttributeRelationEntity;
import com.machine.service.scm.attribute.service.IScmAttributeGroupService;
import com.machine.starter.redis.cache.scm.RedisScmBackCategoryCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScmAttributeGroupServiceImpl implements IScmAttributeGroupService {

    @Autowired
    private IScmAttributeDao attributeDao;

    @Autowired
    private IScmAttributeGroupDao attributeGroupDao;

    @Autowired
    private IScmAttributeGroupAttributeRelationDao attributeGroupAttributeRelationDao;

    @Autowired
    private IScmBackCategoryAttributeGroupRelationDao backCategoryAttributeGroupRelationDao;

    @Autowired
    private RedisScmBackCategoryCache cacheScmBackCategory;

    @Autowired
    private IDataLeaf4ScmCodeClient leaf4ScmCodeClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmAttributeGroupCreateInputDto inputDto) {
        ScmAttributeGroupEntity entity = new ScmAttributeGroupEntity();
        // 编码由数据服务统一生成
        entity.setCode(leaf4ScmCodeClient.attributeGroupCode());
        entity.setName(inputDto.getName());
        entity.setGroupType(inputDto.getGroupType());
        entity.setStatus(StatusEnum.ENABLE);
        entity.setFeatures(inputDto.getFeatures());
        return attributeGroupDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        ScmAttributeGroupEntity entity = attributeGroupDao.getById(request.getId());
        if (entity == null) {
            return 0;
        }

        if (StatusEnum.ENABLE.equals(entity.getStatus())) {
            throw new ScmBusinessException("scm.attributeGroup.service.delete.enableStatus", "启用状态不能删除");
        }

        if (attributeGroupAttributeRelationDao.countByGroupId(request.getId()) > 0) {
            throw new ScmBusinessException("scm.attributeGroup.service.delete.boundAttribute",
                    "组内已绑定属性，不能删除");
        }

        if (backCategoryAttributeGroupRelationDao.countByGroupId(request.getId()) > 0) {
            throw new ScmBusinessException("scm.attributeGroup.service.delete.boundBackCategory",
                    "已关联后台分类，不能删除");
        }

        return attributeGroupDao.deleteById(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmAttributeGroupUpdateInputDto inputDto) {
        ScmAttributeGroupEntity entityById = attributeGroupDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attributeGroup.service.update.notExists", "属性分组不存在");
        }
        ScmAttributeGroupEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmAttributeGroupEntity.class);
        return attributeGroupDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(ScmAttributeGroupUpdateStatusInputDto inputDto) {
        ScmAttributeGroupEntity entityById = attributeGroupDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attributeGroup.service.updateStatus.notExists", "属性分组不存在");
        }
        if (inputDto.getStatus().equals(entityById.getStatus())) {
            return 0;
        }

        // 启用前校验：该分组关联的后台分类必须都仍然存在
        if (StatusEnum.ENABLE.equals(inputDto.getStatus())) {
            List<String> backCategoryIdList =
                    backCategoryAttributeGroupRelationDao.listBackCategoryIdByGroupId(inputDto.getId());
            if (CollectionUtil.isNotEmpty(backCategoryIdList)) {
                ScmBackCategoryTreeSimpleOutputDto tree = cacheScmBackCategory.treeSimple();
                for (String backCategoryId : backCategoryIdList) {
                    if (null == TreeUtil.findNode(tree, backCategoryId)) {
                        throw new ScmBusinessException(
                                "scm.attributeGroup.service.updateStatus.backCategoryNotExists",
                                "关联的后台分类不存在，不能启用");
                    }
                }
            }
        }

        ScmAttributeGroupEntity entity = new ScmAttributeGroupEntity();
        entity.setId(inputDto.getId());
        entity.setStatus(inputDto.getStatus());
        return attributeGroupDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBackCategory(ScmAttributeGroupUpdateBackCategoryInputDto inputDto) {
        ScmAttributeGroupEntity entityById = attributeGroupDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attributeGroup.service.updateBackCategory.notExists", "属性分组不存在");
        }

        List<ScmAttributeGroupBackCategoryItemDto> itemList =
                CollectionUtil.isEmpty(inputDto.getBackCategoryItemList()) ? List.of() : inputDto.getBackCategoryItemList();

        // 后台分类去重（同时得到分类ID集合）
        Map<String, ScmAttributeGroupBackCategoryItemDto> itemMap = new LinkedHashMap<>(itemList.size());
        for (ScmAttributeGroupBackCategoryItemDto item : itemList) {
            if (StrUtil.isBlank(item.getBackCategoryId())) {
                continue;
            }
            if (itemMap.put(item.getBackCategoryId(), item) != null) {
                throw new ScmBusinessException(
                        "scm.attributeGroup.service.updateBackCategory.backCategoryRepeat", "后台分类不能重复");
            }
        }
        Set<String> backCategoryIdSet = itemMap.keySet();

        // 校验后台分类存在，且同一分组的关联分类之间不能存在父子（含自身）关系
        if (CollectionUtil.isNotEmpty(backCategoryIdSet)) {
            ScmBackCategoryTreeSimpleOutputDto tree = cacheScmBackCategory.treeSimple();
            Map<String, List<ScmBackCategoryTreeSimpleOutputDto>> parentNodeListMap = TreeUtil.getParentNodeListMap(tree, backCategoryIdSet);
            for (String backCategoryId : backCategoryIdSet) {
                List<ScmBackCategoryTreeSimpleOutputDto> parentNodeList = parentNodeListMap.get(backCategoryId);
                if (CollectionUtil.isEmpty(parentNodeList)) {
                    throw new ScmBusinessException(
                            "scm.attributeGroup.service.updateBackCategory.backCategoryNotExists", "后台分类不存在");
                }
                boolean overlap = parentNodeList.stream()
                        .map(ScmBackCategoryTreeSimpleOutputDto::getId)
                        .anyMatch(id -> !id.equals(backCategoryId) && backCategoryIdSet.contains(id));
                if (overlap) {
                    throw new ScmBusinessException(
                            "scm.attributeGroup.service.updateBackCategory.backCategoryOverlap",
                            "关联的后台分类不能存在父子关系");
                }
            }
        }

        // 全量替换该分组的关联后台分类
        backCategoryAttributeGroupRelationDao.deleteByGroupId(inputDto.getId());
        if (CollectionUtil.isNotEmpty(itemMap)) {
            List<ScmBackCategoryAttributeGroupRelationEntity> entityList = new ArrayList<>(itemMap.size());
            for (ScmAttributeGroupBackCategoryItemDto item : itemMap.values()) {
                ScmBackCategoryAttributeGroupRelationEntity relationEntity =
                        new ScmBackCategoryAttributeGroupRelationEntity();
                relationEntity.setGroupId(inputDto.getId());
                relationEntity.setBackCategoryId(item.getBackCategoryId());
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
    public int updateAttribute(ScmAttributeGroupUpdateAttributeInputDto inputDto) {
        ScmAttributeGroupEntity entityById = attributeGroupDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attributeGroup.service.updateAttribute.notExists", "属性分组不存在");
        }

        List<ScmAttributeGroupAttributeItemDto> itemList =
                CollectionUtil.isEmpty(inputDto.getAttributeItemList()) ? List.of() : inputDto.getAttributeItemList();

        // 属性去重（同时得到属性ID集合）
        Map<String, ScmAttributeGroupAttributeItemDto> itemMap = new LinkedHashMap<>(itemList.size());
        for (ScmAttributeGroupAttributeItemDto item : itemList) {
            if (StrUtil.isBlank(item.getAttributeId())) {
                continue;
            }
            if (itemMap.put(item.getAttributeId(), item) != null) {
                throw new ScmBusinessException(
                        "scm.attributeGroup.service.updateAttribute.attributeRepeat", "属性不能重复");
            }
        }

        // 校验属性存在
        if (CollectionUtil.isNotEmpty(itemMap)
                && attributeDao.listByIdSet(itemMap.keySet()).size() != itemMap.size()) {
            throw new ScmBusinessException(
                    "scm.attributeGroup.service.updateAttribute.attributeNotExists", "属性不存在");
        }

        // 全量替换该分组关联的属性
        attributeGroupAttributeRelationDao.deleteByGroupId(inputDto.getId());
        if (CollectionUtil.isNotEmpty(itemMap)) {
            List<ScmAttributeGroupAttributeRelationEntity> entityList = new ArrayList<>(itemMap.size());
            for (ScmAttributeGroupAttributeItemDto item : itemMap.values()) {
                ScmAttributeGroupAttributeRelationEntity relationEntity = new ScmAttributeGroupAttributeRelationEntity();
                relationEntity.setGroupId(inputDto.getId());
                relationEntity.setAttributeId(item.getAttributeId());
                relationEntity.setRequiredFlag(item.getRequiredFlag());
                relationEntity.setDisplayFlag(item.getDisplayFlag());
                relationEntity.setSearchableFlag(item.getSearchableFlag());
                relationEntity.setSort(item.getSort());
                relationEntity.setFeatures(item.getFeatures());
                entityList.add(relationEntity);
            }
            attributeGroupAttributeRelationDao.batchInsert(entityList);
        }
        return itemMap.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCode(ScmAttributeGroupUpdateCodeInputDto inputDto) {
        ScmAttributeGroupEntity entityById = attributeGroupDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attributeGroup.service.updateCode.notExists", "属性分组不存在");
        }
        if (entityById.getCode().equals(inputDto.getCode())) {
            return 0;
        }
        ScmAttributeGroupEntity entityByCode = attributeGroupDao.getByCode(inputDto.getCode());
        if (entityByCode != null && !entityByCode.getId().equals(inputDto.getId())) {
            throw new ScmBusinessException("scm.attributeGroup.service.updateCode.codeAlreadyExists", "编码已经存在");
        }

        ScmAttributeGroupEntity entity = new ScmAttributeGroupEntity();
        entity.setId(inputDto.getId());
        entity.setCode(inputDto.getCode());
        return attributeGroupDao.update(entity);
    }

    @Override
    public Map<String, Integer> mapBackCategoryCountByGroupIdSet(IdSetRequest request) {
        if (CollectionUtil.isEmpty(request.getIdSet())) {
            return Map.of();
        }
        return backCategoryAttributeGroupRelationDao.listByGroupIdSet(request.getIdSet()).stream()
                .collect(Collectors.groupingBy(ScmBackCategoryAttributeGroupRelationEntity::getGroupId,
                        Collectors.summingInt(relation -> 1)));
    }

    @Override
    public Map<String, Integer> mapAttributeCountByGroupIdSet(IdSetRequest request) {
        if (CollectionUtil.isEmpty(request.getIdSet())) {
            return Map.of();
        }
        return attributeGroupAttributeRelationDao.listByGroupIdSet(request.getIdSet()).stream()
                .collect(Collectors.groupingBy(ScmAttributeGroupAttributeRelationEntity::getGroupId,
                        Collectors.summingInt(relation -> 1)));
    }

    @Override
    public Map<String, Integer> mapAttributeGroupCountByBackCategoryIdSet(IdSetRequest request) {
        if (CollectionUtil.isEmpty(request.getIdSet())) {
            return Map.of();
        }
        return backCategoryAttributeGroupRelationDao.listByBackCategoryIdSet(request.getIdSet()).stream()
                .collect(Collectors.groupingBy(ScmBackCategoryAttributeGroupRelationEntity::getBackCategoryId,
                        Collectors.summingInt(relation -> 1)));
    }

    @Override
    public Map<String, Integer> mapAttributeGroupCountByAttributeIdSet(IdSetRequest request) {
        if (CollectionUtil.isEmpty(request.getIdSet())) {
            return Map.of();
        }
        return attributeGroupAttributeRelationDao.listByAttributeIdSet(request.getIdSet()).stream()
                .collect(Collectors.groupingBy(ScmAttributeGroupAttributeRelationEntity::getAttributeId,
                        Collectors.summingInt(relation -> 1)));
    }

    @Override
    public Map<String, List<ScmAttributeGroupItemOutputDto>> mapAttributeGroupByBackCategoryIdSet(IdSetRequest request) {
        if (CollectionUtil.isEmpty(request.getIdSet())) {
            return Map.of();
        }
        List<ScmBackCategoryAttributeGroupRelationEntity> relationList =
                backCategoryAttributeGroupRelationDao.listByBackCategoryIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(relationList)) {
            return Map.of();
        }

        Set<String> groupIdSet = relationList.stream()
                .map(ScmBackCategoryAttributeGroupRelationEntity::getGroupId)
                .collect(Collectors.toSet());
        Map<String, ScmAttributeGroupEntity> groupMap = attributeGroupDao.listByIdSet(groupIdSet).stream()
                .collect(Collectors.toMap(ScmAttributeGroupEntity::getId, entity -> entity, (a, b) -> a));

        Map<String, List<ScmAttributeGroupItemOutputDto>> resultMap = new LinkedHashMap<>();
        for (ScmBackCategoryAttributeGroupRelationEntity relation : relationList) {
            ScmAttributeGroupEntity group = groupMap.get(relation.getGroupId());
            ScmAttributeGroupItemOutputDto item = new ScmAttributeGroupItemOutputDto();
            item.setGroupId(relation.getGroupId());
            item.setGroupName(null == group ? null : group.getName());
            item.setGroupCode(null == group ? null : group.getCode());
            item.setRequiredFlag(relation.getRequiredFlag());
            item.setDisplayFlag(relation.getDisplayFlag());
            item.setSearchableFlag(relation.getSearchableFlag());
            item.setSort(relation.getSort());
            item.setFeatures(relation.getFeatures());
            resultMap.computeIfAbsent(relation.getBackCategoryId(), key -> new ArrayList<>()).add(item);
        }

        // 稳定排序（sort 倒序 + groupId 升序），避免 diff 误报
        for (List<ScmAttributeGroupItemOutputDto> itemList : resultMap.values()) {
            itemList.sort(Comparator.comparing(ScmAttributeGroupItemOutputDto::getSort,
                            Comparator.nullsLast(Comparator.reverseOrder()))
                    .thenComparing(ScmAttributeGroupItemOutputDto::getGroupId,
                            Comparator.nullsLast(Comparator.naturalOrder())));
        }
        return resultMap;
    }

    @Override
    public ScmAttributeGroupDetailOutputDto getById(IdRequest request) {
        ScmAttributeGroupEntity entity = attributeGroupDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        ScmAttributeGroupDetailOutputDto outputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeGroupDetailOutputDto.class);
        return outputDto;
    }

    @Override
    public List<ScmAttributeGroupBackCategoryItemDto> listBackCategoryByGroupId(IdRequest request) {
        return backCategoryAttributeGroupRelationDao.listByGroupId(request.getId()).stream()
                .map(relation -> JSONUtil.toBean(JSONUtil.toJsonStr(relation),
                        ScmAttributeGroupBackCategoryItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScmAttributeGroupSimpleListOutputDto> listByIdSet(IdSetRequest request) {
        if (CollectionUtil.isEmpty(request.getIdSet())) {
            return List.of();
        }
        return attributeGroupDao.listByIdSet(request.getIdSet()).stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeGroupSimpleListOutputDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScmAttributeGroupSimpleListOutputDto> listSimple(ScmAttributeGroupQueryListInputDto inputDto) {
        List<ScmBackCategoryAttributeGroupRelationEntity> relationList =
                backCategoryAttributeGroupRelationDao.listByBackCategoryId(inputDto.getBackCategoryId());
        if (CollectionUtil.isEmpty(relationList)) {
            return List.of();
        }

        Set<String> groupIdSet = relationList.stream()
                .map(ScmBackCategoryAttributeGroupRelationEntity::getGroupId)
                .collect(Collectors.toSet());
        Map<String, ScmAttributeGroupEntity> groupMap = attributeGroupDao.listByIdSet(groupIdSet).stream()
                .collect(Collectors.toMap(ScmAttributeGroupEntity::getId, entity -> entity, (a, b) -> a));

        List<ScmAttributeGroupSimpleListOutputDto> outputDtoList = new ArrayList<>(relationList.size());
        for (ScmBackCategoryAttributeGroupRelationEntity relation : relationList) {
            ScmAttributeGroupEntity group = groupMap.get(relation.getGroupId());
            if (null == group) {
                continue;
            }
            outputDtoList.add(JSONUtil.toBean(JSONUtil.toJsonStr(group), ScmAttributeGroupSimpleListOutputDto.class));
        }
        return outputDtoList;
    }

    @Override
    public PageResponse<ScmAttributeGroupListOutputDto> pageExpand(ScmAttributeGroupQueryPageInputDto inputDto) {
        // 按后台分类筛选时先解析出关联分组ID集合（null=不筛选；空集合=无匹配）
        Set<String> groupIdIn = null;
        if (CollectionUtil.isNotEmpty(inputDto.getBackCategoryIdSet())) {
            groupIdIn = new HashSet<>(backCategoryAttributeGroupRelationDao
                    .listGroupIdByBackCategoryIdSet(inputDto.getBackCategoryIdSet()));
        }
        Page<ScmAttributeGroupEntity> page = attributeGroupDao.selectPage(inputDto, groupIdIn);
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }
        List<ScmAttributeGroupListOutputDto> records = page.getRecords().stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeGroupListOutputDto.class))
                .collect(Collectors.toList());
        return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

}

