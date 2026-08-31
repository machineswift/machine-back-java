package com.machine.client.iam.biam.log;

import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogAvailableOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/user_login_log",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamUserLoginLogClient {

    @PostMapping("create")
    String create(@RequestBody @Validated BIamUserLoginLogCreateInputDto inputDto);

    @PostMapping("detail")
    BIamUserLoginLogDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @GetMapping("get_loginSuccess_by_userId")
    BIamUserLoginLogDetailOutputDto getLoginSuccessByUserId(@RequestParam("userId") String userId);

    @GetMapping("get_loginSuccess_by_accessTokenId")
    BIamUserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(@RequestParam("accessTokenId") String accessTokenId);

    @PostMapping("select_availableToken")
    List<BIamUserLoginLogAvailableOutputDto> selectAvailableToken(@RequestBody @Validated BIamUserLoginLogQueryAvailableInputDto inputDto);

    @PostMapping("page")
    PageResponse<BIamUserLoginLogListOutputDto> page(@RequestBody @Validated BIamUserLoginLogQueryPageInputDto inputDto);

}



