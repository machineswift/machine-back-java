package com.machine.client.data.filecenter.attachment;

import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionRollbackInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionUpdateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentVersionDetailOutputDto;
import com.machine.sdk.base.config.OpenFeignMidTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "machine-data-service",
        path = "machine-data-service/server/data/file_center/attachment_version",
        configuration = OpenFeignMidTimeConfig.class)
public interface IDataAttachmentVersionClient {

    @PostMapping("update")
    void update(@RequestBody @Validated DataAttachmentVersionUpdateInputDto inputDto);

    @PostMapping("rollback")
    void rollback(@RequestBody @Validated DataAttachmentVersionRollbackInputDto inputDto);

    @PostMapping("get_by_id")
    DataAttachmentVersionDetailOutputDto getById(@RequestBody @Validated IdRequest request);

    @PostMapping("map_by_idSet")
    Map<String, DataAttachmentVersionDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

}
