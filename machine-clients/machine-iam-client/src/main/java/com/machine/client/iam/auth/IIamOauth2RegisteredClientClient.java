package com.machine.client.iam.auth;

import com.machine.client.iam.auth.dto.AuthTokenAddDto;
import com.machine.client.iam.auth.dto.OAuth2RegisteredClientDto;
import com.machine.client.iam.auth.dto.output.OAuth2RegisteredClientDetailOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/oauth2_registered_client", configuration = OpenFeignDefaultConfig.class)
public interface IIamOauth2RegisteredClientClient {

    @PostMapping("add")
    int add(@RequestBody AuthTokenAddDto dto);

    @PostMapping("save")
    int save(@RequestBody OAuth2RegisteredClientDto dto);

    @PostMapping("update")
    int update(@RequestBody OAuth2RegisteredClientDto dto);

    @GetMapping("all_enable_client_id")
    List<String> allEnableClientId();

    @PostMapping("get_by_id")
    OAuth2RegisteredClientDetailOutputDto getById(@RequestParam("id")  String id);

    @GetMapping("get_by_clientId")
    OAuth2RegisteredClientDetailOutputDto getByClientId(@RequestParam("clientId")  String clientId);

}

