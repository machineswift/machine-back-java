package com.machine.service.iam.user.server;

import com.machine.client.iam.user.IIamUserRoleRelationClient;
import com.machine.client.iam.user.dto.output.IamUserRoleRelationListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.service.IIamUserRoleRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/iam/user_role_relation")
public class IamUserRoleRelationServer implements IIamUserRoleRelationClient {

    @Autowired
    private IIamUserRoleRelationService userRoleRelationService;

    @Override
    @PostMapping("list_by_userId")
    public List<IamUserRoleRelationListOutputDto> listByUserId(@RequestBody @Validated IdRequest request) {
        return userRoleRelationService.listByUserId(request);
    }

    @Override
    @PostMapping("list_by_idSet")
    public List<IamUserRoleRelationListOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleRelationService.listByIdSet(request);
    }

    @Override
    @PostMapping("list_by_roleIdSet")
    public List<IamUserRoleRelationListOutputDto> listByRoleIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleRelationService.listByRoleIdSet(request);
    }

    @Override
    @PostMapping("list_by_userIdSet")
    public List<IamUserRoleRelationListOutputDto> listByUserIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleRelationService.listByUserIdSet(request);
    }

    @Override
    @PostMapping("countUser_by_roleIdSet")
    public Map<String, Integer> countUserByRoleIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleRelationService.countUserByRoleIdSet(request);
    }
}
