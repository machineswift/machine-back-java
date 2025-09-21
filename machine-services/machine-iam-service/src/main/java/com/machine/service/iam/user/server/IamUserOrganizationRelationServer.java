package com.machine.service.iam.user.server;

import com.machine.client.iam.user.IIamUserOrganizationRelationClient;
import com.machine.client.iam.user.dto.output.IamUserOrganizationRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.service.IIamUserOrganizationRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/user_organization_relation")
public class IamUserOrganizationRelationServer implements IIamUserOrganizationRelationClient {

    @Autowired
    private IIamUserOrganizationRelationService userOrganizationRelationService;

    @Override
    @PostMapping("list_by_userId")
    public List<IamUserOrganizationRelationOutputDto> listByUserId(@RequestBody @Validated IdRequest request) {
        return userOrganizationRelationService.listByUserId(request);
    }

    @Override
    @PostMapping("list_by_organizationIdSet")
    public List<IamUserOrganizationRelationOutputDto> listByOrganizationIdSet(@RequestBody @Validated IdSetRequest request) {
        return userOrganizationRelationService.listByOrganizationIdSet(request);
    }
}
