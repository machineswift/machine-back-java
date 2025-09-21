package com.machine.sdk.feishu.client.dto.input;

/**
 * @description:
 * @author：Geek Wang
 * @date: 2023/10/9
 */
public class ClientRequest {

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用秘钥
     */
    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
