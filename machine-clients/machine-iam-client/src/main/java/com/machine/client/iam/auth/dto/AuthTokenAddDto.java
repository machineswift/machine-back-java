package com.machine.client.iam.auth.dto;

import lombok.Data;

@Data
public class AuthTokenAddDto {

    private String username;

    private String series;

    private String token;
}
