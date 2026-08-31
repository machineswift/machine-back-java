package com.machine.client.iam.biam.identity;

import com.machine.client.iam.biam.identity.dto.BIamOAuth2AuthorizationDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/oauth2_authorization",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamOauth2AuthorizationClient {

    @PostMapping("save")
    int save(@RequestBody BIamOAuth2AuthorizationDto dto);

    @GetMapping("remove")
    void remove(@RequestParam("id") String id);

    @PostMapping("update")
    int update(@RequestBody BIamOAuth2AuthorizationDto dto);

    @GetMapping("existsById")
    boolean existsById(@RequestParam("id") String id);

    @GetMapping("findById")
    BIamOAuth2AuthorizationDto findById(@RequestParam("id") String id);

    @PostMapping("findByToken")
    BIamOAuth2AuthorizationDto findByToken(@RequestBody BIamOAuth2AuthorizationDto dto);

}



