package com.machine.app.openapi.wechat.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.machine.starter.wechat.miniapp.utils.WechatMiniAppJsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;


@Tag(name = "【微信/小程序模块】用户接口")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("openapi/wechat/miniapp/user")
public class WechatMiniAppUserController {
    private final WxMaService wxMaService;

    @Operation(summary = "登陆接口")
    @GetMapping("login")
    public String login(@RequestParam("appId") String appId, String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }

        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%s]的配置，请核实！", appId));
        }

        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return WechatMiniAppJsonUtils.toJson(session);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return e.toString();
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }

    @Operation(summary = "获取用户信息接口")
    @GetMapping("info")
    public String info(@RequestParam("appId") String appId, String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%s]的配置，请核实！", appId));
        }

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return WechatMiniAppJsonUtils.toJson(userInfo);
    }

    @Operation(summary = "获取用户绑定手机号信息")
    @GetMapping("phone")
    public String phone(@RequestParam("appId") String appId, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%s]的配置，请核实！", appId));
        }

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return WechatMiniAppJsonUtils.toJson(phoneNoInfo);
    }

}
