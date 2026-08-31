package com.machine.service.data.config.server;

import com.machine.client.data.config.IDataOpenapiConfigClient;
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

}
