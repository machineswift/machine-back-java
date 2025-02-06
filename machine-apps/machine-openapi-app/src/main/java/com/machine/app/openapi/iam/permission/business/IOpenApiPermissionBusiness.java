package com.machine.app.openapi.iam.permission.business;

import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionIdRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionListSubRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionQueryAppListRequestVo;
import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;

import java.util.List;

public interface IOpenApiPermissionBusiness {

    List<PermissionTreeOutputDto> listApp(OpenApiPermissionQueryAppListRequestVo request);

    PermissionTreeOutputDto detail(OpenApiPermissionIdRequestVo request);

    List<String> listParentByTarget(OpenApiPermissionIdRequestVo request);

    List<String> listSubId(OpenApiPermissionListSubRequestVo request);

    List<PermissionTreeOutputDto> listSub(OpenApiPermissionListSubRequestVo request);

}
