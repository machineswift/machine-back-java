package com.machine.client.data.file.attachment;

import com.machine.client.data.file.attachment.dto.output.DataFileDetailOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/file/file",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataFileClient {

    @PostMapping("get_by_id")
    DataFileDetailOutputDto getById(@RequestBody @Validated IdRequest request);

    @PostMapping("map_by_idSet")
    Map<String, DataFileDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);
}


