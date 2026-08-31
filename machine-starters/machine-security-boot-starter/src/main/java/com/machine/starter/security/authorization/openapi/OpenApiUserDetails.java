package com.machine.starter.security.authorization.openapi;

import lombok.Data;

@Data
public class OpenApiUserDetails {
    private String clientId;
    private String clientSecret;
}
