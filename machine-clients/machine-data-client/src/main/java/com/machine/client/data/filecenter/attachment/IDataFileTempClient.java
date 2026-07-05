package com.machine.client.data.filecenter.attachment;

import com.machine.client.data.filecenter.attachment.dto.input.DataFileTempCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataFileTempDetailOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/file_center/file_temp",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataFileTempClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataFileTempCreateInputDto inputDto);

    @PostMapping("get_by_id")
    DataFileTempDetailOutputDto getById(@RequestBody @Validated IdRequest request);
}
