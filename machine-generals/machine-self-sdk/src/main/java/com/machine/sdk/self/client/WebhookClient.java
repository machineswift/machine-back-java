package com.machine.sdk.self.client;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.common.model.dto.data.WebHookInfoDto;
import com.machine.sdk.common.tool.AESUtil;
import com.machine.sdk.self.domain.WebHookEventRequestBody;
import com.machine.sdk.self.domain.WebHookResponseBody;
import com.machine.sdk.self.util.SelfOkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

@Slf4j
public class WebhookClient {

    /**
     * 调用对应  oauth2_registered_client 的回调地址
     */
    public static <T> WebHookResponseBody callBack(OkHttpClient okHttpClient,
                                                   WebHookInfoDto webHookInfo,
                                                   WebHookEventRequestBody<T> requestBody) {
        requestBody.setTraceId(TraceContext.traceId());
        String strBody = JSONUtil.toJsonStr(requestBody);
        String encryptStrBody = AESUtil.encrypt(strBody, AESUtil.reconstructSecretKey(webHookInfo.getSecret()));
        log.info("Webhook调用应用接口开始，url={} requestBody={} encryptRequestBody={}",
                webHookInfo.getCallBackUrl(), strBody, encryptStrBody);
        long startTime = System.currentTimeMillis();
        String responseContent = SelfOkHttpUtil.responseContentByPost(okHttpClient, webHookInfo.getCallBackUrl(), encryptStrBody);
        log.info("Webhook调用应用接口结束，executeTime={},responseContent={}",
                (System.currentTimeMillis() - startTime), responseContent);

        return JSONUtil.toBean(responseContent, WebHookResponseBody.class);
    }

}
