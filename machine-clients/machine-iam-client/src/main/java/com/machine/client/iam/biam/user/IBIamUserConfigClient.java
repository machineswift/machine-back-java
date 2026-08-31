package com.machine.client.iam.biam.user;

import com.machine.client.iam.biam.user.dto.input.BIamUserConfigGetInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserConfigSaveInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserConfigOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/user_config",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamUserConfigClient {

    @PostMapping("get_by_key")
    BIamUserConfigOutputDto getByKey(@RequestBody @Validated BIamUserConfigGetInputDto inputDto);

    @PostMapping("save")
    void save(@RequestBody @Validated BIamUserConfigSaveInputDto inputDto);

}
