package com.machine.app.openapi.iam.user.controller;

import com.machine.app.openapi.iam.user.business.IOpenApiUserBusiness;
import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserIdRequestVo;
import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserListSimpleRequestVo;
import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserPhoneRequestVo;
import com.machine.app.openapi.iam.user.controller.vo.response.OpenApiUserListSimpleResponseVo;
import com.machine.app.openapi.iam.user.controller.vo.response.OpenapiUserDetailResponseVo;
import com.machine.sdk.common.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "【IAM】用户模块")
@RestController
@RequestMapping("openapi/iam/user")
public class IOpenApiUserController {

    @Autowired
    private IOpenApiUserBusiness userBusiness;

    @Operation(summary = "获取用户Id(根据手机号)")
    @PostMapping("userId_by_phone")
    @PreAuthorize("hasAnyAuthority('iam','iam_user')")
    public IdResponse<String> userIdByPhone(@RequestBody @Valid OpenApiUserPhoneRequestVo request) {
        return new IdResponse<>(userBusiness.userIdByPhone(request));
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyAuthority('iam','iam_user')")
    public OpenapiUserDetailResponseVo detail(@RequestBody @Valid OpenApiUserIdRequestVo request) {
        return userBusiness.detail(request);
    }

    @Operation(summary = "获取用户列表(基础信息)")
    @PostMapping("list_simple")
    @PreAuthorize("hasAnyAuthority('iam','iam_user')")
    public List<OpenApiUserListSimpleResponseVo> listSimple(@RequestBody @Valid OpenApiUserListSimpleRequestVo request) {
        return userBusiness.listSimple(request);
    }
}