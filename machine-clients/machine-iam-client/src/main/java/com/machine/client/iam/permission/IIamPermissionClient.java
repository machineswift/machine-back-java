package com.machine.client.iam.permission;

import com.machine.client.iam.permission.dto.input.*;
import com.machine.client.iam.permission.dto.output.IamPermissionDetailOutputDto;
import com.machine.client.iam.permission.dto.output.IamPermissionListOutputDto;
import com.machine.client.iam.permission.dto.output.IamPermissionTreeOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/permission",
        configuration = OpenFeignDefaultConfig.class)
public interface IIamPermissionClient {

    @PostMapping("create")
    String create(@RequestBody @Validated IamPermissionCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated IamPermissionUpdateInputDto inputDto);

    @PostMapping("update_parent")
    int updateParent(@RequestBody @Validated IamPermissionUpdateParentInputDto inputDto);

    @PostMapping("detail")
    IamPermissionDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("detail_by_code")
    IamPermissionDetailOutputDto detailByCode(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_roleId")
    List<IamPermissionListOutputDto> listByRoleId(@RequestBody @Validated IdRequest request);

    @GetMapping("tree_all")
    IamPermissionTreeOutputDto treeAll();
}
