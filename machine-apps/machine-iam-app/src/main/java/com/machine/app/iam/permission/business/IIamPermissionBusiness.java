package com.machine.app.iam.permission.business;

import com.machine.app.iam.permission.controller.vo.request.*;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionDetailResponseVo;
import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IIamPermissionBusiness {

    String create(IamPermissionCreateRequestVo requestVo);

    void delete(IdRequest request);

    void update(IamPermissionUpdateRequestVo request);

    void updateStatus(IamPermissionUpdateStatusRequestVo request);

    void updateParent(IamPermissionUpdateParentRequestVo request);

    IamPermissionDetailResponseVo detail(IdRequest request);

    List<PermissionTreeOutputDto> listApp(IamPermissionQueryAppListRequestVo request);

    List<PermissionTreeOutputDto> listSub(IamPermissionQueryListSubRequestVo request);

    PermissionTreeOutputDto tree(IamPermissionQueryTreeRequestVo request);

}
