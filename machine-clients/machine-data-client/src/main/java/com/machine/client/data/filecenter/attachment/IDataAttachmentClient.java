package com.machine.client.data.filecenter.attachment;

import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentWithCurrentFileInfoOutputDto;
import com.machine.sdk.base.config.OpenFeignMidTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/file_center/attachment",
        configuration = OpenFeignMidTimeConfig.class)
public interface IDataAttachmentClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataAttachmentCreateInputDto inputDto);

    @PostMapping("get_by_id")
    DataAttachmentDetailOutputDto getById(@RequestBody @Validated IdRequest request);

    @PostMapping("map_by_idSet")
    Map<String, DataAttachmentDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("get_by_attachmentId")
    DataAttachmentWithCurrentFileInfoOutputDto getCurrentByAttachmentId(@RequestBody @Validated IdRequest request);

    @PostMapping("map_by_attachmentIdSet")
    Map<String, DataAttachmentWithCurrentFileInfoOutputDto> mapCurrentByAttachmentIdSet(@RequestBody @Validated IdSetRequest request);

}


