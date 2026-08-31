package com.machine.service.iam.biam.log.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryPageInputDto;
import com.machine.service.iam.biam.log.dao.IBIamUserLoginLogDao;
import com.machine.service.iam.biam.log.dao.mapper.BIamUserLoginLogMapper;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamUserLoginLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BIamUserLoginLogDaoImpl implements IBIamUserLoginLogDao {

    @Autowired
    private BIamUserLoginLogMapper userLoginLogMapper;

    @Override
    public String insert(BIamUserLoginLogEntity insertEntity) {
        userLoginLogMapper.insert(insertEntity);
        return insertEntity.getUserId();
    }

    @Override
    public BIamUserLoginLogEntity getById(String id) {
        return userLoginLogMapper.selectById(id);
    }

    @Override
    public BIamUserLoginLogEntity getLoginSuccessByUserId(String userId) {
        return userLoginLogMapper.getLoginSuccessByUserId(userId);
    }

    @Override
    public BIamUserLoginLogEntity getLoginSuccessByAccessTokenId(String accessTokenId) {
        return userLoginLogMapper.getLoginSuccessByAccessTokenId(accessTokenId);
    }

    @Override
    public List<BIamUserLoginLogEntity> selectAvailableToken(BIamUserLoginLogQueryAvailableInputDto inputDto) {
        inputDto.setCurrentTimeMillis(System.currentTimeMillis());
        return userLoginLogMapper.selectAvailableToken(inputDto);
    }

    @Override
    public Page<BIamUserLoginLogEntity> page(BIamUserLoginLogQueryPageInputDto inputDto) {
        IPage<BIamUserLoginLogEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userLoginLogMapper.selectPage(inputDto, page);
    }
}
