package com.machine.service.scm.attribute.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.scm.attribute.dto.input.ScmBackCategoryAttributeRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmBackCategoryAttributeRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.scm.attribute.dao.IScmBackCategoryAttributeRelationDao;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmBackCategoryAttributeRelationEntity;
import com.machine.service.scm.attribute.service.IScmBackCategoryAttributeRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScmBackCategoryAttributeRelationServiceImpl implements IScmBackCategoryAttributeRelationService {

    @Autowired
    private IScmBackCategoryAttributeRelationDao relationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmBackCategoryAttributeRelationCreateInputDto inputDto) {
        ScmBackCategoryAttributeRelationEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmBackCategoryAttributeRelationEntity.class);
        return relationDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        return relationDao.deleteById(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmBackCategoryAttributeRelationEntity entity) {
        return relationDao.update(entity);
    }

    @Override
    public ScmBackCategoryAttributeRelationDetailOutputDto getById(IdRequest request) {
        ScmBackCategoryAttributeRelationEntity entity = relationDao.getById(request.getId());
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmBackCategoryAttributeRelationDetailOutputDto.class);
    }

    @Override
    public List<ScmBackCategoryAttributeRelationEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet) {
        return relationDao.listByBackCategoryIdSet(backCategoryIdSet);
    }

    @Override
    public List<ScmBackCategoryAttributeRelationDetailOutputDto> listByAttributeId(IdRequest request) {
        return JSONUtil.toList(JSONUtil.toJsonStr(relationDao.listByAttributeIdSet(Set.of(request.getId()))),
                ScmBackCategoryAttributeRelationDetailOutputDto.class);
    }

    @Override
    public List<ScmBackCategoryAttributeRelationDetailOutputDto> listByBackCategoryId(IdRequest request) {
        return JSONUtil.toList(JSONUtil.toJsonStr(relationDao.listByBackCategoryIdSet(Set.of(request.getId()))),
                ScmBackCategoryAttributeRelationDetailOutputDto.class);
    }

    @Override
    public Map<String, Integer> mapAttributeCountByBackCategoryIdSet(IdSetRequest request) {
        if (CollectionUtil.isEmpty(request.getIdSet())) {
            return Map.of();
        }
        return relationDao.listByBackCategoryIdSet(request.getIdSet()).stream()
                .collect(Collectors.groupingBy(ScmBackCategoryAttributeRelationEntity::getBackCategoryId,
                        Collectors.summingInt(relation -> 1)));
    }

    @Override
    public Map<String, Integer> mapBackCategoryCountByAttributeIdSet(IdSetRequest request) {
        if (CollectionUtil.isEmpty(request.getIdSet())) {
            return Map.of();
        }
        return relationDao.listByAttributeIdSet(request.getIdSet()).stream()
                .collect(Collectors.groupingBy(ScmBackCategoryAttributeRelationEntity::getAttributeId,
                        Collectors.summingInt(relation -> 1)));
    }

}