package com.machine.client.iam.user;

import com.machine.client.iam.user.dto.output.IamUserOrganizationRelationOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/user_organization_relation",
        configuration = OpenFeignDefaultConfig.class)
public interface IIamUserOrganizationRelationClient {

    /**
     * <组织Id,负责人Id>
     */
    @PostMapping("map_leader_by_organizationIdSet")
    Map<String, String> mapLeaderByOrganizationIdSet(@RequestBody @Validated IdSetRequest request);

    /**
     * <组织负责人关系>
     */
    @PostMapping("select_leader_by_organizationId")
    IamUserOrganizationRelationOutputDto selectLeaderByOrganizationId(@RequestBody @Validated IdRequest request);

}
