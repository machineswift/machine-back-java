package com.machine.client.iam.user;

import com.machine.client.iam.user.dto.input.IamThirdPartyUserBindInputDto;
import com.machine.client.iam.user.dto.input.IamThirdPartyUserCreateInputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-iam-service", path = "machine-iam-service/server/iam/third_party_user",
        configuration = OpenFeignMinTimeConfig.class)
public interface IIamThirdPartyUserClient {

    @PostMapping("create")
    String create(@RequestBody @Validated IamThirdPartyUserCreateInputDto inputDto);

    @PostMapping("bind")
    void bind(@RequestBody @Validated IamThirdPartyUserBindInputDto inputDto);

}



