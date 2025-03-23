package com.machine.service.iam.user.server;

import com.machine.client.iam.user.IIamUserOrganizationRelationClient;
import com.machine.client.iam.user.dto.output.IamUserOrganizationRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.service.IUserOrganizationRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/iam/user_organization_relation")
public class IamUserOrganizationRelationServer implements IIamUserOrganizationRelationClient {

    @Autowired
    private IUserOrganizationRelationService userOrganizationRelationService;

    @Override
    @PostMapping("map_leader_by_organizationIdSet")
    public Map<String, String> mapLeaderByOrganizationIdSet(@RequestBody @Validated IdSetRequest request) {
        return userOrganizationRelationService.mapLeaderByOrganizationIdSet(request);
    }

    @Override
    @PostMapping("select_leader_by_organizationId")
    public IamUserOrganizationRelationOutputDto selectLeaderByOrganizationId(@RequestBody @Validated IdRequest request) {
        return userOrganizationRelationService.selectLeaderByOrganizationId(request);
    }
}
