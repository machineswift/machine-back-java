package com.machine.service.iam.identity.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.identity.dto.input.IamOAuth2RegisteredClientPageQueryInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.service.iam.identity.dao.IIamOauth2RegisteredClientDao;
import com.machine.service.iam.identity.dao.mapper.IamOauth2RegisteredClientMapper;
import com.machine.service.iam.identity.dao.mapper.entiry.IamOauth2RegisteredClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IamOauth2RegisteredClientDaoImpl implements IIamOauth2RegisteredClientDao {

    @Autowired
    private IamOauth2RegisteredClientMapper authTokenMapper;

    @Override
    public String insert(IamOauth2RegisteredClientEntity entity) {
        authTokenMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(IamOauth2RegisteredClientEntity entity) {
        return authTokenMapper.updateById(entity);
    }

    @Override
    public int updateStatus(String id,
                            StatusEnum status) {
        IamOauth2RegisteredClientEntity entity = new IamOauth2RegisteredClientEntity();
        entity.setId(id);
        entity.setStatus(status);
        return authTokenMapper.updateById(entity);
    }

    @Override
    public int deleteById(String id) {
        return authTokenMapper.deleteById(id);
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
        Wrapper<IamOauth2RegisteredClientEntity> wrapper = new LambdaQueryWrapper<IamOauth2RegisteredClientEntity>()
                .eq(IamOauth2RegisteredClientEntity::getClientId, clientId);
        return authTokenMapper.selectOne(wrapper);
    }

    @Override
    public IamOauth2RegisteredClientEntity findByClientName(String clientName) {
        Wrapper<IamOauth2RegisteredClientEntity> wrapper = new LambdaQueryWrapper<IamOauth2RegisteredClientEntity>()
                .eq(IamOauth2RegisteredClientEntity::getClientName, clientName);
        return authTokenMapper.selectOne(wrapper);
    }

    @Override
    public Page<IamOauth2RegisteredClientEntity> selectPage(IamOAuth2RegisteredClientPageQueryInputDto inputDto) {
        IPage<IamOauth2RegisteredClientEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return authTokenMapper.selectPage(inputDto, page);
    }
}
