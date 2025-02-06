package com.machine.client.iam.auth.dto;

import lombok.Data;

@Data
public class AuthTokenUpdateTokenDto {

    private String series;

    private String token;
}
