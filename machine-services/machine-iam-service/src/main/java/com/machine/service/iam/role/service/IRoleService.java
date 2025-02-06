package com.machine.service.iam.role.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.role.dto.input.*;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.role.dto.output.IamRoleListOutputDto;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IRoleService {

    String create(IamRoleCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(IamRoleUpdateInputDto inputDto);

    int updateStatus(IamRoleUpdateStatusInputDto inputDto);

    IamRoleDetailOutputDto detail(IdRequest request);

    IamRoleDetailOutputDto detailByCode(IamRoleCodeInputDto request);

    IamRoleDetailOutputDto detailByName(IamRoleCodeInputDto request);

    List<String> listSubId(IamRoleListSubInputDto inputDto);

    List<String> listIdByType(RoleTypeEnum type);

    List<String> listParentByTarget(IdRequest request);

    List<IamRoleListOutputDto> listSub(IamRoleListSubInputDto inputDto);

    Page<IamRoleListOutputDto> page(IamRoleQueryPageInputDto inputDto);

    Map<String, IamRoleDetailOutputDto> mapByIdSet(IdSetRequest request);

}
