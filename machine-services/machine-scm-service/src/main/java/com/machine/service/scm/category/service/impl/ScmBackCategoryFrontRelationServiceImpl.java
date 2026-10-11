package com.machine.service.scm.category.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.scm.category.dto.output.ScmBackCategoryFrontRelationDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryFrontRelationListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.category.dao.IScmBackCategoryFrontRelationDao;
import com.machine.service.scm.category.dao.mapper.entity.ScmBackCategoryFrontRelationEntity;
import com.machine.service.scm.category.service.IScmBackCategoryFrontRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ScmBackCategoryFrontRelationServiceImpl implements IScmBackCategoryFrontRelationService {

    @Autowired
    private IScmBackCategoryFrontRelationDao relationDao;

    @Override
    public ScmBackCategoryFrontRelationDetailOutputDto getById(IdRequest request) {
        ScmBackCategoryFrontRelationEntity entity = relationDao.getById(request.getId());
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmBackCategoryFrontRelationDetailOutputDto.class);
    }

    @Override
    public List<ScmBackCategoryFrontRelationListOutputDto> listAll() {
        List<ScmBackCategoryFrontRelationEntity> entityList = relationDao.listAll();
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), ScmBackCategoryFrontRelationListOutputDto.class);
    }

    @Override
    public List<ScmBackCategoryFrontRelationListOutputDto> listByFrontCategoryId(IdRequest request) {
        List<ScmBackCategoryFrontRelationEntity> entityList = relationDao.selectByFrontCategoryId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), ScmBackCategoryFrontRelationListOutputDto.class);
    }

    @Override
    public List<ScmBackCategoryFrontRelationListOutputDto> listByBackCategoryId(IdRequest request) {
        List<ScmBackCategoryFrontRelationEntity> entityList = relationDao.selectByBackCategoryId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), ScmBackCategoryFrontRelationListOutputDto.class);
    }
}