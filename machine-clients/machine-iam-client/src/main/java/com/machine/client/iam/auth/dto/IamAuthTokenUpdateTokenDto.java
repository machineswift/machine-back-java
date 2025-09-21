package com.machine.client.iam.auth.dto;

import lombok.Data;

@Data
public class IamAuthTokenUpdateTokenDto {

    private String series;

    private String token;
}
