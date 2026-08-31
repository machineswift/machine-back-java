package com.machine.service.iam.biam.identity.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.identity.dto.input.BIamOAuth2RegisteredClientPageQueryInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.service.iam.biam.identity.dao.IBIamOauth2RegisteredClientDao;
import com.machine.service.iam.biam.identity.dao.mapper.BIamOauth2RegisteredClientMapper;
import com.machine.service.iam.biam.identity.dao.mapper.entity.BIamOauth2RegisteredClientEntity;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Auth2RegisteredClient.BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Auth2RegisteredClient.BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_VERSION_KEY;

@Repository
public class BIamOauth2RegisteredClientDaoImpl implements IBIamOauth2RegisteredClientDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private BIamOauth2RegisteredClientMapper authTokenMapper;

    @Override
    public String insert(BIamOauth2RegisteredClientEntity entity) {
        authTokenMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int deleteById(String id) {
        deleteCache(id);
        return authTokenMapper.deleteById(id);
    }

    @Override
    public int update(BIamOauth2RegisteredClientEntity entity) {
        deleteCache(entity.getId());
        return authTokenMapper.updateById(entity);
    }

    @Override
    public int updateStatus(String id,
                            StatusEnum status) {
        deleteCache(id);

        BIamOauth2RegisteredClientEntity entity = new BIamOauth2RegisteredClientEntity();
        entity.setId(id);
        entity.setStatus(status);
        return authTokenMapper.updateById(entity);
    }

    @Override
    public int updateClientSecret(String id,
                                  String clientSecret) {
        deleteCache(id);

        BIamOauth2RegisteredClientEntity entity = new BIamOauth2RegisteredClientEntity();
        entity.setId(id);
        entity.setClientSecret(clientSecret);
        return authTokenMapper.updateById(entity);
    }

    @Override
    public List<String> allClientId(StatusEnum status) {
        return authTokenMapper.allClientId(status);
    }

    @Override
    public BIamOauth2RegisteredClientEntity findById(String id) {
        return authTokenMapper.selectById(id);
    }

    @Override
    public BIamOauth2RegisteredClientEntity findByClientId(String clientId) {
        Wrapper<BIamOauth2RegisteredClientEntity> wrapper = new LambdaQueryWrapper<BIamOauth2RegisteredClientEntity>()
                .eq(BIamOauth2RegisteredClientEntity::getClientId, clientId);
        return authTokenMapper.selectOne(wrapper);
    }

    @Override
    public BIamOauth2RegisteredClientEntity findByClientName(String clientName) {
        Wrapper<BIamOauth2RegisteredClientEntity> wrapper = new LambdaQueryWrapper<BIamOauth2RegisteredClientEntity>()
                .eq(BIamOauth2RegisteredClientEntity::getClientName, clientName);
        return authTokenMapper.selectOne(wrapper);
    }

    @Override
    public Page<BIamOauth2RegisteredClientEntity> selectPage(BIamOAuth2RegisteredClientPageQueryInputDto inputDto) {
        IPage<BIamOauth2RegisteredClientEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return authTokenMapper.selectPage(inputDto, page);
    }

    private void deleteCache(String id) {
        BIamOauth2RegisteredClientEntity entity = authTokenMapper.selectById(id);
        if (null == entity) {
            return;
        }

        customerRedisCommands.hdel(BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_VERSION_KEY, entity.getClientId());
        customerRedisCommands.del(BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_DATA + entity.getClientId());
    }
}
