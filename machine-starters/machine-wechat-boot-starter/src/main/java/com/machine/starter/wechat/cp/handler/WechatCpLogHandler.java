package com.machine.starter.wechat.cp.handler;

import com.machine.starter.wechat.cp.utils.WechatCpJsonUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class WechatCpLogHandler extends WechatCpAbstractHandler {
    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService cpService,
                                    WxSessionManager sessionManager) {
        log.info("\n接收到请求消息，内容：{}", WechatCpJsonUtils.toJson(wxMessage));
        return null;
    }

}
