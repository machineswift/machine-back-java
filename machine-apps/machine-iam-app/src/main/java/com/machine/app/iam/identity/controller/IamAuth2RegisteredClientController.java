package com.machine.app.iam.identity.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.identity.businss.IIamAuth2RegisteredClientBusiness;
import com.machine.app.iam.identity.controller.vo.request.*;
import com.machine.app.iam.identity.controller.vo.response.IamAuth2RegisteredClientDetailResponseVo;
import com.machine.app.iam.identity.controller.vo.response.IamAuth2RegisteredClientListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "【IAM】认证中心-客户端")
@RequestMapping("iam/identity_center/auth2_registered_client")
public class IamAuth2RegisteredClientController {

    @Autowired
    private IIamAuth2RegisteredClientBusiness auth2RegisteredClientBusiness;

    @Operation(summary = "创建客户端")
    @PostMapping("create")
    @PreAuthorize("hasAnyRole('ROOT') && hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated IamAuth2RegisteredClientCreateRequestVo request) {
        log.info("认证中心创建客户端，request={}", request);
        return new IdResponse<>(auth2RegisteredClientBusiness.create(request));
    }

    @Operation(summary = "修改客户端")
    @PostMapping("update")
    @PreAuthorize("hasAnyRole('ROOT') && hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:UPDATE')")
    public void update(@RequestBody @Validated IamAuth2RegisteredClientUpdateRequestVo request) {
        log.info("认证中心修改客户端信息，request={}", request);
        auth2RegisteredClientBusiness.update(request);
    }

    @Operation(summary = "修改客户端状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAnyRole('ROOT') && hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:UPDATE_STATUS')")
    public void updateStatus(@RequestBody @Validated IamAuth2RegisteredClientUpdateStatusRequestVo request) {
        log.info("认证中心修改客户端状态，request={}", JSONUtil.toJsonStr(request));
        auth2RegisteredClientBusiness.updateStatus(request);
    }

    @Operation(summary = "删除客户端")
    @PostMapping("delete")
    @PreAuthorize("hasAnyRole('ROOT') && hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:DELETE')")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("认证中心删除客户端，request={}", JSONUtil.toJsonStr(request));
        auth2RegisteredClientBusiness.delete(request);
    }

    @Operation(summary = "客户端详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyRole('ROOT') && hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:DETAIL')")
    public IamAuth2RegisteredClientDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return auth2RegisteredClientBusiness.detail(request);
    }

    @Operation(summary = "分页查询用户(应用于管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAnyRole('ROOT') && hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:PAGE_EXPAND')")
    public PageResponse<IamAuth2RegisteredClientListResponseVo> pageExpand(@RequestBody IamAuth2RegisteredClientPageQueryRequestVo query) {
        return auth2RegisteredClientBusiness.pageExpand(query);
    }
}
