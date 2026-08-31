package com.machine.service.iam.biam.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogDeleteInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserAccessLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserAccessLogListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

public interface IBIamUserAccessLogService {

    /**
     * 创建访问日志
     */
    String create(BIamUserAccessLogCreateInputDto inputDto);

    /**
     * 详情
     */
    BIamUserAccessLogDetailOutputDto detail(IdRequest request);

    /**
     * 分页查询
     */
    Page<BIamUserAccessLogListOutputDto> page(BIamUserAccessLogQueryPageInputDto inputDto);

    /**
     * 清理指定时间之前的日志（日志保留策略）
     */
    int deleteByCreateTimeBefore(BIamUserAccessLogDeleteInputDto inputDto);

}
