package com.machine.app.openapi.iam.organization.controller;

import com.machine.app.openapi.iam.organization.business.IOpenApiOrganizationBusiness;
import com.machine.app.openapi.iam.organization.controller.vo.request.OpenApiOrganizationIdRequestVo;
import com.machine.app.openapi.iam.organization.controller.vo.request.OpenApiOrganizationRootIdRequestVo;
import com.machine.app.openapi.iam.organization.controller.vo.response.OpenApiOrganizationDetailResponseVo;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
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
@Tag(name = "【IAM】组织模块")
@RestController
@RequestMapping("openapi/iam/organization")
public class IOpenApiOrganizationController {

    @Autowired
    private IOpenApiOrganizationBusiness organizationBusiness;

    @PreAuthorize("hasAnyAuthority('iam','iam_organization')")
    @Operation(summary = "根组织ID")
    @PostMapping("root_id")
    public IdResponse<String> rootId(@RequestBody @Valid OpenApiOrganizationRootIdRequestVo request) {
        return new IdResponse<>(organizationBusiness.rootId(request));
    }

    @PreAuthorize("hasAnyAuthority('iam','iam_organization')")
    @Operation(summary = "获取组织详情")
    @PostMapping("detail")
    public OpenApiOrganizationDetailResponseVo detail(@RequestBody @Valid OpenApiOrganizationIdRequestVo request) {
        return organizationBusiness.detail(request);
    }

    @PreAuthorize("hasAnyAuthority('iam','iam_organization')")
    @Operation(summary = "获取子组织ID列表",
            description = "本接口不受授权范围限制。\n" +
                    "本接口只支持获取下一级所有组织ID列表。")
    @PostMapping("list_sub_id")
    public List<String> listSubId(@RequestBody @Valid OpenApiOrganizationIdRequestVo request) {
        return organizationBusiness.listSubId(request);
    }

    @PreAuthorize("hasAnyAuthority('iam','iam_organization')")
    @Operation(summary = "获取子组织列表",
            description = "本接口只支持获取当前组织的下一级组织基础信息，不支持获取当前组织下所有层级子组织。")
    @PostMapping("list_sub")
    public List<IamOrganizationTreeSimpleOutputDto> listSub(@RequestBody @Valid OpenApiOrganizationIdRequestVo request) {
        return organizationBusiness.listSub(request);
    }

    @PreAuthorize("hasAnyAuthority('iam','iam_organization')")
    @Operation(summary = "获取指定组织的所有父组织ID列表",
            description = "该接口不受通讯录权限范围限制。\n" +
                    "获取的list元素第一个是当前组织ID，最后一个是根组织ID，从左至右组织层级递增。")
    @PostMapping("list_parent_by_target")
    public List<String> listParentByTarget(@RequestBody @Valid OpenApiOrganizationIdRequestVo request) {
        return organizationBusiness.listParentByTarget(request);
    }
}