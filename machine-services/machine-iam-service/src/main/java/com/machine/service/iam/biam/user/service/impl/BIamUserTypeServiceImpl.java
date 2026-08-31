package com.machine.service.iam.biam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.user.dto.input.BIamUserTypeExistsTypeInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserTypeOutputDto;
import com.machine.sdk.base.envm.biam.user.BIamUserTypeEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.iam.biam.user.dao.IBIamUserTypeDao;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserTypeEntity;
import com.machine.service.iam.biam.user.service.IBIamUserTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BIamUserTypeServiceImpl implements IBIamUserTypeService {

    @Autowired
    private IBIamUserTypeDao userTypeDao;

    @Override
    public boolean existsType(BIamUserTypeExistsTypeInputDto inputDto) {
     return userTypeDao.existsType(inputDto);
    }

    @Override
    public List<BIamUserTypeOutputDto> listByUserId(IdRequest request) {
        List<BIamUserTypeEntity> entityList = userTypeDao.selectByUserId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamUserTypeOutputDto.class);
    }

    @Override
    public List<BIamUserTypeEnum> listTypeByUserId(IdRequest request) {
        List<BIamUserTypeEntity> entityList = userTypeDao.selectByUserId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return entityList.stream().map(BIamUserTypeEntity::getUserType).toList();
    }

    @Override
    public Map<String, List<BIamUserTypeEnum>> mapTypeByUserIdSet(IdSetRequest request) {
        List<BIamUserTypeEntity> entityList = userTypeDao.selectByUserIds(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return Map.of();
        }

        Map<String, List<BIamUserTypeEnum>> userTypeMap = new HashMap<>();
        for (BIamUserTypeEntity entity : entityList) {
            List<BIamUserTypeEnum> typeList = userTypeMap.get(entity.getUserId());
            if (CollectionUtil.isEmpty(typeList)) {
                typeList = new ArrayList<>();
                userTypeMap.put(entity.getUserId(), typeList);
            }
            typeList.add(entity.getUserType());
        }

        return userTypeMap;
    }
}
