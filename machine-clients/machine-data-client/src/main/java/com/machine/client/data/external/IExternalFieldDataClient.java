package com.machine.client.data.external;

import com.machine.client.data.external.dto.input.ExternalFieldDataCreateInputDto;
import com.machine.client.data.external.dto.input.ExternalFieldDataDeleteInputDto;
import com.machine.client.data.external.dto.input.ExternalFieldDataGetValueInputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/external_field_data",
        configuration = OpenFeignDefaultConfig.class)
public interface IExternalFieldDataClient {

    @PostMapping("create")
    String create(@RequestBody @Validated ExternalFieldDataCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated ExternalFieldDataDeleteInputDto inputDto);

    @PostMapping("get_value")
    String getValue(@RequestBody @Validated ExternalFieldDataGetValueInputDto inputDto);
}
