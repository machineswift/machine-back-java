package com.machine.client.data.config;

import com.machine.client.data.config.dto.SystemConfigDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/config",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataSystemConfigClient {

    @PostMapping("create")
    void create(@Validated @RequestBody SystemConfigDto dto);

    @DeleteMapping("delete")
    void delete(@Validated @RequestBody SystemConfigDto dto);

    @PutMapping("update")
    void update(@Validated @RequestBody SystemConfigDto inputDto);

    @PostMapping("exist")
    boolean exist(@Validated @RequestBody SystemConfigDto inputDto);

    @PostMapping("boolValue")
    Boolean getBool(@Validated @RequestBody SystemConfigDto inputDto);

    @PostMapping("boolValue/default")
    Boolean getBoolOrElse(@Validated @RequestBody SystemConfigDto inputDto,
                          @RequestParam("defaultValue") Boolean defaultValue);

    @PostMapping("intValue")
    Integer getInt(@Validated @RequestBody SystemConfigDto inputDto);

    @PostMapping("intValue/default")
    Integer getIntOrElse(@Validated @RequestBody SystemConfigDto inputDto,
                         @RequestParam(value = "defaultValue") Integer defaultValue);

    @PostMapping("longValue")
    Long getLong(@Validated @RequestBody SystemConfigDto inputDto);

    @PostMapping("longValue/default")
    Long getLongOrElse(@Validated @RequestBody SystemConfigDto inputDto,
                       @RequestParam("defaultValue") Long defaultValue);

    @PostMapping("stringValue")
    String getString(@Validated @RequestBody SystemConfigDto inputDto);

    @PostMapping("stringValue/default")
    String getStringOrElse(@Validated @RequestBody SystemConfigDto inputDto,
                           @RequestParam("defaultValue") String defaultValue);
}
