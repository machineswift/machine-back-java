package com.machine.service.iam.biam.user.server;

import com.machine.client.iam.biam.user.IBIamUserOrganizationRelationClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserOrganizationRelationOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.iam.biam.user.service.IBIamUserOrganizationRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/user_organization_relation")
public class BIamUserOrganizationRelationServer implements IBIamUserOrganizationRelationClient {

    @Autowired
    private IBIamUserOrganizationRelationService userOrganizationRelationService;

    @Override
    @PostMapping("list_by_userId")
    public List<BIamUserOrganizationRelationOutputDto> listByUserId(@RequestBody @Validated IdRequest request) {
        return userOrganizationRelationService.listByUserId(request);
    }

    @Override
    @PostMapping("list_by_organizationIdSet")
    public List<BIamUserOrganizationRelationOutputDto> listByOrganizationIdSet(@RequestBody @Validated IdSetRequest request) {
        return userOrganizationRelationService.listByOrganizationIdSet(request);
    }
}
