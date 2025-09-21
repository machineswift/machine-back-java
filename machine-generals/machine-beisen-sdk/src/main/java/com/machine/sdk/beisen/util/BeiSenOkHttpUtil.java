package com.machine.sdk.beisen.util;

import com.machine.sdk.beisen.exception.BeiSenSdkException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static com.machine.sdk.beisen.constant.BeiSenConstant.BEI_SEN_API_BASE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class BeiSenOkHttpUtil {

    private static final MediaType JSON = MediaType.parse(APPLICATION_JSON_VALUE);

    public static String responseContentByPost(OkHttpClient okHttpClient,
                                               String url,
                                               String body) {
        Request request = new Request.Builder()
                .url(BEI_SEN_API_BASE_URL + url)
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
                log.error("北森http请求状态异常，API-{}-{},url={} responseBody={}",
                        request.method(), response.code(), request.url(), response.body());
                throw new BeiSenSdkException("sdk.beiSen.http.wrongHttpStatus", "北森http请求状态异常");
            }
        } catch (IOException e) {
            throw new BeiSenSdkException("sdk.beiSen.http.iOException", "北森http请求IO异常", e);
        }
    }
}
