package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.user.dto.output.IamUserRoleRelationListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.dao.IUserRoleRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleRelationEntity;
import com.machine.service.iam.user.service.IUserRoleRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserRoleRelationServiceImpl implements IUserRoleRelationService {

    @Autowired
    private IUserRoleRelationDao userRoleRelationDao;

    @Override
    public List<IamUserRoleRelationListOutputDto> listByUserId(IdRequest request) {
        List<UserRoleRelationEntity> entityList = userRoleRelationDao.listByUserId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleRelationListOutputDto.class);
    }

    @Override
    public List<IamUserRoleRelationListOutputDto> listByIdSet(IdSetRequest request) {
        List<UserRoleRelationEntity> entityList = userRoleRelationDao.listByIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleRelationListOutputDto.class);
    }

    @Override
    public List<IamUserRoleRelationListOutputDto> listByRoleIdSet(IdSetRequest request) {
        List<UserRoleRelationEntity> entityList = userRoleRelationDao.listByRoleIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleRelationListOutputDto.class);
    }

    @Override
    public List<IamUserRoleRelationListOutputDto> listByUserIdSet(IdSetRequest request) {
        List<UserRoleRelationEntity> entityList = userRoleRelationDao.listByUserIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleRelationListOutputDto.class);
    }
}
