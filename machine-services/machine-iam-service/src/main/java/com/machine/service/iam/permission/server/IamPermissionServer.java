package com.machine.service.iam.permission.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.permission.IIamPermissionClient;
import com.machine.client.iam.permission.dto.input.*;
import com.machine.client.iam.permission.dto.output.PermissionDetailOutputDto;
import com.machine.client.iam.permission.dto.output.PermissionListOutputDto;
import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.iam.permission.service.IPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/permission")
public class IamPermissionServer implements IIamPermissionClient {

    @Autowired
    private IPermissionService permissionService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated IamPermissionCreateInputDto inputDto) {
        log.info("新增权限，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return permissionService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除权限，inputDto={}", JSONUtil.toJsonStr(request));
        return permissionService.delete(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated IamPermissionUpdateInputDto inputDto) {
        log.info("修改权限，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return permissionService.update(inputDto);
    }

    @Override
    @PostMapping("updateStatus")
    public int updateStatus(@RequestBody @Validated IamPermissionUpdateStatusInputDto inputDto) {
        log.info("修改权限状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return permissionService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("updateParent")
    public int updateParent(@RequestBody @Validated IamPermissionUpdateParentInputDto inputDto) {
        log.info("修改权限父节点，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return permissionService.updateParent(inputDto);
    }

    @Override
    @PostMapping("detail")
    public PermissionDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return permissionService.detail(request);
    }

    @Override
    @PostMapping("detail_by_code")
    public PermissionDetailOutputDto detailByCode(@RequestBody @Validated IdRequest request) {
        return permissionService.detailByCode(request);
    }

    @Override
    @PostMapping("list_by_roleId")
    public List<PermissionListOutputDto> listByRoleId(@RequestBody @Validated IdRequest request) {
        return permissionService.listByRoleId(request);
    }

    @Override
    @GetMapping("tree_all")
    public PermissionTreeOutputDto treeAll() {
        return permissionService.treeAll();
    }
}
