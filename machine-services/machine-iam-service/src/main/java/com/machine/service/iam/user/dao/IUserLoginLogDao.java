package com.machine.service.iam.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.service.iam.user.dao.mapper.entity.UserLoginLogEntity;

import java.util.List;

public interface IUserLoginLogDao {

    String insert(UserLoginLogEntity insertEntity);

    UserLoginLogEntity getLoginSuccessByUserId(String userId);

    UserLoginLogEntity getLoginSuccessByAccessTokenId(String accessTokenId);

    List<UserLoginLogEntity> selectAvailableToken(IamUserLoginLogQueryAvailableInputDto inputDto);

    Page<UserLoginLogEntity> page(IamUserLoginLogQueryPageInputDto inputDto);

}
