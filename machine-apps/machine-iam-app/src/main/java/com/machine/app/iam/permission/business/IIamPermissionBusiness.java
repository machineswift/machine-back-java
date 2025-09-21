package com.machine.app.iam.permission.business;

import com.machine.app.iam.permission.controller.vo.request.*;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionDetailResponseVo;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionTreeExpandResponseVo;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionTreeSimpleResponseVo;
import com.machine.client.iam.permission.dto.output.IamPermissionTreeOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

public interface IIamPermissionBusiness {

    String create(IamPermissionCreateRequestVo request);

    void delete(IdRequest request);

    void update(IamPermissionUpdateRequestVo request);

    void updateParent(IamPermissionUpdateParentRequestVo request);

    IamPermissionDetailResponseVo detail(IdRequest request);

    IamPermissionTreeSimpleResponseVo treeSimple(IdRequest request);

    IamPermissionTreeExpandResponseVo treeExpand(IdRequest request);

}
