package com.machine.service.iam.biam.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogAvailableOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

public interface IBIamUserLoginLogService {

    String create(BIamUserLoginLogCreateInputDto inputDto);

    BIamUserLoginLogDetailOutputDto detail(IdRequest request);

    BIamUserLoginLogDetailOutputDto getLoginSuccessByUserId(String userId);

    BIamUserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(String accessTokenId);

    List<BIamUserLoginLogAvailableOutputDto> selectAvailableToken(BIamUserLoginLogQueryAvailableInputDto inputDto);

    Page<BIamUserLoginLogListOutputDto> page(BIamUserLoginLogQueryPageInputDto inputDto);

}
