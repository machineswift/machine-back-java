package com.machine.client.iam.biam.log;

import com.machine.client.iam.biam.log.dto.input.BIamOperationLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamOperationLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamOperationLogListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/operation_log",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamOperationLogClient {

    @PostMapping("create")
    String create(@RequestBody @Validated BIamOperationLogCreateInputDto inputDto);

    @PostMapping("detail")
    BIamOperationLogDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("page")
    PageResponse<BIamOperationLogListOutputDto> page(@RequestBody @Validated BIamOperationLogQueryPageInputDto inputDto);

}
