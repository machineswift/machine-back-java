package com.machine.service.iam.auth.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.machine.client.iam.auth.dto.input.IamOauth2AuthorizationConsentInputDto;
import com.machine.service.iam.auth.dao.IIamOauth2AuthorizationConsentDao;
import com.machine.service.iam.auth.dao.mapper.IamOauth2AuthorizationConsentMapper;
import com.machine.service.iam.auth.dao.mapper.entity.IamOauth2AuthorizationConsentEntity;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class IamOauth2AuthorizationConsentDaoImpl implements IIamOauth2AuthorizationConsentDao {

    @Autowired
    private IamOauth2AuthorizationConsentMapper authorizationConsentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(IamOauth2AuthorizationConsentEntity entity) {
        UpdateWrapper<IamOauth2AuthorizationConsentEntity> oauth2AuthorizationConsentEntityUpdateWrapper = new UpdateWrapper<>();
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
    public void save(IamOauth2AuthorizationConsentEntity entity) {
        authorizationConsentMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(IamOauth2AuthorizationConsentEntity entity) {
        authorizationConsentMapper.deleteById(entity.getId());
    }

    @Override
    public IamOauth2AuthorizationConsentEntity findById(IamOauth2AuthorizationConsentInputDto dto) {
        return authorizationConsentMapper.selectOne(new QueryWrapper<IamOauth2AuthorizationConsentEntity>()
                .eq("registered_client_id", dto.getRegisteredClientId())
                .eq("principal_name", dto.getPrincipalName()));
    }
}
