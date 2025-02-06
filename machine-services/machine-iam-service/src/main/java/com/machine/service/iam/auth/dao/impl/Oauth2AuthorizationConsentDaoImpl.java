package com.machine.service.iam.auth.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.machine.client.iam.auth.dto.input.Oauth2AuthorizationConsentInputDto;
import com.machine.client.iam.auth.dto.output.Oauth2AuthorizationConsentOutputDto;
import com.machine.service.iam.auth.dao.IOauth2AuthorizationConsentDao;
import com.machine.service.iam.auth.dao.mapper.Oauth2AuthorizationConsentMapper;
import com.machine.service.iam.auth.dao.mapper.entity.Oauth2AuthorizationConsentEntity;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;

@Repository
public class Oauth2AuthorizationConsentDaoImpl implements IOauth2AuthorizationConsentDao {

    @Autowired
    private Oauth2AuthorizationConsentMapper authorizationConsentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Oauth2AuthorizationConsentEntity entity) {
        UpdateWrapper<Oauth2AuthorizationConsentEntity> oauth2AuthorizationConsentEntityUpdateWrapper = new UpdateWrapper<>();
        if (StringUtils.isNotBlank(entity.getPrincipalName())) {
            oauth2AuthorizationConsentEntityUpdateWrapper.eq("principal_name", entity.getPrincipalName());
        }
        if (StringUtils.isNotBlank(entity.getRegisteredClientId())) {
            oauth2AuthorizationConsentEntityUpdateWrapper.eq("principal_name", entity.getRegisteredClientId());
        }
        authorizationConsentMapper.update(entity, oauth2AuthorizationConsentEntityUpdateWrapper);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Oauth2AuthorizationConsentEntity entity) {
        authorizationConsentMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Oauth2AuthorizationConsentEntity entity) {
        authorizationConsentMapper.deleteById(entity.getId());
    }

    @Override
    public Oauth2AuthorizationConsentEntity findById(Oauth2AuthorizationConsentInputDto dto) {
        return authorizationConsentMapper.selectOne(new QueryWrapper<Oauth2AuthorizationConsentEntity>()
                .eq("registered_client_id", dto.getRegisteredClientId())
                .eq("principal_name", dto.getPrincipalName()));
    }
}
