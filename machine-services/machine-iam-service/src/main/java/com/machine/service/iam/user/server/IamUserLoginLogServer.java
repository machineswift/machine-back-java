package com.machine.service.iam.user.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogAvailableOutputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.iam.user.service.IIamUserLoginLogService;
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
    private IIamUserLoginLogService userLoginLogService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated IamUserLoginLogCreateInputDto inputDto) {
        log.info("创建用户登录日志，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userLoginLogService.create(inputDto);
    }

    @Override
    @PostMapping("detail")
    public IamUserLoginLogDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return userLoginLogService.detail(request);
    }

    @GetMapping("get_loginSuccess_by_userId")
    public IamUserLoginLogDetailOutputDto getLoginSuccessByUserId(@RequestParam("userId") String userId) {
        return userLoginLogService.getLoginSuccessByUserId(userId);
    }

    @Override
    @GetMapping("get_loginSuccess_by_accessTokenId")
    public IamUserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(@RequestParam("accessTokenId") String accessTokenId) {
        return userLoginLogService.getLoginSuccessByAccessTokenId(accessTokenId);
    }

    @Override
    @PostMapping("select_availableToken")
    public List<IamUserLoginLogAvailableOutputDto> selectAvailableToken(@RequestBody @Validated IamUserLoginLogQueryAvailableInputDto inputDto) {
        return userLoginLogService.selectAvailableToken(inputDto);
    }

    @Override
    @PostMapping("page")
    public PageResponse<IamUserLoginLogListOutputDto> page(@RequestBody @Validated IamUserLoginLogQueryPageInputDto inputDto) {
        Page<IamUserLoginLogListOutputDto> pageResult = userLoginLogService.page(inputDto);

        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
