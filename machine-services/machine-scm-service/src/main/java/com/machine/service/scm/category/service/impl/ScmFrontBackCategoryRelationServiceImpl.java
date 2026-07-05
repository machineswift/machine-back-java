package com.machine.service.scm.category.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.scm.category.dto.output.ScmFrontBackCategoryRelationDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontBackCategoryRelationListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.category.dao.IScmFrontBackCategoryRelationDao;
import com.machine.service.scm.category.dao.mapper.entity.ScmFrontBackCategoryRelationEntity;
import com.machine.service.scm.category.service.IScmFrontBackCategoryRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ScmFrontBackCategoryRelationServiceImpl implements IScmFrontBackCategoryRelationService {

    @Autowired
    private IScmFrontBackCategoryRelationDao relationDao;

    @Override
    public ScmFrontBackCategoryRelationDetailOutputDto getById(IdRequest request) {
        ScmFrontBackCategoryRelationEntity entity = relationDao.getById(request.getId());
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmFrontBackCategoryRelationDetailOutputDto.class);
    }

    @Override
    public List<ScmFrontBackCategoryRelationListOutputDto> listAll() {
        List<ScmFrontBackCategoryRelationEntity> entityList = relationDao.listAll();
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), ScmFrontBackCategoryRelationListOutputDto.class);
    }

    @Override
    public List<ScmFrontBackCategoryRelationListOutputDto> listByFrontCategoryId(IdRequest request) {
        List<ScmFrontBackCategoryRelationEntity> entityList = relationDao.selectByFrontCategoryId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), ScmFrontBackCategoryRelationListOutputDto.class);
    }
}