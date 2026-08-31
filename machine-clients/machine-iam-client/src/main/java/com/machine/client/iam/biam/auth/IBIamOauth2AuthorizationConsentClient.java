package com.machine.client.iam.biam.auth;

import com.machine.client.iam.biam.auth.dto.input.BIamOauth2AuthorizationConsentInputDto;
import com.machine.client.iam.biam.auth.dto.output.BIamOauth2AuthorizationConsentOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/oauth2_authorization_consent",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamOauth2AuthorizationConsentClient {


    @PostMapping("update")
    void update(@RequestBody BIamOauth2AuthorizationConsentInputDto dto);

    @PostMapping("save")
    void save(@RequestBody BIamOauth2AuthorizationConsentInputDto dto);

    @PostMapping("remove")
    void remove(@RequestBody BIamOauth2AuthorizationConsentInputDto dto);

    @PostMapping("findById")
    BIamOauth2AuthorizationConsentOutputDto findById(@RequestBody BIamOauth2AuthorizationConsentInputDto dto);
}



