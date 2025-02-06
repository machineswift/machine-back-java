package com.machine.client.iam.user;

import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogAvailableOutputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogPageOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/user_login_log", configuration = OpenFeignDefaultConfig.class)
public interface IIamUserLoginLogClient {

    @PostMapping("create")
    String create(@RequestBody @Validated IamUserLoginLogCreateInputDto inputDto);

    @GetMapping("get_loginSuccess_by_userId")
    UserLoginLogDetailOutputDto getLoginSuccessByUserId(@RequestParam("userId") String userId);

    @GetMapping("get_loginSuccess_by_accessTokenId")
    UserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(@RequestParam("accessTokenId") String accessTokenId);

    @PostMapping("select_availableToken")
    List<UserLoginLogAvailableOutputDto> selectAvailableToken(@RequestBody @Validated IamUserLoginLogQueryAvailableInputDto inputDto);

    @PostMapping("page")
    PageResponse<UserLoginLogPageOutputDto> page(@RequestBody @Validated IamUserLoginLogQueryPageInputDto inputDto);

}
