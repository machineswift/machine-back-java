package com.machine.service.iam.biam.permission.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.permission.IBIamPermissionClient;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionCreateInputDto;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionUpdateInputDto;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionUpdateParentInputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionDetailOutputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionListOutputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.Tuples;
import com.machine.service.iam.biam.permission.service.IBIamPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/permission")
public class BIamPermissionServer implements IBIamPermissionClient {

    @Autowired
    private IBIamPermissionService permissionService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated BIamPermissionCreateInputDto inputDto) {
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
    public int update(@RequestBody @Validated BIamPermissionUpdateInputDto inputDto) {
        log.info("修改权限，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return permissionService.update(inputDto);
    }

    @Override
    @PostMapping("update_parent")
    public int updateParent(@RequestBody @Validated BIamPermissionUpdateParentInputDto inputDto) {
        log.info("修改权限父节点，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return permissionService.updateParent(inputDto);
    }

    @Override
    @PostMapping("detail")
    public BIamPermissionDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return permissionService.detail(request);
    }

    @Override
    @PostMapping("detail_by_code")
    public BIamPermissionDetailOutputDto detailByCode(@RequestBody @Validated IdRequest request) {
        return permissionService.detailByCode(request);
    }

    @Override
    @PostMapping("list_by_roleId")
    public List<BIamPermissionListOutputDto> listByRoleId(@RequestBody @Validated IdRequest request) {
        return permissionService.listByRoleId(request);
    }

    @Override
    @PostMapping("list_by_roleIdSet")
    public List<BIamPermissionListOutputDto> listByRoleIdSet(@RequestBody @Validated IdSetRequest request) {
        return permissionService.listByRoleIdSet(request);
    }

    @Override
    @GetMapping("tree_all")
    public Tuples.Tuple2<String, BIamPermissionTreeOutputDto> treeAll() {
        return permissionService.treeAll();
    }
}
