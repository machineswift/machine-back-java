package com.machine.app.suprr.iam.organization.controller;

import com.machine.app.suprr.iam.organization.business.ISuperOrganizationBusiness;
import com.machine.app.suprr.iam.organization.controller.vo.request.SupeOrganizationTreeAllRequestVo;
import com.machine.app.suprr.iam.organization.controller.vo.request.SupeOrganizationTreeRequestVo;
import com.machine.app.suprr.iam.organization.controller.vo.response.SuperOrganizationTreeExpandSelfResponseVo;
import com.machine.app.suprr.iam.organization.controller.vo.response.SuperOrganizationTreeSimpleSelfResponseVo;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【IAM】组织模块")
@RestController
@RequestMapping("super/iam/organization")
public class SuperOrganizationController {

    @Autowired
    private ISuperOrganizationBusiness organizationBusiness;

    @Operation(summary = "树(当前用户关联的组织基础信息)")
    @PostMapping("tree_self_simple")
    public SuperOrganizationTreeSimpleSelfResponseVo treeSelfSimple(@RequestBody @Validated SupeOrganizationTreeRequestVo request) {
        return organizationBusiness.treeSelfSimple(request);
    }

    @Operation(summary = "树(当前用户关联的组织扩展信息)")
    @PostMapping("tree_self_expand")
    public SuperOrganizationTreeExpandSelfResponseVo treeSelfExpand(@RequestBody @Validated SupeOrganizationTreeRequestVo request) {
        return organizationBusiness.treeSelfExpand(request);
    }

    @Operation(summary = "树(所有的组织基础信息)")
    @PostMapping("tree_all_simple")
    public IamOrganizationTreeSimpleOutputDto treeAllSimple(@RequestBody @Validated SupeOrganizationTreeAllRequestVo request) {
        return organizationBusiness.treeAllSimple(request);
    }
}
