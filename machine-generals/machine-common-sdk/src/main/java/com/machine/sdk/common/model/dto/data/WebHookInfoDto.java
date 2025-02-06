package com.machine.sdk.common.model.dto.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebHookInfoDto {

    /**
     * 密钥
     */
    private String secret;

    /**
     * 回调地址
     */
    private String callBackUrl;

}
