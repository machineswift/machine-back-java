package com.machine.app.openapi.wechat.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "【微信/小程序模块】")
@RestController
@AllArgsConstructor
@RequestMapping("openapi/wechat/miniapp/portal")
@Slf4j
public class WechatMiniAppPortalController {
    private final WxMaService wxMaService;
    private final WxMaMessageRouter wxMaMessageRouter;

    @Operation(summary = "接收到来自微信服务器的认证消息")
    @GetMapping(path = "check_signature", produces = "text/plain;charset=utf-8")
    public String checkSignature(@RequestParam("appId") String appId,
                                 @RequestParam(name = "signature", required = false) String signature,
                                 @RequestParam(name = "timestamp", required = false) String timestamp,
                                 @RequestParam(name = "nonce", required = false) String nonce,
                                 @RequestParam(name = "echostr", required = false) String echostr) {
        log.info("接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
                signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%s]的配置，请核实！", appId));
        }

        if (wxMaService.checkSignature(timestamp, nonce, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return echostr;
        }
        WxMaConfigHolder.remove();//清理ThreadLocal
        return "非法请求";
    }

    @Operation(summary = "接收微信请求")
    @PostMapping(path = "process", produces = "application/xml; charset=UTF-8")
    public String process(@RequestParam("appId") String appId,
                          @RequestBody String requestBody,
                          @RequestParam(name = "msg_signature", required = false) String msgSignature,
                          @RequestParam(name = "encrypt_type", required = false) String encryptType,
                          @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam("timestamp") String timestamp,
                          @RequestParam("nonce") String nonce) {
        log.info("接收微信请求：[msg_signature=[{}], encrypt_type=[{}], signature=[{}]," +
                        " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                msgSignature, encryptType, signature, timestamp, nonce, requestBody);

        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%s]的配置，请核实！", appId));
        }

        final boolean isJson = Objects.equals(wxMaService.getWxMaConfig().getMsgDataFormat(),
                WxMaConstants.MsgDataFormat.JSON);
        if (StringUtils.isBlank(encryptType)) {
            // 明文传输的消息
            WxMaMessage inMessage;
            if (isJson) {
                inMessage = WxMaMessage.fromJson(requestBody);
            } else {//xml
                inMessage = WxMaMessage.fromXml(requestBody);
            }

            this.route(inMessage);
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "success";
        }

        if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            WxMaMessage inMessage;
            if (isJson) {
                inMessage = WxMaMessage.fromEncryptedJson(requestBody, wxMaService.getWxMaConfig());
            } else {//xml
                inMessage = WxMaMessage.fromEncryptedXml(requestBody, wxMaService.getWxMaConfig(),
                        timestamp, nonce, msgSignature);
            }

            this.route(inMessage);
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "success";
        }
        WxMaConfigHolder.remove();//清理ThreadLocal
        throw new RuntimeException("不可识别的加密类型：" + encryptType);
    }

    private void route(WxMaMessage message) {
        try {
            wxMaMessageRouter.route(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
