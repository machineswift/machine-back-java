package com.machine.app.openapi.iam.user.business;

import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserIdRequestVo;
import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserListSimpleRequestVo;
import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserPhoneRequestVo;
import com.machine.app.openapi.iam.user.controller.vo.response.OpenApiUserListSimpleResponseVo;
import com.machine.app.openapi.iam.user.controller.vo.response.OpenapiUserDetailResponseVo;

import java.util.List;

public interface IOpenApiUserBusiness {

    String userIdByPhone(OpenApiUserPhoneRequestVo request);

    OpenapiUserDetailResponseVo detail(OpenApiUserIdRequestVo request);

    List<OpenApiUserListSimpleResponseVo> listSimple(OpenApiUserListSimpleRequestVo request);

}
