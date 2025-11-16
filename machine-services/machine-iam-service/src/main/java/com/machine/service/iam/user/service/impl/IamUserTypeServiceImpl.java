package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.client.iam.user.dto.output.IamUserTypeOutputDto;
import com.machine.sdk.common.envm.iam.user.IamUserTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.dao.IIamUserTypeDao;
import com.machine.service.iam.user.dao.mapper.entity.IamUserTypeEntity;
import com.machine.service.iam.user.service.IIamUserTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class IamUserTypeServiceImpl implements IIamUserTypeService {

    @Autowired
    private IIamUserTypeDao userTypeDao;

    @Override
    public boolean existsType(IamUserTypeExistsTypeInputDto inputDto) {
     return userTypeDao.existsType(inputDto);
    }

    @Override
    public List<IamUserTypeOutputDto> listByUserId(IdRequest request) {
        List<IamUserTypeEntity> entityList = userTypeDao.selectByUserId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserTypeOutputDto.class);
    }

    @Override
    public List<IamUserTypeEnum> listTypeByUserId(IdRequest request) {
        List<IamUserTypeEntity> entityList = userTypeDao.selectByUserId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return entityList.stream().map(IamUserTypeEntity::getUserType).toList();
    }

    @Override
    public Map<String, List<IamUserTypeEnum>> mapTypeByUserIdSet(IdSetRequest request) {
        List<IamUserTypeEntity> entityList = userTypeDao.selectByUserIds(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return Map.of();
        }

        Map<String, List<IamUserTypeEnum>> userTypeMap = new HashMap<>();
        for (IamUserTypeEntity entity : entityList) {
            List<IamUserTypeEnum> typeList = userTypeMap.get(entity.getUserId());
            if (CollectionUtil.isEmpty(typeList)) {
                typeList = new ArrayList<>();
                userTypeMap.put(entity.getUserId(), typeList);
            }
            typeList.add(entity.getUserType());
        }

        return userTypeMap;
    }
}
