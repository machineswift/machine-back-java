package com.machine.service.iam.user.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.service.iam.user.dao.IUserLoginLogDao;
import com.machine.service.iam.user.dao.mapper.IUserLoginLogMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserLoginLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserLoginLogDaoImpl implements IUserLoginLogDao {

    @Autowired
    private IUserLoginLogMapper userLoginLogMapper;

    @Override
    public String insert(UserLoginLogEntity insertEntity) {
        userLoginLogMapper.insert(insertEntity);
        return insertEntity.getUserId();
    }

    @Override
    public UserLoginLogEntity getLoginSuccessByUserId(String userId) {
        return userLoginLogMapper.getLoginSuccessByUserId(userId);
    }

    @Override
    public UserLoginLogEntity getLoginSuccessByAccessTokenId(String accessTokenId) {
        return userLoginLogMapper.getLoginSuccessByAccessTokenId(accessTokenId);
    }

    @Override
    public List<UserLoginLogEntity> selectAvailableToken(IamUserLoginLogQueryAvailableInputDto inputDto) {
        inputDto.setCurrentTimeMillis(System.currentTimeMillis());
        return userLoginLogMapper.selectAvailableToken(inputDto);
    }

    @Override
    public Page<UserLoginLogEntity> page(IamUserLoginLogQueryPageInputDto inputDto) {
        IPage<UserLoginLogEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userLoginLogMapper.page(inputDto, page);
    }
}
