package com.machine.service.iam.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogAvailableOutputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IIamUserLoginLogService {

    String create(IamUserLoginLogCreateInputDto inputDto);

    IamUserLoginLogDetailOutputDto detail(IdRequest request);

    IamUserLoginLogDetailOutputDto getLoginSuccessByUserId(String userId);

    IamUserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(String accessTokenId);

    List<IamUserLoginLogAvailableOutputDto> selectAvailableToken(IamUserLoginLogQueryAvailableInputDto inputDto);

    Page<IamUserLoginLogListOutputDto> page(IamUserLoginLogQueryPageInputDto inputDto);

}
