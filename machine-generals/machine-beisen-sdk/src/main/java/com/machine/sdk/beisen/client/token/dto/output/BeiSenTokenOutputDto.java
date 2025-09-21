package com.machine.sdk.beisen.client.token.dto.output;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

@Data
public class BeiSenTokenOutputDto {

    /**
     * 租户授权的唯一票据
     */
    @Alias("access_token")
    private String accessToken;

    /**
     * 授权类型
     */
    @Alias("token_type")
    private String tokenType;

    /**
     * 过期时间
     */
    @Alias("expires_in")
    private Integer expiresIn;

    /**
     * 刷新Token
     */
    @Alias("refresh_token")
    private String refreshToken;
}
