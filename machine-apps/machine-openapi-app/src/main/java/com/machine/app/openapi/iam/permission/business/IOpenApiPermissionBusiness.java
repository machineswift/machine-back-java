package com.machine.app.openapi.iam.permission.business;

import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionIdRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionListSubRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionQueryAppListRequestVo;
import com.machine.client.iam.permission.dto.output.IamPermissionTreeOutputDto;

import java.util.List;

public interface IOpenApiPermissionBusiness {

    List<IamPermissionTreeOutputDto> listApp(OpenApiPermissionQueryAppListRequestVo request);

    IamPermissionTreeOutputDto detail(OpenApiPermissionIdRequestVo request);

    List<String> listParentByTarget(OpenApiPermissionIdRequestVo request);

    List<String> listSubId(OpenApiPermissionListSubRequestVo request);

    List<IamPermissionTreeOutputDto> listSub(OpenApiPermissionListSubRequestVo request);

}
