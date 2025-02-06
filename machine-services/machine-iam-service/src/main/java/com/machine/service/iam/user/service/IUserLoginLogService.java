package com.machine.service.iam.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogAvailableOutputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogPageOutputDto;

import java.util.List;

public interface IUserLoginLogService {

    String create(IamUserLoginLogCreateInputDto inputDto);

    UserLoginLogDetailOutputDto getLoginSuccessByUserId(String userId);

    UserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(String accessTokenId);

    List<UserLoginLogAvailableOutputDto> selectAvailableToken(IamUserLoginLogQueryAvailableInputDto inputDto);

    Page<UserLoginLogPageOutputDto> page(IamUserLoginLogQueryPageInputDto inputDto);

}
