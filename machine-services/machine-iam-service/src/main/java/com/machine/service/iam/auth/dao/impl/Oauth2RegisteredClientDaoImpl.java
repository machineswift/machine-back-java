package com.machine.service.iam.auth.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.iam.auth.dto.AuthTokenAddDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.iam.auth.dao.IOauth2RegisteredClientDao;
import com.machine.service.iam.auth.dao.mapper.Oauth2RegisteredClientMapper;
import com.machine.service.iam.auth.dao.mapper.entity.Oauth2RegisteredClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class Oauth2RegisteredClientDaoImpl implements IOauth2RegisteredClientDao {

    @Autowired
    private Oauth2RegisteredClientMapper authTokenMapper;

    @Override
    public int add(AuthTokenAddDto dto) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(Oauth2RegisteredClientEntity entity) {
        return authTokenMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(Oauth2RegisteredClientEntity entity) {
        return authTokenMapper.updateById(entity);
    }

    @Override
    public List<String> allClientId(StatusEnum status) {
        return authTokenMapper.allClientId(status);
    }

    @Override
    public Oauth2RegisteredClientEntity findById(String id) {
        return authTokenMapper.selectById(id);
    }

    @Override
    public Oauth2RegisteredClientEntity findByClientId(String clientId) {
        Wrapper<Oauth2RegisteredClientEntity> queryWrapper = new LambdaQueryWrapper<Oauth2RegisteredClientEntity>()
                .eq(Oauth2RegisteredClientEntity::getClientId, clientId);
        return authTokenMapper.selectOne(queryWrapper);
    }
}
