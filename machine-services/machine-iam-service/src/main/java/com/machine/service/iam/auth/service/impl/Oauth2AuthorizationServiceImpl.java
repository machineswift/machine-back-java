package com.machine.service.iam.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.machine.client.iam.auth.dto.input.OAuth2AuthorizationDto;
import com.machine.client.iam.auth.dto.output.OAuth2AuthorizationOutputDto;
import com.machine.service.iam.auth.dao.IOauth2AuthorizationDao;
import com.machine.service.iam.auth.dao.mapper.entity.Oauth2AuthorizationEntity;
import com.machine.service.iam.auth.service.IOauth2AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class Oauth2AuthorizationServiceImpl implements IOauth2AuthorizationService {

    @Autowired
    private IOauth2AuthorizationDao authorizationDao;


    @Override
    public int save(OAuth2AuthorizationDto dto) {
        Oauth2AuthorizationEntity entity = new Oauth2AuthorizationEntity();
        BeanUtil.copyProperties(dto, entity);
        saveAdminInfo(entity);
        return authorizationDao.save(entity);
    }

    private static void saveAdminInfo(Oauth2AuthorizationEntity entity) {
        entity.setUpdateBy("admin");
        entity.setUpdateTime(System.currentTimeMillis());
        entity.setCreateBy("admin");
        entity.setCreateTime(System.currentTimeMillis());
    }
    @Override
    public int update(OAuth2AuthorizationDto dto) {
        Oauth2AuthorizationEntity entity = new Oauth2AuthorizationEntity();
        BeanUtil.copyProperties(dto, entity);
        saveAdminInfo(entity);
        return authorizationDao.update(entity);
    }

    @Override
    public OAuth2AuthorizationOutputDto findById(String id) {
        Oauth2AuthorizationEntity entity = authorizationDao.findById(id);
        if (Objects.isNull(entity)) {
            return null;
        }
        OAuth2AuthorizationOutputDto dto = new OAuth2AuthorizationOutputDto();
        BeanUtil.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public OAuth2AuthorizationOutputDto findByToken(OAuth2AuthorizationDto dto) {
        Oauth2AuthorizationEntity entity = new Oauth2AuthorizationEntity();
        BeanUtil.copyProperties(dto, entity);
        Oauth2AuthorizationEntity authorizationEntity = authorizationDao.findByToken(entity);
        if (Objects.isNull(authorizationEntity)) {
            return null;
        }
        OAuth2AuthorizationOutputDto result = new OAuth2AuthorizationOutputDto();
        BeanUtil.copyProperties(authorizationEntity, result);
        return result;
    }

    @Override
    public void remove(String id) {
        authorizationDao.remove(id);
    }
}
