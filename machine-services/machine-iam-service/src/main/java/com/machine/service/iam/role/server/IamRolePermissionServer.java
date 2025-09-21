package com.machine.service.iam.role.server;

import com.machine.client.iam.role.IIamRolePermissionClient;
import com.machine.client.iam.role.dto.output.IamRolePermissionListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.iam.role.service.IIamRolePermissionService;
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
@RequestMapping("server/iam/role_permission")
public class IamRolePermissionServer implements IIamRolePermissionClient {


    @Autowired
    private IIamRolePermissionService rolePermissionService;

    @Override
    @PostMapping("listByRoleId")
    public List<IamRolePermissionListOutputDto> listByRoleId(@RequestBody @Validated IdRequest request) {
        return rolePermissionService.listByRoleId(request);
    }

}
