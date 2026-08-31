package com.machine.service.iam.biam.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamOperationLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamOperationLogListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

public interface IBIamOperationLogService {

    /**
     * 新增操作日志
     */
    String create(BIamOperationLogCreateInputDto inputDto);

    /**
     * 详情
     */
    BIamOperationLogDetailOutputDto detail(IdRequest request);

    /**
     * 分页查询
     */
    Page<BIamOperationLogListOutputDto> page(BIamOperationLogQueryPageInputDto inputDto);
}
