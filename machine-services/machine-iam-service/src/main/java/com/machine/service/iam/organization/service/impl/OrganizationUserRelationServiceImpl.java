package com.machine.service.iam.organization.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.organization.dto.output.IamOrganizationUserRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.organization.dao.IOrganizationUserRelationDao;
import com.machine.service.iam.organization.dao.mapper.entity.OrganizationUserRelationEntity;
import com.machine.service.iam.organization.service.IOrganizationUserRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class OrganizationUserRelationServiceImpl implements IOrganizationUserRelationService {

    @Autowired
    private IOrganizationUserRelationDao organizationUserRelationDao;

    @Override
    public Map<String, String> mapByOrganizationIdSet(IdSetRequest request) {
        List<OrganizationUserRelationEntity> entityList = organizationUserRelationDao.selectByOrganizationIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return Map.of();
        }

        return entityList.stream()
                .collect(Collectors.toMap(
                        OrganizationUserRelationEntity::getOrganizationId,
                        OrganizationUserRelationEntity::getUserId
                ));
    }

    @Override
    public IamOrganizationUserRelationOutputDto selectByOrganizationId(IdRequest request) {
        OrganizationUserRelationEntity entity = organizationUserRelationDao.selectByOrganizationId(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamOrganizationUserRelationOutputDto.class);
    }
}
