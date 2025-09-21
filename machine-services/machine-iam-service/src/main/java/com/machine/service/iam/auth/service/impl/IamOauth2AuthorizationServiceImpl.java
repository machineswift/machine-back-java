package com.machine.service.iam.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.machine.client.iam.auth.dto.input.IamOAuth2AuthorizationDto;
import com.machine.client.iam.auth.dto.output.IamOAuth2AuthorizationOutputDto;
import com.machine.service.iam.auth.dao.IIamOauth2AuthorizationDao;
import com.machine.service.iam.auth.dao.mapper.entity.IamOauth2AuthorizationEntity;
import com.machine.service.iam.auth.service.IIamOauth2AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class IamOauth2AuthorizationServiceImpl implements IIamOauth2AuthorizationService {

    @Autowired
    private IIamOauth2AuthorizationDao authorizationDao;


    @Override
    public int save(IamOAuth2AuthorizationDto dto) {
        IamOauth2AuthorizationEntity entity = new IamOauth2AuthorizationEntity();
        BeanUtil.copyProperties(dto, entity);
        saveAdminInfo(entity);
        return authorizationDao.save(entity);
    }

    private static void saveAdminInfo(IamOauth2AuthorizationEntity entity) {
        entity.setUpdateBy("admin");
        entity.setUpdateTime(System.currentTimeMillis());
        entity.setCreateBy("admin");
        entity.setCreateTime(System.currentTimeMillis());
    }
    @Override
    public int update(IamOAuth2AuthorizationDto dto) {
        IamOauth2AuthorizationEntity entity = new IamOauth2AuthorizationEntity();
        BeanUtil.copyProperties(dto, entity);
        saveAdminInfo(entity);
        return authorizationDao.update(entity);
    }

    @Override
    public IamOAuth2AuthorizationOutputDto findById(String id) {
        IamOauth2AuthorizationEntity entity = authorizationDao.findById(id);
        if (Objects.isNull(entity)) {
            return null;
        }
        IamOAuth2AuthorizationOutputDto dto = new IamOAuth2AuthorizationOutputDto();
        BeanUtil.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public IamOAuth2AuthorizationOutputDto findByToken(IamOAuth2AuthorizationDto dto) {
        IamOauth2AuthorizationEntity entity = new IamOauth2AuthorizationEntity();
        BeanUtil.copyProperties(dto, entity);
        IamOauth2AuthorizationEntity authorizationEntity = authorizationDao.findByToken(entity);
        if (Objects.isNull(authorizationEntity)) {
            return null;
        }
        IamOAuth2AuthorizationOutputDto result = new IamOAuth2AuthorizationOutputDto();
        BeanUtil.copyProperties(authorizationEntity, result);
        return result;
    }

    @Override
    public void remove(String id) {
        authorizationDao.remove(id);
    }
}
