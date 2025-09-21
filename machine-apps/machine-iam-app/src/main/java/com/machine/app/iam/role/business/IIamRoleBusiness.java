package com.machine.app.iam.role.business;

import com.machine.app.iam.role.controller.vo.request.*;
import com.machine.app.iam.role.controller.vo.response.IamRoleDetailResponseVo;
import com.machine.app.iam.role.controller.vo.response.IamRoleExpandListResponseVo;
import com.machine.app.iam.role.controller.vo.response.IamRoleSimpleListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IIamRoleBusiness {

    String create(IamRoleCreateRequestVo request);

    void delete(IdRequest request);

    void update(IamRoleUpdateRequestVo request);

    void updateStatus(IamRoleUpdateStatusRequestVo request);

    void updatePermission(IamRoleUpdatePermissionRequestVo request);

    IamRoleDetailResponseVo detail(IdRequest request);

    PageResponse<IamRoleSimpleListResponseVo> pageSimple(IamRoleQueryPageRequestVo request);

    PageResponse<IamRoleExpandListResponseVo> pageExpand(IamRoleQueryPageRequestVo request);

}
