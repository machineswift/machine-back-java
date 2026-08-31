package com.machine.service.iam.biam.role.server;

import com.machine.client.iam.biam.role.IBIamRolePermissionClient;
import com.machine.client.iam.biam.role.dto.output.BIamRolePermissionListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.iam.biam.role.service.IBIamRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/role_permission")
public class BIamRolePermissionServer implements IBIamRolePermissionClient {


    @Autowired
    private IBIamRolePermissionService rolePermissionService;

    @Override
    @PostMapping("listByRoleId")
    public List<BIamRolePermissionListOutputDto> listByRoleId(@RequestBody @Validated IdRequest request) {
        return rolePermissionService.listByRoleId(request);
    }

}
