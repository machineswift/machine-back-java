package com.machine.service.iam.auth.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.iam.auth.dto.IamAuthTokenAddDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.iam.auth.dao.IIamOauth2RegisteredClientDao;
import com.machine.service.iam.auth.dao.mapper.IamOauth2RegisteredClientMapper;
import com.machine.service.iam.auth.dao.mapper.entity.IamOauth2RegisteredClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class IamOauth2RegisteredClientDaoImpl implements IIamOauth2RegisteredClientDao {

    @Autowired
    private IamOauth2RegisteredClientMapper authTokenMapper;

    @Override
    public int add(IamAuthTokenAddDto dto) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(IamOauth2RegisteredClientEntity entity) {
        return authTokenMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(IamOauth2RegisteredClientEntity entity) {
        return authTokenMapper.updateById(entity);
    }

    @Override
    public List<String> allClientId(StatusEnum status) {
        return authTokenMapper.allClientId(status);
    }

    @Override
    public IamOauth2RegisteredClientEntity findById(String id) {
        return authTokenMapper.selectById(id);
    }

    @Override
    public IamOauth2RegisteredClientEntity findByClientId(String clientId) {
        Wrapper<IamOauth2RegisteredClientEntity> queryWrapper = new LambdaQueryWrapper<IamOauth2RegisteredClientEntity>()
                .eq(IamOauth2RegisteredClientEntity::getClientId, clientId);
        return authTokenMapper.selectOne(queryWrapper);
    }
}
