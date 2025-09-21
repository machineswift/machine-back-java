package com.machine.client.iam.user;

import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogAvailableOutputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/user_login_log",
        configuration = OpenFeignDefaultConfig.class)
public interface IIamUserLoginLogClient {

    @PostMapping("create")
    String create(@RequestBody @Validated IamUserLoginLogCreateInputDto inputDto);

    @PostMapping("detail")
    IamUserLoginLogDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @GetMapping("get_loginSuccess_by_userId")
    IamUserLoginLogDetailOutputDto getLoginSuccessByUserId(@RequestParam("userId") String userId);

    @GetMapping("get_loginSuccess_by_accessTokenId")
    IamUserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(@RequestParam("accessTokenId") String accessTokenId);

    @PostMapping("select_availableToken")
    List<IamUserLoginLogAvailableOutputDto> selectAvailableToken(@RequestBody @Validated IamUserLoginLogQueryAvailableInputDto inputDto);

    @PostMapping("page")
    PageResponse<IamUserLoginLogListOutputDto> page(@RequestBody @Validated IamUserLoginLogQueryPageInputDto inputDto);

}
