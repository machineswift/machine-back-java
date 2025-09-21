package com.machine.service.data.config.server;

import com.machine.client.data.config.IDataSystemConfigClient;
import com.machine.client.data.config.dto.DataSystemConfigDto;
import com.machine.service.data.config.service.IDataSystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/data/config")
public class DataSystemConfigServer implements IDataSystemConfigClient {

    @Autowired
    private IDataSystemConfigService configService;

    @Override
    @PostMapping("create")
    public void create(@Validated @RequestBody DataSystemConfigDto dto) {
        log.info("新增配置: {}", dto);
        configService.create(dto);
    }

    @Override
    @DeleteMapping("delete")
    public void delete(@Validated @RequestBody DataSystemConfigDto dto) {
        log.info("删除配置: {}", dto);
        configService.delete(dto);
    }

    @Override
    @PutMapping("update")
    public void update(@Validated @RequestBody DataSystemConfigDto dto) {
        log.info("修改配置: {}", dto);
        configService.update(dto);
    }

    @Override
    @PostMapping("exist")
    public boolean exist(@Validated @RequestBody DataSystemConfigDto dto) {
        DataSystemConfigDto conf = getByUk(dto);
        return conf != null;
    }

    @Override
    @PostMapping("boolValue")
    public Boolean getBool(@Validated @RequestBody DataSystemConfigDto dto) {
        DataSystemConfigDto conf = getByUk(dto);
        if (conf == null) {
            return null;
        }
        return Boolean.valueOf(conf.getContent());
    }

    @Override
    @PostMapping("boolValue/default")
    public Boolean getBoolOrElse(@Validated @RequestBody DataSystemConfigDto dto,
                                 Boolean defaultValue) {
        DataSystemConfigDto conf = getByUk(dto);
        if (conf == null) {
            return defaultValue;
        }
        return Boolean.valueOf(conf.getContent());
    }

    @Override
    @PostMapping("intValue")
    public Integer getInt(@Validated @RequestBody DataSystemConfigDto dto) {
        DataSystemConfigDto conf = getByUk(dto);
        if (conf == null) {
            return null;
        }
        return Integer.valueOf(conf.getContent());
    }

    @Override
    @PostMapping("intValue/default")
    public Integer getIntOrElse(@Validated @RequestBody DataSystemConfigDto dto,
                                Integer defaultValue) {
        DataSystemConfigDto conf = getByUk(dto);
        if (conf == null) {
            return defaultValue;
        }
        return Integer.valueOf(conf.getContent());
    }

    @Override
    @PostMapping("longValue")
    public Long getLong(@Validated @RequestBody DataSystemConfigDto dto) {
        DataSystemConfigDto conf = getByUk(dto);
        if (conf == null) {
            return null;
        }
        return Long.valueOf(conf.getContent());
    }

    @Override
    @PostMapping("longValue/default")
    public Long getLongOrElse(@Validated @RequestBody DataSystemConfigDto dto,
                              Long defaultValue) {
        DataSystemConfigDto conf = getByUk(dto);
        if (conf == null) {
            return defaultValue;
        }
        return Long.valueOf(conf.getContent());
    }

    @Override
    @PostMapping("stringValue")
    public String getString(@Validated @RequestBody DataSystemConfigDto dto) {
        DataSystemConfigDto conf = getByUk(dto);
        if (conf == null) {
            return null;
        }
        return conf.getContent();
    }

    @Override
    @PostMapping("stringValue/default")
    public String getStringOrElse(@Validated @RequestBody DataSystemConfigDto dto,
                                  String defaultValue) {
        DataSystemConfigDto conf = getByUk(dto);
        if (conf == null) {
            return defaultValue;
        }
        return conf.getContent();
    }


    private DataSystemConfigDto getByUk(DataSystemConfigDto dto) {
        return configService.getByUk(dto);
    }
}
