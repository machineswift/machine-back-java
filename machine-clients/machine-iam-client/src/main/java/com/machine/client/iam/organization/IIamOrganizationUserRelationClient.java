package com.machine.client.iam.organization;

import com.machine.client.iam.organization.dto.output.IamOrganizationUserRelationOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/organization_user_relation",
        configuration = OpenFeignDefaultConfig.class)
public interface IIamOrganizationUserRelationClient {

    /**
     * <组织Id,负责人Id>
     */
    @PostMapping("map_by_organizationIdSet")
    Map<String, String> mapByOrganizationIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("select_by_OrganizationId")
    IamOrganizationUserRelationOutputDto selectByOrganizationId(@RequestBody @Validated IdRequest request);

}
