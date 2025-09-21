package com.machine.service.iam.role.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.role.dto.input.*;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.role.dto.output.IamRoleListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IIamRoleService {

    String create(IamRoleCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(IamRoleUpdateInputDto inputDto);

    int updateStatus(IamRoleUpdateStatusInputDto inputDto);

    void updatePermission(IamRoleUpdatePermissionInputDto inputDto);

    IamRoleDetailOutputDto detail(IdRequest request);

    List<String> listSubId(IamRoleListSubInputDto inputDto);

    List<String> listParentByTarget(IdRequest request);

    List<IamRoleListOutputDto> listSub(IamRoleListSubInputDto inputDto);

    Page<IamRoleListOutputDto> selectPage(IamRoleQueryPageInputDto inputDto);

    Map<String, IamRoleDetailOutputDto> mapByIdSet(IdSetRequest request);

}
