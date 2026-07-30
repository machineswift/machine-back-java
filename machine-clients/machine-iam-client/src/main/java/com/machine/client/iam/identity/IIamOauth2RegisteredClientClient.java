package com.machine.client.iam.identity;

import com.machine.client.iam.identity.dto.input.*;
import com.machine.client.iam.identity.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import com.machine.client.iam.identity.dto.output.IamOAuth2RegisteredClientListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.dto.iam.identity.IamOAuth2RegisteredClientDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "machine-iam-service", path = "machine-iam-service/server/oauth2_registered_client",
        configuration = OpenFeignMinTimeConfig.class)
public interface IIamOauth2RegisteredClientClient {

    @PostMapping("create")
    String create(@RequestBody @Validated IamOAuth2RegisteredClientCreateInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated IamOAuth2RegisteredClientUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated IamOAuth2RegisteredClientUpdateStatusInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @GetMapping("all_enable_client_id")
    List<String> allEnableClientId();

    @PostMapping("get_by_id")
    IamOAuth2RegisteredClientDto getById(@RequestParam("id") String id);

    @GetMapping("get_by_clientId")
    IamOAuth2RegisteredClientDto getByClientId(@RequestParam("clientId") String clientId);

    @PostMapping("detail")
    IamOAuth2RegisteredClientDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("select_page")
    PageResponse<IamOAuth2RegisteredClientListOutputDto> selectPage(@RequestBody IamOAuth2RegisteredClientPageQueryInputDto inputDto);

}




