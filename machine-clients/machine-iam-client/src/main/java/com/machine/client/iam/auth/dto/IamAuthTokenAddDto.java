package com.machine.client.iam.auth.dto;

import lombok.Data;

@Data
public class IamAuthTokenAddDto {

    private String username;

    private String series;

    private String token;
}
