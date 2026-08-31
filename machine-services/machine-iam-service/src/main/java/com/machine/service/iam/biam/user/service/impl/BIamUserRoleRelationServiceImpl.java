package com.machine.service.iam.biam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.user.dto.output.BIamUserRoleRelationListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.iam.biam.user.dao.IBIamUserRoleRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleRelationEntity;
import com.machine.service.iam.biam.user.service.IBIamUserRoleRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BIamUserRoleRelationServiceImpl implements IBIamUserRoleRelationService {

    @Autowired
    private IBIamUserRoleRelationDao userRoleRelationDao;

    @Override
    public List<BIamUserRoleRelationListOutputDto> listByUserId(IdRequest request) {
        List<BIamUserRoleRelationEntity> entityList = userRoleRelationDao.listByUserId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamUserRoleRelationListOutputDto.class);
    }

    @Override
    public List<BIamUserRoleRelationListOutputDto> listByIdSet(IdSetRequest request) {
        List<BIamUserRoleRelationEntity> entityList = userRoleRelationDao.listByIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamUserRoleRelationListOutputDto.class);
    }

    @Override
    public List<BIamUserRoleRelationListOutputDto> listByRoleIdSet(IdSetRequest request) {
        List<BIamUserRoleRelationEntity> entityList = userRoleRelationDao.listByRoleIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamUserRoleRelationListOutputDto.class);
    }

    @Override
    public List<BIamUserRoleRelationListOutputDto> listByUserIdSet(IdSetRequest request) {
        List<BIamUserRoleRelationEntity> entityList = userRoleRelationDao.listByUserIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamUserRoleRelationListOutputDto.class);
    }

    @Override
    public Map<String, Integer> countUserByRoleIdSet(IdSetRequest request) {
        return userRoleRelationDao.countUserByRoleIdSet(request.getIdSet());
    }
}
