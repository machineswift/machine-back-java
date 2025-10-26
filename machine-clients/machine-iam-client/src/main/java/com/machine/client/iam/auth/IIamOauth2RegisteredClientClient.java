package com.machine.client.iam.auth;

import com.machine.client.iam.auth.dto.IamAuthTokenAddDto;
import com.machine.client.iam.auth.dto.IamOAuth2RegisteredClientDto;
import com.machine.client.iam.auth.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "machine-iam-service", path = "machine-iam-service/server/oauth2_registered_client",
        configuration = OpenFeignMinTimeConfig.class)
public interface IIamOauth2RegisteredClientClient {

    @PostMapping("add")
    int add(@RequestBody IamAuthTokenAddDto dto);

    @PostMapping("save")
    int save(@RequestBody IamOAuth2RegisteredClientDto dto);

    @PostMapping("update")
    int update(@RequestBody IamOAuth2RegisteredClientDto dto);

    @GetMapping("all_enable_client_id")
    List<String> allEnableClientId();

    @PostMapping("get_by_id")
    IamOAuth2RegisteredClientDetailOutputDto getById(@RequestParam("id") String id);

    @GetMapping("get_by_clientId")
    IamOAuth2RegisteredClientDetailOutputDto getByClientId(@RequestParam("clientId") String clientId);

}




