package com.machine.sdk.beisen.interceptor;

import com.machine.sdk.beisen.client.token.BeiSenTokenClient;
import com.machine.sdk.beisen.client.token.dto.input.BeiSenTokenInputDto;
import com.machine.sdk.beisen.client.token.dto.output.BeiSenTokenOutputDto;
import okhttp3.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class BeiSenTokenInterceptor implements Interceptor {

    private final String appKey;
    private final String appSecret;

    public BeiSenTokenInterceptor(String appKey,
                                  String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request request = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + getToken())
                .build();
        Response response = chain.proceed(request);

        if (HttpStatus.UNAUTHORIZED.value() == response.code()) {
            try (ResponseBody ignored = response.body()) {
                beiSenToken = null;
            }
            request = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + getToken())
                    .build();
            response = chain.proceed(request);
        }
        return response;
    }

    /**
     * 获取北森token
     */
    private String getToken() {
        if (null == beiSenToken) {
            synchronized (BeiSenTokenInterceptor.class) {
                if (null == beiSenToken) {
                    BeiSenTokenInputDto inputDto = new BeiSenTokenInputDto();
                    inputDto.setAppKey(appKey);
                    inputDto.setAppSecret(appSecret);
                    BeiSenTokenOutputDto outputDto = BeiSenTokenClient.getToken(inputDto);
                    beiSenToken = outputDto.getAccessToken();
                }
            }
        }
        return beiSenToken;
    }

    private static String beiSenToken = null;

}
