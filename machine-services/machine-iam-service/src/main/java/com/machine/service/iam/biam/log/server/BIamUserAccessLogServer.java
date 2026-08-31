package com.machine.service.iam.biam.log.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.IBIamUserAccessLogClient;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogDeleteInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserAccessLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserAccessLogListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.iam.biam.log.service.IBIamUserAccessLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/user_access_log")
public class BIamUserAccessLogServer implements IBIamUserAccessLogClient {

    @Autowired
    private IBIamUserAccessLogService userAccessLogService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated BIamUserAccessLogCreateInputDto inputDto) {
        log.info("创建用户访问日志，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userAccessLogService.create(inputDto);
    }

    @Override
    @PostMapping("delete_by_createTime_before")
    public int deleteByCreateTimeBefore(@RequestBody @Validated BIamUserAccessLogDeleteInputDto inputDto) {
        return userAccessLogService.deleteByCreateTimeBefore(inputDto);
    }

    @Override
    @PostMapping("detail")
    public BIamUserAccessLogDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return userAccessLogService.detail(request);
    }

    @Override
    @PostMapping("page")
    public PageResponse<BIamUserAccessLogListOutputDto> page(@RequestBody @Validated BIamUserAccessLogQueryPageInputDto inputDto) {
        Page<BIamUserAccessLogListOutputDto> pageResult = userAccessLogService.page(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

}
