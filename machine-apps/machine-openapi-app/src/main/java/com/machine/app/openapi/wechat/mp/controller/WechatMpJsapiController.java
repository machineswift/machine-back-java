package com.machine.app.openapi.wechat.mp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "【微信/公众号模块】")
@AllArgsConstructor
@RestController
@RequestMapping("openapi/wechat/mp/jsapi")
public class WechatMpJsapiController {
    private final WxMpService wxService;

    @Operation(summary = "创建调用jsapi时所需要的签名")
    @GetMapping("getJsapiTicket")
    public String getJsapiTicket(@RequestParam("appId") String appId) throws WxErrorException {
        final WxJsapiSignature jsapiSignature = this.wxService.switchoverTo(appId).createJsapiSignature("111");
        System.out.println(jsapiSignature);
        return this.wxService.getJsapiTicket(true);
    }
}
