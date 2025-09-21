package com.machine.service.plugin.yunxi.server;

import com.machine.client.plugin.yunxi.IYunXiCubeShopClient;
import com.machine.service.plugin.yunxi.service.IYunXiCubeShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/plugin/yunxi_cube_shop")
public class YunXiCubeShopServer implements IYunXiCubeShopClient {

    @Autowired
    private IYunXiCubeShopService cubeShopService;
}
