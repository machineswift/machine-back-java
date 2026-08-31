package com.machine.client.iam.biam.permission;

import com.machine.client.iam.biam.permission.dto.input.BIamPermissionCreateInputDto;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionUpdateInputDto;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionUpdateParentInputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionDetailOutputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionListOutputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.Tuples;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/permission", configuration = OpenFeignMinTimeConfig.class)
public interface IBIamPermissionClient {

    @PostMapping("create")
    String create(@RequestBody @Validated BIamPermissionCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated BIamPermissionUpdateInputDto inputDto);

    @PostMapping("update_parent")
    int updateParent(@RequestBody @Validated BIamPermissionUpdateParentInputDto inputDto);

    @PostMapping("detail")
    BIamPermissionDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("detail_by_code")
    BIamPermissionDetailOutputDto detailByCode(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_roleId")
    List<BIamPermissionListOutputDto> listByRoleId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_roleIdSet")
    List<BIamPermissionListOutputDto> listByRoleIdSet(@RequestBody @Validated IdSetRequest request);

    @GetMapping("tree_all")
    Tuples.Tuple2<String, BIamPermissionTreeOutputDto> treeAll();
}
