package com.machine.service.iam.biam.auth.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.machine.client.iam.biam.auth.dto.input.BIamOauth2AuthorizationConsentInputDto;
import com.machine.service.iam.biam.auth.dao.IBIamOauth2AuthorizationConsentDao;
import com.machine.service.iam.biam.auth.dao.mapper.BIamOauth2AuthorizationConsentMapper;
import com.machine.service.iam.biam.auth.dao.mapper.entity.BIamOauth2AuthorizationConsentEntity;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BIamOauth2AuthorizationConsentDaoImpl implements IBIamOauth2AuthorizationConsentDao {

    @Autowired
    private BIamOauth2AuthorizationConsentMapper authorizationConsentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BIamOauth2AuthorizationConsentEntity entity) {
        UpdateWrapper<BIamOauth2AuthorizationConsentEntity> oauth2AuthorizationConsentEntityUpdateWrapper = new UpdateWrapper<>();
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
    public void save(BIamOauth2AuthorizationConsentEntity entity) {
        authorizationConsentMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(BIamOauth2AuthorizationConsentEntity entity) {
        authorizationConsentMapper.deleteById(entity.getId());
    }

    @Override
    public BIamOauth2AuthorizationConsentEntity findById(BIamOauth2AuthorizationConsentInputDto dto) {
        return authorizationConsentMapper.selectOne(new QueryWrapper<BIamOauth2AuthorizationConsentEntity>()
                .eq("registered_client_id", dto.getRegisteredClientId())
                .eq("principal_name", dto.getPrincipalName()));
    }
}
