package com.machine.client.iam.biam.log;

import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogDeleteInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserAccessLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserAccessLogListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/user_access_log",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamUserAccessLogClient {

    @PostMapping("create")
    String create(@RequestBody @Validated BIamUserAccessLogCreateInputDto inputDto);

    @PostMapping("delete_by_createTime_before")
    int deleteByCreateTimeBefore(@RequestBody @Validated BIamUserAccessLogDeleteInputDto inputDto);

    @PostMapping("detail")
    BIamUserAccessLogDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("page")
    PageResponse<BIamUserAccessLogListOutputDto> page(@RequestBody @Validated BIamUserAccessLogQueryPageInputDto inputDto);

}
