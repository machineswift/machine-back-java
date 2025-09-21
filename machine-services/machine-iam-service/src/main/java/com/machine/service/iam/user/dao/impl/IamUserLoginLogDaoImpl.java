package com.machine.service.iam.user.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.service.iam.user.dao.IIamUserLoginLogDao;
import com.machine.service.iam.user.dao.mapper.IamUserLoginLogMapper;
import com.machine.service.iam.user.dao.mapper.entity.IamUserLoginLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IamUserLoginLogDaoImpl implements IIamUserLoginLogDao {

    @Autowired
    private IamUserLoginLogMapper userLoginLogMapper;

    @Override
    public String insert(IamUserLoginLogEntity insertEntity) {
        userLoginLogMapper.insert(insertEntity);
        return insertEntity.getUserId();
    }

    @Override
    public IamUserLoginLogEntity getById(String id) {
        return userLoginLogMapper.selectById(id);
    }

    @Override
    public IamUserLoginLogEntity getLoginSuccessByUserId(String userId) {
        return userLoginLogMapper.getLoginSuccessByUserId(userId);
    }

    @Override
    public IamUserLoginLogEntity getLoginSuccessByAccessTokenId(String accessTokenId) {
        return userLoginLogMapper.getLoginSuccessByAccessTokenId(accessTokenId);
    }

    @Override
    public List<IamUserLoginLogEntity> selectAvailableToken(IamUserLoginLogQueryAvailableInputDto inputDto) {
        inputDto.setCurrentTimeMillis(System.currentTimeMillis());
        return userLoginLogMapper.selectAvailableToken(inputDto);
    }

    @Override
    public Page<IamUserLoginLogEntity> page(IamUserLoginLogQueryPageInputDto inputDto) {
        IPage<IamUserLoginLogEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userLoginLogMapper.selectPage(inputDto, page);
    }
}
