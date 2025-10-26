package com.machine.client.data.attachment;

import com.machine.client.data.attachment.dto.input.DataAttachmentBindTableNameInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCreateTemporaryInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentQueryPageInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentUpdateInputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentListOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/attachment",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataAttachmentClient {

    @PostMapping("create_temporary")
    String createTemporary(@RequestBody @Validated DataAttachmentCreateTemporaryInputDto inputDto);

    @PostMapping("update")
    void update(@RequestBody @Validated DataAttachmentUpdateInputDto inputDto);

    @PostMapping("bind_tableName")
    void bindTableName(@RequestBody @Validated DataAttachmentBindTableNameInputDto inputDto);

    @PostMapping("get_by_id")
    DataAttachmentDetailOutputDto getById(@RequestBody @Validated IdRequest request);

    @PostMapping("map_by_idSet")
    Map<String, DataAttachmentDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("select_page")
    PageResponse<DataAttachmentListOutputDto> selectPage(@RequestBody @Validated DataAttachmentQueryPageInputDto inputDto);
}


