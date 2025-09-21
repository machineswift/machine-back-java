package com.machine.sdk.beisen.client.token;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.beisen.client.token.dto.input.BeiSenTokenInputDto;
import com.machine.sdk.beisen.client.token.dto.output.BeiSenTokenOutputDto;
import com.machine.sdk.beisen.exception.BeiSenSdkException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static com.machine.sdk.beisen.constant.BeiSenApiPathConstant.Token.ACCESS_TOKEN;
import static com.machine.sdk.beisen.constant.BeiSenConstant.BEI_SEN_API_BASE_URL;
import static com.machine.sdk.beisen.constant.BeiSenConstant.BEI_SEN_TITLE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class BeiSenTokenClient {

    /**
     * 获取调用北森API接口的凭证
     * <a href="https://open.italent.cn/?_qrt=html#/open-document?menu=develop-guide"></a>
     */
    public static BeiSenTokenOutputDto getToken(BeiSenTokenInputDto inputDto) {
        log.info(BEI_SEN_TITLE + "北森API接口的凭证开始，inputDto={}", JSONUtil.toJsonStr(inputDto));

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse(APPLICATION_JSON_VALUE);
        RequestBody body = RequestBody.create(mediaType, JSONUtil.toJsonStr(inputDto));
        Request request = new Request.Builder()
                .url(BEI_SEN_API_BASE_URL + ACCESS_TOKEN)
                .post(body)
                .build();

        long startTime;
        Response response;
        String responseContent;
        try {
            startTime = System.currentTimeMillis();
            response = client.newCall(request).execute();
            responseContent = response.body().string();
        } catch (IOException e) {
            throw new BeiSenSdkException("sdk.beiSen.getToken.exception", BEI_SEN_TITLE + "获取token", e);
        }

        if (HttpStatus.OK.value() == response.code()) {
            log.info(BEI_SEN_TITLE + "北森API接口的凭证成功，executionTime={},response={}", (System.currentTimeMillis() - startTime), responseContent);
            return JSONUtil.toBean(responseContent, BeiSenTokenOutputDto.class);
        } else {
            log.error(BEI_SEN_TITLE + "北森API接口的凭证失败，executionTime={},response={}", (System.currentTimeMillis() - startTime), responseContent);
            throw new BeiSenSdkException("sdk.beiSen.getToken.httpError", BEI_SEN_TITLE + "获取token失败");
        }
    }

}
