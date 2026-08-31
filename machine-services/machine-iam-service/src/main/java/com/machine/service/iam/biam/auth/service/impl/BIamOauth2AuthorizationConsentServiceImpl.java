package com.machine.service.iam.biam.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.machine.client.iam.biam.auth.dto.input.BIamOauth2AuthorizationConsentInputDto;
import com.machine.client.iam.biam.auth.dto.output.BIamOauth2AuthorizationConsentOutputDto;
import com.machine.service.iam.biam.auth.dao.IBIamOauth2AuthorizationConsentDao;
import com.machine.service.iam.biam.auth.dao.mapper.entity.BIamOauth2AuthorizationConsentEntity;
import com.machine.service.iam.biam.auth.service.IBIamOauth2AuthorizationConsentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class BIamOauth2AuthorizationConsentServiceImpl implements IBIamOauth2AuthorizationConsentService {

    @Autowired
    private IBIamOauth2AuthorizationConsentDao authorizationConsentDao;

    @Override
    public void update(BIamOauth2AuthorizationConsentInputDto dto) {
        BIamOauth2AuthorizationConsentEntity entity = new BIamOauth2AuthorizationConsentEntity();
        BeanUtil.copyProperties(dto, entity);
        authorizationConsentDao.update(entity);
    }

    @Override
    public void save(BIamOauth2AuthorizationConsentInputDto dto) {
        BIamOauth2AuthorizationConsentEntity entity = new BIamOauth2AuthorizationConsentEntity();
        BeanUtil.copyProperties(dto, entity);
        authorizationConsentDao.save(entity);

    }

    @Override
    public void remove(BIamOauth2AuthorizationConsentInputDto dto) {
        BIamOauth2AuthorizationConsentEntity entity = new BIamOauth2AuthorizationConsentEntity();
        BeanUtil.copyProperties(dto, entity);
        authorizationConsentDao.remove(entity);

    }

    @Override
    public BIamOauth2AuthorizationConsentOutputDto findById(BIamOauth2AuthorizationConsentInputDto dto) {
        BIamOauth2AuthorizationConsentEntity entity = authorizationConsentDao.findById(dto);
        if (Objects.isNull(entity)) {
            return null;
        }
        BIamOauth2AuthorizationConsentOutputDto result = new BIamOauth2AuthorizationConsentOutputDto();
        BeanUtil.copyProperties(entity, result);
        return result;
    }
}
