package com.machine.starter.security.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.machine.starter.security.SecurityConstant.BEARER_TYPE;

@Data
@Schema
@NoArgsConstructor
public class IamAuthLoginResponse {

    public IamAuthLoginResponse(String accessToken,
                                Long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public IamAuthLoginResponse(String accessToken,
                                Long expiresIn,
                                String refreshToken) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    private String accessToken;

    private Long expiresIn;

    private String refreshToken;

    private String tokenType = BEARER_TYPE;
}
