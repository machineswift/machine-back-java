package com.machine.sdk.self.util;

import com.machine.sdk.self.exception.SelfSdkException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class SelfOkHttpUtil {

    private static final MediaType JSON = MediaType.parse(APPLICATION_JSON_VALUE);

    public static String responseContentByPost(OkHttpClient okHttpClient,
                                               String url,
                                               String body) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, body))
                .build();
        return getString(okHttpClient, request);
    }

    private static String getString(OkHttpClient okHttpClient,
                                    Request request) {
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == HttpStatus.OK.value()) {
                return response.body().string();
            } else {
                log.error("Webhook http请求状态异常，API-{}-{},url={} responseBody={}",
                        request.method(), response.code(), request.url(), response.body());
                throw new SelfSdkException("sdk.self.http.wrongHttpStatus", "Webhook http请求状态异常");
            }
        } catch (IOException e) {
            throw new SelfSdkException("sdk.self.http.iOException", "Webhook http请求IO异常", e);
        }
    }
}
