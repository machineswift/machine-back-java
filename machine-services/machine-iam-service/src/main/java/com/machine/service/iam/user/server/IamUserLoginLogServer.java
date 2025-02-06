package com.machine.service.iam.user.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogAvailableOutputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogPageOutputDto;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.iam.user.service.IUserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/user_login_log")
public class IamUserLoginLogServer implements IIamUserLoginLogClient {

    @Autowired
    private IUserLoginLogService userLoginLogService;

    @Override
    @PostMapping("selectAvailableToken")
    public List<UserLoginLogAvailableOutputDto> selectAvailableToken(@RequestBody @Validated IamUserLoginLogQueryAvailableInputDto inputDto) {
        return userLoginLogService.selectAvailableToken(inputDto);
    }

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated IamUserLoginLogCreateInputDto inputDto) {
        log.info("创建用户登录日志，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userLoginLogService.create(inputDto);
    }

    @GetMapping("getLoginSuccessByUserId")
    public UserLoginLogDetailOutputDto getLoginSuccessByUserId(@RequestParam("userId") String userId) {
        return userLoginLogService.getLoginSuccessByUserId(userId);
    }

    @Override
    @GetMapping("getLoginSuccessByAuthTokenId")
    public UserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(@RequestParam("accessTokenId") String accessTokenId) {
        return userLoginLogService.getLoginSuccessByAccessTokenId(accessTokenId);
    }

    @Override
    @PostMapping("page")
    public PageResponse<UserLoginLogPageOutputDto> page(@RequestBody @Validated IamUserLoginLogQueryPageInputDto inputDto) {
        Page<UserLoginLogPageOutputDto> pageResult = userLoginLogService.page(inputDto);

        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
