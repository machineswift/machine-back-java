package com.machine.client.iam.role;

import com.machine.client.iam.role.dto.input.*;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.role.dto.output.IamRoleListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/role", configuration = OpenFeignDefaultConfig.class)
public interface IIamRoleClient {

    @PostMapping("create")
    String create(@RequestBody @Validated IamRoleCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated IamRoleUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated IamRoleUpdateStatusInputDto inputDto);

    @PostMapping("update_permission")
    void updatePermission(@RequestBody @Validated IamRoleUpdatePermissionInputDto inputDto);

    @PostMapping("detail")
    IamRoleDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("list_sub_id")
    List<String> listSubId(@RequestBody @Validated IamRoleListSubInputDto inputDto);

    @PostMapping("list_parent_by_target")
    List<String> listParentByTarget(@RequestBody @Validated IdRequest request);

    @PostMapping("list_sub")
    List<IamRoleListOutputDto> listSub(@RequestBody @Validated IamRoleListSubInputDto inputDto);

    @PostMapping("select_page")
    PageResponse<IamRoleListOutputDto> selectPage(@RequestBody @Validated IamRoleQueryPageInputDto inputDto);

    @PostMapping("map_by_idSet")
    Map<String, IamRoleDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

}
