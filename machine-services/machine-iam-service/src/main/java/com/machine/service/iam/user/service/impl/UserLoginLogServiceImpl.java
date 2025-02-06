package com.machine.service.iam.user.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogAvailableOutputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogPageOutputDto;
import com.machine.service.iam.user.dao.IUserLoginLogDao;
import com.machine.service.iam.user.dao.mapper.entity.UserLoginLogEntity;
import com.machine.service.iam.user.service.IUserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserLoginLogServiceImpl implements IUserLoginLogService {

    @Autowired
    private IUserLoginLogDao userLoginLogDao;

    @Override
    public String create(IamUserLoginLogCreateInputDto inputDto) {
        UserLoginLogEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), UserLoginLogEntity.class);
        return userLoginLogDao.insert(insertEntity);
    }

    @Override
    public UserLoginLogDetailOutputDto getLoginSuccessByUserId(String userId) {
        UserLoginLogEntity entity = userLoginLogDao.getLoginSuccessByUserId(userId);
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), UserLoginLogDetailOutputDto.class);
    }

    @Override
    public UserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(String accessTokenId) {
        UserLoginLogEntity entity = userLoginLogDao.getLoginSuccessByAccessTokenId(accessTokenId);
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), UserLoginLogDetailOutputDto.class);
    }

    @Override
    public List<UserLoginLogAvailableOutputDto> selectAvailableToken(IamUserLoginLogQueryAvailableInputDto inputDto) {
        List<UserLoginLogEntity> entityList = userLoginLogDao.selectAvailableToken(inputDto);
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), UserLoginLogAvailableOutputDto.class);
    }

    @Override
    public Page<UserLoginLogPageOutputDto> page(IamUserLoginLogQueryPageInputDto inputDto) {
        Page<UserLoginLogEntity> page = userLoginLogDao.page(inputDto);
        Page<UserLoginLogPageOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), UserLoginLogPageOutputDto.class));
        return pageResult;
    }
}
