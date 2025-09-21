package com.machine.service.iam.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.machine.client.iam.auth.dto.IamAuthTokenAddDto;
import com.machine.client.iam.auth.dto.input.IamOauth2AuthorizationConsentInputDto;
import com.machine.client.iam.auth.dto.output.IamOauth2AuthorizationConsentOutputDto;
import com.machine.service.iam.auth.dao.IIamOauth2AuthorizationConsentDao;
import com.machine.service.iam.auth.dao.mapper.entity.IamOauth2AuthorizationConsentEntity;
import com.machine.service.iam.auth.service.IIamOauth2AuthorizationConsentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class IamOauth2AuthorizationConsentServiceImpl implements IIamOauth2AuthorizationConsentService {

    @Autowired
    private IIamOauth2AuthorizationConsentDao authorizationConsentDao;

    @Override
    public int add(IamAuthTokenAddDto dto) {
        return 0;
    }

    @Override
    public void update(IamOauth2AuthorizationConsentInputDto dto) {
        IamOauth2AuthorizationConsentEntity entity = new IamOauth2AuthorizationConsentEntity();
        BeanUtil.copyProperties(dto, entity);
        authorizationConsentDao.update(entity);
    }

    @Override
    public void save(IamOauth2AuthorizationConsentInputDto dto) {
        IamOauth2AuthorizationConsentEntity entity = new IamOauth2AuthorizationConsentEntity();
        BeanUtil.copyProperties(dto, entity);
        authorizationConsentDao.save(entity);

    }

    @Override
    public void remove(IamOauth2AuthorizationConsentInputDto dto) {
        IamOauth2AuthorizationConsentEntity entity = new IamOauth2AuthorizationConsentEntity();
        BeanUtil.copyProperties(dto, entity);
        authorizationConsentDao.remove(entity);

    }

    @Override
    public IamOauth2AuthorizationConsentOutputDto findById(IamOauth2AuthorizationConsentInputDto dto) {
        IamOauth2AuthorizationConsentEntity entity = authorizationConsentDao.findById(dto);
        if (Objects.isNull(entity)) {
            return null;
        }
        IamOauth2AuthorizationConsentOutputDto result = new IamOauth2AuthorizationConsentOutputDto();
        BeanUtil.copyProperties(entity, result);
        return result;
    }
}
