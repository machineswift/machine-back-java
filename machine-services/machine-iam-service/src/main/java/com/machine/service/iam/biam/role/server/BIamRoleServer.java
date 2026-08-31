package com.machine.service.iam.biam.role.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.role.IBIamRoleClient;
import com.machine.client.iam.biam.role.dto.input.*;
import com.machine.client.iam.biam.role.dto.output.BIamRoleDetailOutputDto;
import com.machine.client.iam.biam.role.dto.output.BIamRoleListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.iam.biam.role.service.IBIamRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/role")
public class BIamRoleServer implements IBIamRoleClient {

    @Autowired
    private IBIamRoleService roleService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated BIamRoleCreateInputDto inputDto) {
        log.info("创建角色， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return roleService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除角色， inputDto={}", JSONUtil.toJsonStr(request));
        return roleService.delete(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated BIamRoleUpdateInputDto inputDto) {
        log.info("修改角色， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return roleService.update(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated BIamRoleUpdateStatusInputDto inputDto) {
        log.info("修改角色状态， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return roleService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("update_permission")
    public void updatePermission(@RequestBody @Validated BIamRoleUpdatePermissionInputDto inputDto) {
        log.info("修改角色权限， inputDto={}", JSONUtil.toJsonStr(inputDto));
        roleService.updatePermission(inputDto);
    }

    @Override
    @PostMapping("detail")
    public BIamRoleDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return roleService.detail(request);
    }

    @Override
    @PostMapping("list_sub_id")
    public List<String> listSubId(@RequestBody @Validated BIamRoleListSubInputDto inputDto) {
        return roleService.listSubId(inputDto);
    }

    @Override
    @PostMapping("list_parent_by_target")
    public List<String> listParentByTarget(@RequestBody @Validated IdRequest request) {
        return roleService.listParentByTarget(request);
    }

    @Override
    @PostMapping("list_sub")
    public List<BIamRoleListOutputDto> listSub(@RequestBody @Validated BIamRoleListSubInputDto inputDto) {
        return roleService.listSub(inputDto);
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<BIamRoleListOutputDto> selectPage(@RequestBody @Validated BIamRoleQueryPageInputDto inputDto) {
        Page<BIamRoleListOutputDto> pageResult = roleService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, BIamRoleDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        return roleService.mapByIdSet(request);
    }

}
