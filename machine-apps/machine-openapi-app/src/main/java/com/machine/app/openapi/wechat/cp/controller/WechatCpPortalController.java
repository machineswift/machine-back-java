package com.machine.app.openapi.wechat.cp.controller;

import com.machine.starter.wechat.cp.config.WechatCpConfiguration;
import com.machine.starter.wechat.cp.utils.WechatCpJsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "【微信/企业微信模块】")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("openapi/wechat/cp/portal")
public class WechatCpPortalController {
    private final StringRedisTemplate stringRedisTemplate;

    @PostMapping("/test")
    public String test(@RequestParam(name = "corpId") String corpId,
                       @RequestParam(name = "userId") String userId) throws WxErrorException {

        String s = stringRedisTemplate.opsForValue().get("test");
        log.info("data is {}", s);

        final val wxCpService = WechatCpConfiguration.getCpService(corpId, 10001);
        List<String> list = wxCpService.getExternalContactService().listExternalContacts(userId);

        return list.toString();
    }

    @Operation(summary = "接收到来自微信服务器的认证消息")
    @GetMapping(path = "check_signature", produces = "text/plain;charset=utf-8")
    public String checkSignature(@RequestParam(name = "corpId") String corpId,
                                 @RequestParam(name = "agentId") Integer agentId,
                                 @RequestParam(name = "msg_signature", required = false) String signature,
                                 @RequestParam(name = "timestamp", required = false) String timestamp,
                                 @RequestParam(name = "nonce", required = false) String nonce,
                                 @RequestParam(name = "echostr", required = false) String echostr) {
        log.info("接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
                signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        final WxCpService wxCpService = WechatCpConfiguration.getCpService(corpId, agentId);
        if (wxCpService == null) {
            throw new IllegalArgumentException(String.format("未找到对应agentId=[%d]的配置，请核实！", agentId));
        }

        if (wxCpService.checkSignature(signature, timestamp, nonce, echostr)) {
            return new WxCpCryptUtil(wxCpService.getWxCpConfigStorage()).decrypt(echostr);
        }

        return "非法请求";
    }


    @Operation(summary = "接收微信请求")
    @PostMapping(path = "process", produces = "application/xml; charset=UTF-8")
    public String process(@RequestParam(name = "corpId") String corpId,
                          @RequestParam(name = "agentId") Integer agentId,
                          @RequestBody String requestBody,
                          @RequestParam("msg_signature") String signature,
                          @RequestParam("timestamp") String timestamp,
                          @RequestParam("nonce") String nonce) {
        log.info("接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, timestamp, nonce, requestBody);

        final WxCpService wxCpService = WechatCpConfiguration.getCpService(corpId, agentId);
        if (wxCpService == null) {
            throw new IllegalArgumentException(String.format("未找到对应agentId=[%d]的配置，请核实！", agentId));
        }

        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(requestBody, wxCpService.getWxCpConfigStorage(),
                timestamp, nonce, signature);
        log.debug("\n消息解密后内容为：\n{} ", WechatCpJsonUtils.toJson(inMessage));
        WxCpXmlOutMessage outMessage = this.route(corpId, agentId, inMessage);
        if (outMessage == null) {
            return "";
        }

        String out = outMessage.toEncryptedXml(wxCpService.getWxCpConfigStorage());
        log.debug("\n组装回复信息：{}", out);
        return out;
    }

    private WxCpXmlOutMessage route(String corpId, Integer agentId, WxCpXmlMessage message) {
        try {
            return WechatCpConfiguration.getRouters().get(corpId + agentId).route(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }


}
