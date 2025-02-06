package com.machine.starter.wechat.cp.handler;

import com.machine.starter.wechat.cp.builder.WechatCpTextBuilder;
import com.machine.starter.wechat.cp.utils.WechatCpJsonUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 通讯录变更事件处理器.
 */
@Slf4j
@Component
public class WechatCpContactChangeHandler extends WechatCpAbstractHandler {

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService cpService,
                                    WxSessionManager sessionManager) {
        String content = "收到通讯录变更事件，内容：" + WechatCpJsonUtils.toJson(wxMessage);
        log.info(content);

        return new WechatCpTextBuilder().build(content, wxMessage, cpService);
    }

}
