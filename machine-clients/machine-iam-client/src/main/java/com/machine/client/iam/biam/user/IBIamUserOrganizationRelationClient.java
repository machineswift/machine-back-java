package com.machine.client.iam.biam.user;

import com.machine.client.iam.biam.user.dto.output.BIamUserOrganizationRelationOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/user_organization_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamUserOrganizationRelationClient {

    @PostMapping("list_by_userId")
    List<BIamUserOrganizationRelationOutputDto> listByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_organizationIdSet")
    List<BIamUserOrganizationRelationOutputDto> listByOrganizationIdSet(@RequestBody @Validated IdSetRequest request);
}



