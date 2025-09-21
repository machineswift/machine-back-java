package com.machine.client.data.config;

import com.machine.client.data.config.dto.DataSystemConfigDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/config",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataSystemConfigClient {

    @PostMapping("create")
    void create(@Validated @RequestBody DataSystemConfigDto dto);

    @DeleteMapping("delete")
    void delete(@Validated @RequestBody DataSystemConfigDto dto);

    @PutMapping("update")
    void update(@Validated @RequestBody DataSystemConfigDto inputDto);

    @PostMapping("exist")
    boolean exist(@Validated @RequestBody DataSystemConfigDto inputDto);

    @PostMapping("boolValue")
    Boolean getBool(@Validated @RequestBody DataSystemConfigDto inputDto);

    @PostMapping("boolValue/default")
    Boolean getBoolOrElse(@Validated @RequestBody DataSystemConfigDto inputDto,
                          @RequestParam("defaultValue") Boolean defaultValue);

    @PostMapping("intValue")
    Integer getInt(@Validated @RequestBody DataSystemConfigDto inputDto);

    @PostMapping("intValue/default")
    Integer getIntOrElse(@Validated @RequestBody DataSystemConfigDto inputDto,
                         @RequestParam(value = "defaultValue") Integer defaultValue);

    @PostMapping("longValue")
    Long getLong(@Validated @RequestBody DataSystemConfigDto inputDto);

    @PostMapping("longValue/default")
    Long getLongOrElse(@Validated @RequestBody DataSystemConfigDto inputDto,
                       @RequestParam("defaultValue") Long defaultValue);

    @PostMapping("stringValue")
    String getString(@Validated @RequestBody DataSystemConfigDto inputDto);

    @PostMapping("stringValue/default")
    String getStringOrElse(@Validated @RequestBody DataSystemConfigDto inputDto,
                           @RequestParam("defaultValue") String defaultValue);
}
