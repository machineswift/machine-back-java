package com.machine.app.iam.biam.role.business;

import com.machine.app.iam.biam.role.controller.vo.request.*;
import com.machine.app.iam.biam.role.controller.vo.response.BIamRoleDetailResponseVo;
import com.machine.app.iam.biam.role.controller.vo.response.BIamRoleExpandListResponseVo;
import com.machine.app.iam.biam.role.controller.vo.response.BIamRoleSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IBIamRoleBusiness {

    String create(BIamRoleCreateRequestVo request);

    void delete(IdRequest request);

    void update(BIamRoleUpdateRequestVo request);

    void updateStatus(BIamRoleUpdateStatusRequestVo request);

    void updatePermission(BIamRoleUpdatePermissionRequestVo request);

    BIamRoleDetailResponseVo detail(IdRequest request);

    PageResponse<BIamRoleSimpleListResponseVo> pageSimple(BIamRoleQueryPageRequestVo request);

    PageResponse<BIamRoleExpandListResponseVo> pageExpand(BIamRoleQueryPageRequestVo request);

}
