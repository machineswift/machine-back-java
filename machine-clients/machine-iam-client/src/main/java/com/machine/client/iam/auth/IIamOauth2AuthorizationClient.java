package com.machine.client.iam.auth;

import com.machine.client.iam.auth.dto.input.IamOAuth2AuthorizationDto;
import com.machine.client.iam.auth.dto.output.IamOAuth2AuthorizationOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "machine-iam-service", path = "machine-iam-service/server/oauth2_authorization",
        configuration = OpenFeignMinTimeConfig.class)
public interface IIamOauth2AuthorizationClient {

    @PostMapping("save")
    int save(@RequestBody IamOAuth2AuthorizationDto dto);

    @PostMapping("update")
    int update(@RequestBody IamOAuth2AuthorizationDto dto);

    @PostMapping("findById")
    IamOAuth2AuthorizationOutputDto findById(@RequestParam("id") String id);

    @PostMapping("findByClientId")
    IamOAuth2AuthorizationOutputDto findByToken(IamOAuth2AuthorizationDto dto);

    @PostMapping("remove")
    void remove(@RequestParam("id") String id);
}



