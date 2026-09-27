package com.machine.client.data.filecenter.attachment;

import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogQueryPageInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentOperationLogDetailOutputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentOperationLogListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/file_center/attachment_operation_log",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataAttachmentOperationLogClient {

    @PostMapping("create")
    void create(@RequestBody @Validated DataAttachmentOperationLogCreateInputDto inputDto);

    @PostMapping("detail")
    DataAttachmentOperationLogDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("select_page")
    PageResponse<DataAttachmentOperationLogListOutputDto> selectPage(@RequestBody @Validated DataAttachmentOperationLogQueryPageInputDto inputDto);
}
