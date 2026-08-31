package com.machine.service.iam.biam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.user.dto.output.BIamUserOrganizationRelationOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.iam.biam.user.dao.IBIamUserOrganizationRelationDao;
import com.machine.service.iam.biam.user.service.IBIamUserOrganizationRelationService;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserOrganizationRelationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BIamUserOrganizationRelationServiceImpl implements IBIamUserOrganizationRelationService {

    @Autowired
    private IBIamUserOrganizationRelationDao userOrganizationRelationDao;

    @Override
    public List<BIamUserOrganizationRelationOutputDto> listByUserId(IdRequest request) {
        List<BIamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamUserOrganizationRelationOutputDto.class);
    }

    @Override
    public List<BIamUserOrganizationRelationOutputDto> listByOrganizationIdSet(IdSetRequest request) {
        List<BIamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByOrganizationIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamUserOrganizationRelationOutputDto.class);
    }
}
