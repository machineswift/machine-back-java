package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.user.dto.output.IamUserOrganizationRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.dao.IIamUserOrganizationRelationDao;
import com.machine.service.iam.user.service.IIamUserOrganizationRelationService;
import com.machine.service.iam.user.dao.mapper.entity.IamUserOrganizationRelationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IamUserOrganizationRelationServiceImpl implements IIamUserOrganizationRelationService {

    @Autowired
    private IIamUserOrganizationRelationDao userOrganizationRelationDao;

    @Override
    public List<IamUserOrganizationRelationOutputDto> listByUserId(IdRequest request) {
        List<IamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserOrganizationRelationOutputDto.class);
    }

    @Override
    public List<IamUserOrganizationRelationOutputDto> listByOrganizationIdSet(IdSetRequest request) {
        List<IamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByOrganizationIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserOrganizationRelationOutputDto.class);
    }
}
