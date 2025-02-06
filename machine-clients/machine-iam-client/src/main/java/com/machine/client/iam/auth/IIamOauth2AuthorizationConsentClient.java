package com.machine.client.iam.auth;

import com.machine.client.iam.auth.dto.input.Oauth2AuthorizationConsentInputDto;
import com.machine.client.iam.auth.dto.output.Oauth2AuthorizationConsentOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/oauth2_authorization_consent", configuration = OpenFeignDefaultConfig.class)
public interface IIamOauth2AuthorizationConsentClient {


    @PostMapping("update")
    void update(@RequestBody Oauth2AuthorizationConsentInputDto dto);

    @PostMapping("save")
    void save(@RequestBody Oauth2AuthorizationConsentInputDto dto);

    @PostMapping("remove")
    void remove(@RequestBody Oauth2AuthorizationConsentInputDto dto);

    @PostMapping("findById")
    Oauth2AuthorizationConsentOutputDto findById(@RequestBody Oauth2AuthorizationConsentInputDto dto);
}
