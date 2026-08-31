package com.machine.client.iam.biam.user;

import com.machine.client.iam.biam.user.dto.input.BIamThirdPartyUserBindInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamThirdPartyUserCreateInputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/third_party_user",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamThirdPartyUserClient {

    @PostMapping("create")
    String create(@RequestBody @Validated BIamThirdPartyUserCreateInputDto inputDto);

    @PostMapping("bind")
    void bind(@RequestBody @Validated BIamThirdPartyUserBindInputDto inputDto);

}



