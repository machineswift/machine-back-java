package com.machine.app.openapi.iam.role.business;

import com.machine.app.openapi.iam.role.controller.vo.request.OpenApiRoleIdRequestVo;
import com.machine.app.openapi.iam.role.controller.vo.request.OpenApiRoleListSubRequestVo;
import com.machine.app.openapi.iam.role.controller.vo.request.OpenApiRoleRootRequestVo;
import com.machine.app.openapi.iam.role.controller.vo.response.OpenApiRoleDetailResponseVo;
import com.machine.app.openapi.iam.role.controller.vo.response.OpenApiRolePermissionResponseVo;

import java.util.List;

public interface IOpenApiRoleBusiness {

    String rootId(OpenApiRoleRootRequestVo request);

    OpenApiRoleDetailResponseVo detail(OpenApiRoleIdRequestVo request);

    List<String> listSubId(OpenApiRoleListSubRequestVo request);

    List<String> listParentByTarget(OpenApiRoleIdRequestVo request);

    OpenApiRolePermissionResponseVo listPermissionByTarget(OpenApiRoleIdRequestVo request);
}
