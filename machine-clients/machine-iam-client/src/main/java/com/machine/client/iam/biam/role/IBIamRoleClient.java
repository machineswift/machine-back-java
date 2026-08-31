package com.machine.client.iam.biam.role;

import com.machine.client.iam.biam.role.dto.input.*;
import com.machine.client.iam.biam.role.dto.output.BIamRoleDetailOutputDto;
import com.machine.client.iam.biam.role.dto.output.BIamRoleListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/role",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamRoleClient {

    @PostMapping("create")
    String create(@RequestBody @Validated BIamRoleCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated BIamRoleUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated BIamRoleUpdateStatusInputDto inputDto);

    @PostMapping("update_permission")
    void updatePermission(@RequestBody @Validated BIamRoleUpdatePermissionInputDto inputDto);

    @PostMapping("detail")
    BIamRoleDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("list_sub_id")
    List<String> listSubId(@RequestBody @Validated BIamRoleListSubInputDto inputDto);

    @PostMapping("list_parent_by_target")
    List<String> listParentByTarget(@RequestBody @Validated IdRequest request);

    @PostMapping("list_sub")
    List<BIamRoleListOutputDto> listSub(@RequestBody @Validated BIamRoleListSubInputDto inputDto);

    @PostMapping("select_page")
    PageResponse<BIamRoleListOutputDto> selectPage(@RequestBody @Validated BIamRoleQueryPageInputDto inputDto);

    @PostMapping("map_by_idSet")
    Map<String, BIamRoleDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

}



