package com.machine.service.iam.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.machine.client.iam.auth.dto.AuthTokenAddDto;
import com.machine.client.iam.auth.dto.input.Oauth2AuthorizationConsentInputDto;
import com.machine.client.iam.auth.dto.output.Oauth2AuthorizationConsentOutputDto;
import com.machine.service.iam.auth.dao.IOauth2AuthorizationConsentDao;
import com.machine.service.iam.auth.dao.mapper.entity.Oauth2AuthorizationConsentEntity;
import com.machine.service.iam.auth.service.IOauth2AuthorizationConsentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class Oauth2AuthorizationConsentServiceImpl implements IOauth2AuthorizationConsentService {

    @Autowired
    private IOauth2AuthorizationConsentDao authorizationConsentDao;

    @Override
    public int add(AuthTokenAddDto dto) {
        return 0;
    }

    @Override
    public void update(Oauth2AuthorizationConsentInputDto dto) {
        Oauth2AuthorizationConsentEntity entity = new Oauth2AuthorizationConsentEntity();
        BeanUtil.copyProperties(dto, entity);
        authorizationConsentDao.update(entity);
    }

    @Override
    public void save(Oauth2AuthorizationConsentInputDto dto) {
        Oauth2AuthorizationConsentEntity entity = new Oauth2AuthorizationConsentEntity();
        BeanUtil.copyProperties(dto, entity);
        authorizationConsentDao.save(entity);

    }

    @Override
    public void remove(Oauth2AuthorizationConsentInputDto dto) {
        Oauth2AuthorizationConsentEntity entity = new Oauth2AuthorizationConsentEntity();
        BeanUtil.copyProperties(dto, entity);
        authorizationConsentDao.remove(entity);

    }

    @Override
    public Oauth2AuthorizationConsentOutputDto findById(Oauth2AuthorizationConsentInputDto dto) {
        Oauth2AuthorizationConsentEntity entity = authorizationConsentDao.findById(dto);
        if (Objects.isNull(entity)) {
            return null;
        }
        Oauth2AuthorizationConsentOutputDto result = new Oauth2AuthorizationConsentOutputDto();
        BeanUtil.copyProperties(entity, result);
        return result;
    }
}
