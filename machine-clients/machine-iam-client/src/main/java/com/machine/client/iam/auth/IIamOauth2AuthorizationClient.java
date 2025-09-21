package com.machine.client.iam.auth;

import com.machine.client.iam.auth.dto.input.IamOAuth2AuthorizationDto;
import com.machine.client.iam.auth.dto.output.IamOAuth2AuthorizationOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * packageName com.machine.client.iam.auth
 *
 * @author chenjie
 * @version JDK 8
 * @className IIamOauth2AuthorizationClient (此处以class为例)
 * @date 2024/12/3
 * @description TODO
 */
@FeignClient(name = "machine-iam-service/machine-iam-service/server/oauth2_authorization", configuration = OpenFeignDefaultConfig.class)
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
