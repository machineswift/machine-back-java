package com.machine.client.iam.user;

import com.machine.client.iam.user.dto.output.IamUserOrganizationRelationOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-iam-service", path = "machine-iam-service/server/iam/user_organization_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IIamUserOrganizationRelationClient {

    @PostMapping("list_by_userId")
    List<IamUserOrganizationRelationOutputDto> listByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_organizationIdSet")
    List<IamUserOrganizationRelationOutputDto> listByOrganizationIdSet(@RequestBody @Validated IdSetRequest request);
}



