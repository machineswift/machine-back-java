package com.machine.service.iam.biam.role.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.role.dto.input.*;
import com.machine.client.iam.biam.role.dto.output.BIamRoleDetailOutputDto;
import com.machine.client.iam.biam.role.dto.output.BIamRoleListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IBIamRoleService {

    String create(BIamRoleCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(BIamRoleUpdateInputDto inputDto);

    int updateStatus(BIamRoleUpdateStatusInputDto inputDto);

    void updatePermission(BIamRoleUpdatePermissionInputDto inputDto);

    BIamRoleDetailOutputDto detail(IdRequest request);

    List<String> listSubId(BIamRoleListSubInputDto inputDto);

    List<String> listParentByTarget(IdRequest request);

    List<BIamRoleListOutputDto> listSub(BIamRoleListSubInputDto inputDto);

    Page<BIamRoleListOutputDto> selectPage(BIamRoleQueryPageInputDto inputDto);

    Map<String, BIamRoleDetailOutputDto> mapByIdSet(IdSetRequest request);

}
