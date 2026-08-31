package com.machine.client.iam.biam.identity;

import com.machine.client.iam.biam.identity.dto.input.*;
import com.machine.client.iam.biam.identity.dto.output.BIamOAuth2RegisteredClientDetailOutputDto;
import com.machine.client.iam.biam.identity.dto.output.BIamOAuth2RegisteredClientListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.dto.biam.identity.BIamOAuth2RegisteredClientDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/oauth2_registered_client",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamOauth2RegisteredClientClient {

    @PostMapping("create")
    String create(@RequestBody @Validated BIamOAuth2RegisteredClientCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated BIamOAuth2RegisteredClientUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated BIamOAuth2RegisteredClientUpdateStatusInputDto inputDto);

    @PostMapping("update_clientSecret")
    int updateClientSecret(@RequestBody @Validated BIamOAuth2RegisteredClientUpdateClientSecretInputDto inputDto);

    @GetMapping("all_enable_client_id")

    List<String> allEnableClientId();

    @GetMapping("get_by_clientId")
    BIamOAuth2RegisteredClientDto getByClientId(@RequestParam("clientId") String clientId);

    @PostMapping("detail")
    BIamOAuth2RegisteredClientDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("select_page")
    PageResponse<BIamOAuth2RegisteredClientListOutputDto> selectPage(@RequestBody BIamOAuth2RegisteredClientPageQueryInputDto inputDto);

}




