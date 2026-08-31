package com.machine.app.iam.biam.permission.business;

import com.machine.app.iam.biam.permission.controller.vo.request.BIamPermissionCreateRequestVo;
import com.machine.app.iam.biam.permission.controller.vo.request.BIamPermissionUpdateParentRequestVo;
import com.machine.app.iam.biam.permission.controller.vo.request.BIamPermissionUpdateRequestVo;
import com.machine.app.iam.biam.permission.controller.vo.response.BIamPermissionDetailResponseVo;
import com.machine.app.iam.biam.permission.controller.vo.response.BIamPermissionTreeExpandResponseVo;
import com.machine.app.iam.biam.permission.controller.vo.response.BIamPermissionTreeSimpleResponseVo;
import com.machine.sdk.base.model.request.IdRequest;

public interface IBIamPermissionBusiness {

    String create(BIamPermissionCreateRequestVo request);

    void delete(IdRequest request);

    void update(BIamPermissionUpdateRequestVo request);

    void updateParent(BIamPermissionUpdateParentRequestVo request);

    BIamPermissionDetailResponseVo detail(IdRequest request);

    BIamPermissionTreeSimpleResponseVo treeSimple(IdRequest request);

    BIamPermissionTreeExpandResponseVo treeExpand(IdRequest request);

}
