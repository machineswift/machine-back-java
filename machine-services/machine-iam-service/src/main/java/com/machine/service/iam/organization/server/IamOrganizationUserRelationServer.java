package com.machine.service.iam.organization.server;

import com.machine.client.iam.organization.IIamOrganizationUserRelationClient;
import com.machine.client.iam.organization.dto.output.IamOrganizationUserRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.organization.service.IOrganizationUserRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/iam/organization_user_relation")
public class IamOrganizationUserRelationServer implements IIamOrganizationUserRelationClient {

    @Autowired
    private IOrganizationUserRelationService organizationUserRelationService;

    @Override
    @PostMapping("map_by_organizationIdSet")
    public Map<String, String> mapByOrganizationIdSet(@RequestBody @Validated IdSetRequest request) {
        return organizationUserRelationService.mapByOrganizationIdSet(request);
    }

    @Override
    @PostMapping("select_by_OrganizationId")
    public IamOrganizationUserRelationOutputDto selectByOrganizationId(@RequestBody @Validated IdRequest request) {
        return organizationUserRelationService.selectByOrganizationId(request);
    }
}
