package com.machine.service.scm.attribute.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.leaf.IDataLeaf4ScmCodeClient;
import com.machine.client.scm.attribute.dto.ScmAttributeAttributeGroupItemDto;
import com.machine.client.scm.attribute.dto.ScmAttributeBackCategoryItemDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeQueryPageInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateAttributeGroupInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateBackCategoryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeListOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.exception.scm.ScmBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.service.scm.attribute.dao.IScmBackCategoryAttributeRelationDao;
import com.machine.service.scm.attribute.dao.IScmAttributeDao;
import com.machine.service.scm.attribute.dao.IScmAttributeGroupDao;
import com.machine.service.scm.attribute.dao.IScmAttributeGroupAttributeRelationDao;
import com.machine.service.scm.attribute.dao.IScmAttributeValueDao;
import com.machine.service.scm.attribute.dao.IScmAttributeValueRelationDao;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeEntity;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupAttributeRelationEntity;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeValueEntity;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmBackCategoryAttributeRelationEntity;
import com.machine.service.scm.attribute.service.IScmAttributeService;
import com.machine.starter.redis.cache.scm.RedisScmBackCategoryCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScmAttributeServiceImpl implements IScmAttributeService {

    @Autowired
    private IScmAttributeDao attributeDao;

    @Autowired
    private IScmAttributeValueDao attributeValueDao;

    @Autowired
    private IScmBackCategoryAttributeRelationDao backCategoryAttributeRelationDao;

    @Autowired
    private IScmAttributeGroupAttributeRelationDao attributeGroupAttributeRelationDao;

    @Autowired
    private IScmAttributeGroupDao attributeGroupDao;

    @Autowired
    private IScmAttributeValueRelationDao attributeValueRelationDao;

    @Autowired
    private IDataLeaf4ScmCodeClient leaf4ScmCodeClient;

    @Autowired
    private RedisScmBackCategoryCache cacheScmBackCategory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmAttributeCreateInputDto inputDto) {
        ScmAttributeEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmAttributeEntity.class);
        // 编码由数据服务统一生成
        entity.setCode(leaf4ScmCodeClient.attributeCode());
        return attributeDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        ScmAttributeEntity entityById = attributeDao.getById(request.getId());
        if (entityById == null) {
            return 0;
        }
        if (backCategoryAttributeRelationDao.countByAttributeId(request.getId()) > 0) {
            throw new ScmBusinessException("scm.attribute.service.delete.boundCategory", "属性已绑定后台类目，不能删除");
        }
        if (attributeGroupAttributeRelationDao.countByAttributeId(request.getId()) > 0) {
            throw new ScmBusinessException("scm.attribute.service.delete.boundGroup", "属性已加入属性分组，不能删除");
        }

        // 属性值及其驱动关系一起清掉（parent_value_id 外键为 RESTRICT，必须先清关系）
        List<ScmAttributeValueEntity> valueList = attributeValueDao.listByAttributeId(request.getId());
        Set<String> valueIdSet = valueList.stream()
                .map(ScmAttributeValueEntity::getId)
                .collect(Collectors.toSet());
        attributeValueRelationDao.deleteByParentValueIdSet(valueIdSet);
        attributeValueRelationDao.deleteByChildAttributeId(request.getId());
        for (ScmAttributeValueEntity value : valueList) {
            attributeValueDao.deleteById(value.getId());
        }
        return attributeDao.deleteById(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmAttributeUpdateInputDto inputDto) {
        ScmAttributeEntity entityById = attributeDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attribute.service.update.notExists", "属性不存在");
        }
        if (attributeDao.countByNameExcludeId(inputDto.getName(), inputDto.getId()) > 0) {
            throw new ScmBusinessException("scm.attribute.service.update.nameAlreadyExists", "名称已经存在");
        }
        ScmAttributeEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmAttributeEntity.class);
        return attributeDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCode(ScmAttributeUpdateCodeInputDto inputDto) {
        ScmAttributeEntity entityById = attributeDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attribute.service.updateCode.notExists", "属性不存在");
        }
        if (entityById.getCode().equals(inputDto.getCode())) {
            return 0;
        }
        ScmAttributeEntity entityByCode = attributeDao.getByCode(inputDto.getCode());
        if (entityByCode != null && !entityByCode.getId().equals(inputDto.getId())) {
            throw new ScmBusinessException("scm.attribute.service.updateCode.codeAlreadyExists", "编码已经存在");
        }

        ScmAttributeEntity entity = new ScmAttributeEntity();
        entity.setId(inputDto.getId());
        entity.setCode(inputDto.getCode());
        return attributeDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAttributeGroup(ScmAttributeUpdateAttributeGroupInputDto inputDto) {
        ScmAttributeEntity entityById = attributeDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attribute.service.updateAttributeGroup.notExists", "属性不存在");
        }

        List<ScmAttributeAttributeGroupItemDto> itemList =
                CollectionUtil.isEmpty(inputDto.getAttributeGroupItemList()) ? List.of() : inputDto.getAttributeGroupItemList();

        // 属性分组去重（同时得到分组ID集合）
        Map<String, ScmAttributeAttributeGroupItemDto> itemMap = new LinkedHashMap<>(itemList.size());
        for (ScmAttributeAttributeGroupItemDto item : itemList) {
            if (StrUtil.isBlank(item.getGroupId())) {
                continue;
            }
            if (itemMap.put(item.getGroupId(), item) != null) {
                throw new ScmBusinessException(
                        "scm.attribute.service.updateAttributeGroup.groupRepeat", "属性分组不能重复");
            }
        }

        // 校验属性分组存在
        if (CollectionUtil.isNotEmpty(itemMap)
                && attributeGroupDao.listByIdSet(itemMap.keySet()).size() != itemMap.size()) {
            throw new ScmBusinessException(
                    "scm.attribute.service.updateAttributeGroup.groupNotExists", "属性分组不存在");
        }

        // 全量替换该属性关联的属性分组
        attributeGroupAttributeRelationDao.deleteByAttributeId(inputDto.getId());
        if (CollectionUtil.isNotEmpty(itemMap)) {
            List<ScmAttributeGroupAttributeRelationEntity> entityList = new ArrayList<>(itemMap.size());
            for (ScmAttributeAttributeGroupItemDto item : itemMap.values()) {
                ScmAttributeGroupAttributeRelationEntity relationEntity = new ScmAttributeGroupAttributeRelationEntity();
                relationEntity.setAttributeId(inputDto.getId());
                relationEntity.setGroupId(item.getGroupId());
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
    public int updateBackCategory(ScmAttributeUpdateBackCategoryInputDto inputDto) {
        ScmAttributeEntity entityById = attributeDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attribute.service.updateBackCategory.notExists", "属性不存在");
        }

        List<ScmAttributeBackCategoryItemDto> itemList =
                CollectionUtil.isEmpty(inputDto.getBackCategoryItemList()) ? List.of() : inputDto.getBackCategoryItemList();

        // 后台分类去重（同时得到分类ID集合）
        Map<String, ScmAttributeBackCategoryItemDto> itemMap = new LinkedHashMap<>(itemList.size());
        for (ScmAttributeBackCategoryItemDto item : itemList) {
            if (StrUtil.isBlank(item.getBackCategoryId())) {
                continue;
            }
            if (itemMap.put(item.getBackCategoryId(), item) != null) {
                throw new ScmBusinessException(
                        "scm.attribute.service.updateBackCategory.backCategoryRepeat", "后台分类不能重复");
            }
        }
        Set<String> backCategoryIdSet = itemMap.keySet();

        // 校验后台分类存在，且同一属性的关联分类之间不能存在父子（含自身）关系
        if (CollectionUtil.isNotEmpty(backCategoryIdSet)) {
            ScmBackCategoryTreeSimpleOutputDto tree = cacheScmBackCategory.treeSimple();
            Map<String, List<ScmBackCategoryTreeSimpleOutputDto>> parentNodeListMap =
                    TreeUtil.getParentNodeListMap(tree, backCategoryIdSet);
            for (String backCategoryId : backCategoryIdSet) {
                List<ScmBackCategoryTreeSimpleOutputDto> parentNodeList = parentNodeListMap.get(backCategoryId);
                if (CollectionUtil.isEmpty(parentNodeList)) {
                    throw new ScmBusinessException(
                            "scm.attribute.service.updateBackCategory.backCategoryNotExists", "后台分类不存在");
                }
                boolean overlap = parentNodeList.stream()
                        .map(ScmBackCategoryTreeSimpleOutputDto::getId)
                        .anyMatch(id -> !id.equals(backCategoryId) && backCategoryIdSet.contains(id));
                if (overlap) {
                    throw new ScmBusinessException(
                            "scm.attribute.service.updateBackCategory.backCategoryOverlap",
                            "关联的后台分类不能存在父子关系");
                }
            }
        }

        // 全量替换该属性关联的后台分类
        backCategoryAttributeRelationDao.deleteByAttributeId(inputDto.getId());
        if (CollectionUtil.isNotEmpty(itemMap)) {
            List<ScmBackCategoryAttributeRelationEntity> entityList = new ArrayList<>(itemMap.size());
            for (ScmAttributeBackCategoryItemDto item : itemMap.values()) {
                ScmBackCategoryAttributeRelationEntity relationEntity =
                        new ScmBackCategoryAttributeRelationEntity();
                relationEntity.setAttributeId(inputDto.getId());
                relationEntity.setBackCategoryId(item.getBackCategoryId());
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
    public ScmAttributeDetailOutputDto getById(IdRequest request) {
        ScmAttributeEntity entity = attributeDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeDetailOutputDto.class);
    }

    @Override
    public List<ScmAttributeListOutputDto> listAll() {
        return attributeDao.listAll().stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeListOutputDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScmAttributeListOutputDto> listByIdSet(IdSetRequest request) {
        if (CollectionUtil.isEmpty(request.getIdSet())) {
            return List.of();
        }
        return attributeDao.listByIdSet(request.getIdSet()).stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeListOutputDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<ScmAttributeListOutputDto> selectPage(ScmAttributeQueryPageInputDto inputDto) {
        Page<ScmAttributeEntity> page = attributeDao.selectPage(inputDto);
        if (page.getRecords() == null || page.getRecords().isEmpty()) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }
        List<ScmAttributeListOutputDto> records = page.getRecords().stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeListOutputDto.class))
                .collect(Collectors.toList());
        return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }
}
