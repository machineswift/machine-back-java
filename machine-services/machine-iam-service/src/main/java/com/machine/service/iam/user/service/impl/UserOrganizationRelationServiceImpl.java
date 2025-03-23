package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.user.dto.output.IamUserOrganizationRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.dao.IUserOrganizationRelationDao;
import com.machine.service.iam.user.service.IUserOrganizationRelationService;
import com.machine.service.iam.user.dao.mapper.entity.UserOrganizationRelationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserOrganizationRelationServiceImpl implements IUserOrganizationRelationService {

    @Autowired
    private IUserOrganizationRelationDao userOrganizationRelationDao;

    @Override
    public Map<String, String> mapLeaderByOrganizationIdSet(IdSetRequest request) {
        List<UserOrganizationRelationEntity> entityList = userOrganizationRelationDao.mapLeaderByOrganizationIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return Map.of();
        }

        return entityList.stream()
                .collect(Collectors.toMap(
                        UserOrganizationRelationEntity::getOrganizationId,
                        UserOrganizationRelationEntity::getUserId
                ));
    }

    @Override
    public IamUserOrganizationRelationOutputDto selectLeaderByOrganizationId(IdRequest request) {
        UserOrganizationRelationEntity entity = userOrganizationRelationDao.selectLeaderByOrganizationId(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamUserOrganizationRelationOutputDto.class);
    }
}
