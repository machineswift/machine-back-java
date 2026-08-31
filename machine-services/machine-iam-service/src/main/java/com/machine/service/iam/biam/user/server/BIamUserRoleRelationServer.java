package com.machine.service.iam.biam.user.server;

import com.machine.client.iam.biam.user.IBIamUserRoleRelationClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserRoleRelationListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.iam.biam.user.service.IBIamUserRoleRelationService;
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
@RequestMapping("server/iam/biam/user_role_relation")
public class BIamUserRoleRelationServer implements IBIamUserRoleRelationClient {

    @Autowired
    private IBIamUserRoleRelationService userRoleRelationService;

    @Override
    @PostMapping("list_by_userId")
    public List<BIamUserRoleRelationListOutputDto> listByUserId(@RequestBody @Validated IdRequest request) {
        return userRoleRelationService.listByUserId(request);
    }

    @Override
    @PostMapping("list_by_idSet")
    public List<BIamUserRoleRelationListOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleRelationService.listByIdSet(request);
    }

    @Override
    @PostMapping("list_by_roleIdSet")
    public List<BIamUserRoleRelationListOutputDto> listByRoleIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleRelationService.listByRoleIdSet(request);
    }

    @Override
    @PostMapping("list_by_userIdSet")
    public List<BIamUserRoleRelationListOutputDto> listByUserIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleRelationService.listByUserIdSet(request);
    }

    @Override
    @PostMapping("countUser_by_roleIdSet")
    public Map<String, Integer> countUserByRoleIdSet(@RequestBody @Validated IdSetRequest request) {
        return userRoleRelationService.countUserByRoleIdSet(request);
    }
}
