package com.machine.client.iam.auth;

import com.machine.client.iam.auth.dto.input.IamOauth2AuthorizationConsentInputDto;
import com.machine.client.iam.auth.dto.output.IamOauth2AuthorizationConsentOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/oauth2_authorization_consent", configuration = OpenFeignDefaultConfig.class)
public interface IIamOauth2AuthorizationConsentClient {


    @PostMapping("update")
    void update(@RequestBody IamOauth2AuthorizationConsentInputDto dto);

    @PostMapping("save")
    void save(@RequestBody IamOauth2AuthorizationConsentInputDto dto);

    @PostMapping("remove")
    void remove(@RequestBody IamOauth2AuthorizationConsentInputDto dto);

    @PostMapping("findById")
    IamOauth2AuthorizationConsentOutputDto findById(@RequestBody IamOauth2AuthorizationConsentInputDto dto);
}
