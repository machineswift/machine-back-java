package com.machine.app.iam.biam.identity.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.identity.businss.IBIamAuth2RegisteredClientBusiness;
import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientCreateRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientPageQueryRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientUpdateRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientUpdateStatusRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.response.BIamAuth2RegisteredClientDetailResponseVo;
import com.machine.app.iam.biam.identity.controller.vo.response.BIamAuth2RegisteredClientListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "【BIAM】认证中心-客户端")
@RequestMapping("iam/biam/identity_center/auth2_registered_client")
public class BIamAuth2RegisteredClientController {

    @Autowired
    private IBIamAuth2RegisteredClientBusiness auth2RegisteredClientBusiness;

    @Operation(summary = "清理缓存")
    @GetMapping("clean_cache")
    @PreAuthorize("hasAnyRole('ROOT') && hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:CLEAN_CACHE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_AUTH2_CLIENT,
            operateType = ActionTypeEnum.UNKNOWN,
            operateName = "清理认证客户端缓存")
    public void cleanCache() {
        log.info("清理缓存");
        auth2RegisteredClientBusiness.cleanCache();
    }

    @Operation(summary = "创建客户端")
    @PostMapping("create")
    @PreAuthorize("hasAnyRole('ROOT') && hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:CREATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_AUTH2_CLIENT,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建认证客户端",
            responseEnable = true,
            sanitizeKeys = {"clientSecret"})
    public IdResponse<String> create(@RequestBody @Validated BIamAuth2RegisteredClientCreateRequestVo request) {
        log.info("认证中心创建客户端，request={}", request);
        return new IdResponse<>(auth2RegisteredClientBusiness.create(request));
    }

    @Operation(summary = "修改客户端")
    @PostMapping("update")
    @PreAuthorize("hasAnyRole('ROOT') && hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:UPDATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_AUTH2_CLIENT,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改认证客户端",
            sanitizeKeys = {"clientSecret"})
    public void update(@RequestBody @Validated BIamAuth2RegisteredClientUpdateRequestVo request) {
        log.info("认证中心修改客户端信息，request={}", request);
        auth2RegisteredClientBusiness.update(request);
    }

    @Operation(summary = "修改客户端状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAnyRole('ROOT') && hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:UPDATE_STATUS')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_AUTH2_CLIENT,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改认证客户端状态")
    public void updateStatus(@RequestBody @Validated BIamAuth2RegisteredClientUpdateStatusRequestVo request) {
        log.info("认证中心修改客户端状态，request={}", JSONUtil.toJsonStr(request));
        auth2RegisteredClientBusiness.updateStatus(request);
    }

    @Operation(summary = "删除客户端")
    @PostMapping("delete")
    @PreAuthorize("hasAnyRole('ROOT') && hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:DELETE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_AUTH2_CLIENT,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除认证客户端")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("认证中心删除客户端，request={}", JSONUtil.toJsonStr(request));
        auth2RegisteredClientBusiness.delete(request);
    }

    @Operation(summary = "客户端详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_AUTH2_CLIENT,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询认证客户端详情")
    public BIamAuth2RegisteredClientDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return auth2RegisteredClientBusiness.detail(request);
    }

    @Operation(summary = "分页查询用户(应用于管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:IDENTITY_CENTER:AUTH2_REGISTERED_CLIENT:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_AUTH2_CLIENT,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询认证客户端")
    public PageResponse<BIamAuth2RegisteredClientListResponseVo> pageExpand(
            @RequestBody BIamAuth2RegisteredClientPageQueryRequestVo query) {
        return auth2RegisteredClientBusiness.pageExpand(query);
    }
}
