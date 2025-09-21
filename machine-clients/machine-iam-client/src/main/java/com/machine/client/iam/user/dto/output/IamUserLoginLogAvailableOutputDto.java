package com.machine.client.iam.user.dto.output;

import lombok.Data;

@Data
public class IamUserLoginLogAvailableOutputDto {

    private String id;

    private String accessTokenId;

    private String accessToken;

    private Long accessTokenExpire;

    private String refreshTokenId;

    private String refreshToken;

    private Long refreshTokenExpire;

}
