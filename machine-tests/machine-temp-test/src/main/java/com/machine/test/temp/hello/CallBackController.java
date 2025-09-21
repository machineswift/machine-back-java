package com.machine.test.temp.hello;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.common.tool.AESUtil;
import com.machine.sdk.self.domain.WebHookEventRequestBody;
import com.machine.sdk.self.domain.WebHookResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("temp/hello")
public class CallBackController {

    private static final String secret = "enyAALFiSrgSH1odCXMS/XTJjbl2lRC1BWLSxFDYF0U=";

    @PostMapping("call_back")
    public <T> WebHookResponseBody callBack(@RequestBody String encryptBody) {
        Long timestamp = System.currentTimeMillis();
        log.info("应用接收到WebHook回调数据,encryptBody={}", JSONUtil.toJsonStr(encryptBody));

        String decrypt = AESUtil.decrypt(encryptBody, AESUtil.reconstructSecretKey(secret));
        var requestBody = JSONUtil.toBean(decrypt, WebHookEventRequestBody.class);

        /*
         * 处理业务逻辑 XXXX
         */

        WebHookResponseBody responseBody = new WebHookResponseBody();
        responseBody.setStatus(HttpStatus.OK.value());
        responseBody.setCode("SUCCESS");
        responseBody.setTimestamp(timestamp);
        responseBody.setTraceId(requestBody.getTraceId());

        log.info("应用成功处理WebHook回调数据,body={}", JSONUtil.toJsonStr(requestBody));
        return responseBody;
    }
}