package com.machine.client.iam.biam.log.dto.output;

import lombok.Data;

@Data
public class BIamUserLoginLogAvailableOutputDto {

    private String id;

    private String accessTokenId;

    private Long accessTokenExpire;

    private String refreshTokenId;

    private Long refreshTokenExpire;

}
