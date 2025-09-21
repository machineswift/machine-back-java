package com.machine.service.data.config.server;

import com.machine.client.data.config.IDataOpenapiConfigClient;
import com.machine.client.data.config.dto.DataSystemConfigDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/data/openapi_config")
public class DataOpenapiConfigServer implements IDataOpenapiConfigClient {

    public static final String CATEGORY = "OPENAPI";

    @Autowired
    private DataSystemConfigServer systemConfigServer;

    @Override
    @GetMapping("resource_blank_ip")
    public String resourceBlankIp() {
        String code = "RESOURCE_BLANK_IP";
        DataSystemConfigDto inputDto = new DataSystemConfigDto(CATEGORY, code);
        return systemConfigServer.getString(inputDto);
    }

}
