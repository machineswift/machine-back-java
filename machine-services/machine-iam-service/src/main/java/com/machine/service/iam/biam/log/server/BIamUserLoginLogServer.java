package com.machine.service.iam.biam.log.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.IBIamUserLoginLogClient;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogAvailableOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.iam.biam.log.service.IBIamUserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/user_login_log")
public class BIamUserLoginLogServer implements IBIamUserLoginLogClient {

    @Autowired
    private IBIamUserLoginLogService userLoginLogService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated BIamUserLoginLogCreateInputDto inputDto) {
        log.info("创建用户登录日志，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userLoginLogService.create(inputDto);
    }

    @Override
    @PostMapping("detail")
    public BIamUserLoginLogDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return userLoginLogService.detail(request);
    }

    @GetMapping("get_loginSuccess_by_userId")
    public BIamUserLoginLogDetailOutputDto getLoginSuccessByUserId(@RequestParam("userId") String userId) {
        return userLoginLogService.getLoginSuccessByUserId(userId);
    }

    @Override
    @GetMapping("get_loginSuccess_by_accessTokenId")
    public BIamUserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(@RequestParam("accessTokenId") String accessTokenId) {
        return userLoginLogService.getLoginSuccessByAccessTokenId(accessTokenId);
    }

    @Override
    @PostMapping("select_availableToken")
    public List<BIamUserLoginLogAvailableOutputDto> selectAvailableToken(@RequestBody @Validated BIamUserLoginLogQueryAvailableInputDto inputDto) {
        return userLoginLogService.selectAvailableToken(inputDto);
    }

    @Override
    @PostMapping("page")
    public PageResponse<BIamUserLoginLogListOutputDto> page(@RequestBody @Validated BIamUserLoginLogQueryPageInputDto inputDto) {
        Page<BIamUserLoginLogListOutputDto> pageResult = userLoginLogService.page(inputDto);

        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
