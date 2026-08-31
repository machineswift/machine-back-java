package com.machine.app.openapi.iam.permission.business;

import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionIdRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionListSubRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionQueryAppListRequestVo;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;

import java.util.List;

public interface IOpenApiPermissionBusiness {

    List<BIamPermissionTreeOutputDto> listApp(OpenApiPermissionQueryAppListRequestVo request);

    BIamPermissionTreeOutputDto detail(OpenApiPermissionIdRequestVo request);

    List<String> listParentByTarget(OpenApiPermissionIdRequestVo request);

    List<String> listSubId(OpenApiPermissionListSubRequestVo request);

    List<BIamPermissionTreeOutputDto> listSub(OpenApiPermissionListSubRequestVo request);

}
