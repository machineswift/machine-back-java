package com.machine.starter.security.resource.open;

import lombok.Data;

@Data
public class OpenApiUserDetails {
    private String clientId;
    private String clientSecret;
}
