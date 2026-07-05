package com.machine.client.data.filecenter.attachment;

import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogPageInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentOperationLogListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
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

    @PostMapping("select_page")
    PageResponse<DataAttachmentOperationLogListOutputDto> selectPage(@RequestBody @Validated DataAttachmentOperationLogPageInputDto inputDto);
}
