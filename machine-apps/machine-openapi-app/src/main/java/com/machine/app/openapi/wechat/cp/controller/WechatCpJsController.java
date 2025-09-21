package com.machine.app.openapi.wechat.cp.controller;

import com.machine.starter.wechat.cp.config.WechatCpConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "【微信/企业微信模块】")
@RestController
@RequestMapping("openapi/wechat/cp/js")
public class WechatCpJsController {

    @PostMapping("getJsConf")
    @Operation(summary = "创建调用jsapi时所需要的签名")
    public Map getJsConf(@RequestParam("corpId") String corpId,
                         @RequestParam("agentId") Integer agentId,
                         String uri) throws WxErrorException {

        final WxCpService wxCpService = WechatCpConfiguration.getCpService(corpId, agentId);
        if (wxCpService == null) {
            throw new IllegalArgumentException(String.format("未找到对应agentId=[%d]的配置，请核实！", agentId));
        }

        WxJsapiSignature wxJsapiSignature = wxCpService.createJsapiSignature(uri);
        String signature = wxJsapiSignature.getSignature();
        String nonceStr = wxJsapiSignature.getNonceStr();
        long timestamp = wxJsapiSignature.getTimestamp();

        Map res = new HashMap<String, String>();
        res.put("appId", corpId); // 必填，企业微信的corpID
        res.put("timestamp", timestamp); // 必填，生成签名的时间戳
        res.put("nonceStr", nonceStr); // 必填，生成签名的随机串
        res.put("signature", signature); // 必填，签名，见 附录-JS-SDK使用权限签名算法
        return res;
    }


    public static String genNonce() {
        return bytesToHex(Long.toString(System.nanoTime()).getBytes(StandardCharsets.UTF_8));
    }

    public static String bytesToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
