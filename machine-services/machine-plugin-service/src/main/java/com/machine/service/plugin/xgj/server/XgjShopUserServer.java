package com.machine.service.plugin.xgj.server;

import com.machine.client.plugin.xgj.IXgjShopUserClient;
import com.machine.service.plugin.xgj.service.IXgjShopUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/plugin/xgj_shop_user")
public class XgjShopUserServer implements IXgjShopUserClient {

    @Autowired
    private IXgjShopUserService xgjShopUserService;

}
