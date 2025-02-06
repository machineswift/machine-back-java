package com.machine.sdk.beisen.client.token.dto.input;

import cn.hutool.core.annotation.Alias;

import lombok.Data;

@Data
public class BeiSenTokenInputDto {

    /**
     * 应用KEY
     */
    @Alias("app_key")
    private String appKey;

    /**
     * 应用秘钥
     */
    @Alias("app_secret")
    private String appSecret;

    /**
     * 授权类型
     */
    @Alias("grant_type")
    private String grantType = "client_credentials";
}
