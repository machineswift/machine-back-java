package com.machine.service.iam.biam.identity.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.iam.biam.identity.dao.IBIamOauth2AuthorizationDao;
import com.machine.service.iam.biam.identity.dao.mapper.BIamOauth2AuthorizationMapper;
import com.machine.service.iam.biam.identity.dao.mapper.entity.BIamOauth2AuthorizationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BIamOauth2AuthorizationDaoImpl implements IBIamOauth2AuthorizationDao {

    @Autowired
    private BIamOauth2AuthorizationMapper authorizationMapper;

    @Override
    public int save(BIamOauth2AuthorizationEntity entity) {
        return authorizationMapper.insert(entity);
    }

    @Override
    public int update(BIamOauth2AuthorizationEntity entity) {
        return authorizationMapper.updateById(entity);
    }

    @Override
    public boolean existsById(String id) {
        Wrapper<BIamOauth2AuthorizationEntity> wrapper = new LambdaQueryWrapper<BIamOauth2AuthorizationEntity>()
                .eq(BIamOauth2AuthorizationEntity::getId, id);
        return authorizationMapper.exists(wrapper);
    }

    @Override
    public BIamOauth2AuthorizationEntity findById(String id) {
        return authorizationMapper.selectById(id);
    }

    @Override
    public void remove(String id) {
        authorizationMapper.deleteById(id);
    }

    @Override
    public BIamOauth2AuthorizationEntity findByToken(BIamOauth2AuthorizationEntity entity) {
        LambdaQueryWrapper<BIamOauth2AuthorizationEntity> wrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotEmpty(entity.getState())) {
            wrapper.eq(BIamOauth2AuthorizationEntity::getState, entity.getState());
        }
        if (StrUtil.isNotEmpty(entity.getAuthorizationCodeValue())) {
            wrapper.eq(BIamOauth2AuthorizationEntity::getAuthorizationCodeValue, entity.getAuthorizationCodeValue());
        }
        if (StrUtil.isNotEmpty(entity.getAccessTokenValue())) {
            wrapper.eq(BIamOauth2AuthorizationEntity::getAccessTokenValue, entity.getAccessTokenValue());
        }
        if (StrUtil.isNotEmpty(entity.getOidcIdTokenValue())) {
            wrapper.eq(BIamOauth2AuthorizationEntity::getOidcIdTokenValue, entity.getOidcIdTokenValue());
        }
        if (StrUtil.isNotEmpty(entity.getRefreshTokenValue())) {
            wrapper.eq(BIamOauth2AuthorizationEntity::getRefreshTokenValue, entity.getRefreshTokenValue());
        }
        if (StrUtil.isNotEmpty(entity.getUserCodeValue())) {
            wrapper.eq(BIamOauth2AuthorizationEntity::getUserCodeValue, entity.getUserCodeValue());
        }
        if (StrUtil.isNotEmpty(entity.getDeviceCodeValue())) {
            wrapper.eq(BIamOauth2AuthorizationEntity::getDeviceCodeValue, entity.getDeviceCodeValue());
        }

        return authorizationMapper.selectOne(wrapper);
    }

}
