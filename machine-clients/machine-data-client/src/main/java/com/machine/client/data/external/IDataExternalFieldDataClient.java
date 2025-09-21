package com.machine.client.data.external;

import com.machine.client.data.external.dto.input.DataExternalFieldDataCreateInputDto;
import com.machine.client.data.external.dto.input.DataExternalFieldDataDeleteInputDto;
import com.machine.client.data.external.dto.input.DataExternalFieldDataGetValueInputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/external_field_data",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataExternalFieldDataClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataExternalFieldDataCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated DataExternalFieldDataDeleteInputDto inputDto);

    @PostMapping("get_value")
    String getValue(@RequestBody @Validated DataExternalFieldDataGetValueInputDto inputDto);
}
