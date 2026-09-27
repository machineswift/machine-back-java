package com.machine.service.iam.biam.log.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.IBIamOperationLogClient;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamOperationLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamOperationLogListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.iam.biam.log.service.IBIamOperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/operation_log")
public class BIamOperationLogServer implements IBIamOperationLogClient {

    @Autowired
    private IBIamOperationLogService operationLogService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated BIamOperationLogCreateInputDto inputDto) {
        log.info("创建操作日志，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return operationLogService.create(inputDto);
    }

    @Override
    @PostMapping("detail")
    public BIamOperationLogDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return operationLogService.detail(request);
    }

    @Override
    @PostMapping("page")
    public PageResponse<BIamOperationLogListOutputDto> page(@RequestBody @Validated BIamOperationLogQueryPageInputDto inputDto) {
        Page<BIamOperationLogListOutputDto> pageResult = operationLogService.page(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

}
