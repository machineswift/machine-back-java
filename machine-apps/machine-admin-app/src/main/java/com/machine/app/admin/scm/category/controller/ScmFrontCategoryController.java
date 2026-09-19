package com.machine.app.admin.scm.category.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.category.business.IScmFrontCategoryBusiness;
import com.machine.app.admin.scm.category.controller.vo.request.ScmFrontCategoryCreateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmFrontCategoryUpdateParentRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmFrontCategoryUpdateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.response.ScmFrontCategoryDetailResponseVo;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeExpandOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import com.machine.starter.web.operateLog.annotation.WebOperationLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【SCM】前台分类模块")
@RestController
@RequestMapping("admin/scm/front_category")
public class ScmFrontCategoryController {

    @Autowired
    private IScmFrontCategoryBusiness frontCategoryBusiness;

    @Operation(summary = "创建前台分类")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:FRONT:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_FRONT_CATEGORY,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建前台分类",
            moduleEntityId = "#request.name",
            content = "'创建前台分类：' + #request.name",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated ScmFrontCategoryCreateRequestVo request) {
        log.info("创建前台分类，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(frontCategoryBusiness.create(request));
    }

    @Operation(summary = "删除前台分类")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:FRONT:DELETE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_FRONT_CATEGORY,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除前台分类",
            moduleEntityId = "#request.id",
            content = "'删除前台分类：' + #request.id")
    public void deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除前台分类，id={}", request.getId());
        frontCategoryBusiness.deleteById(request);
    }

    @Operation(summary = "修改前台分类")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:FRONT:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_FRONT_CATEGORY,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改前台分类",
            moduleEntityId = "#request.id",
            content = "'修改前台分类：' + #request.name")
    public void update(@RequestBody @Validated ScmFrontCategoryUpdateRequestVo request) {
        log.info("修改前台分类，request={}", JSONUtil.toJsonStr(request));
        frontCategoryBusiness.update(request);
    }

    @Operation(summary = "修改父分类ID")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:FRONT:UPDATE_PARENT')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_FRONT_CATEGORY,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改前台分类父节点",
            moduleEntityId = "#request.id",
            content = "'修改前台分类父节点：' + #request.id",
            diff = false)
    public void updateParent(@RequestBody @Validated ScmFrontCategoryUpdateParentRequestVo request) {
        log.info("修改前台分类父ID，request={}", JSONUtil.toJsonStr(request));
        frontCategoryBusiness.updateParent(request);
    }

    @Operation(summary = "查询前台分类详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:FRONT:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_FRONT_CATEGORY,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询前台分类详情")
    public ScmFrontCategoryDetailResponseVo getById(@RequestBody @Valid IdRequest request) {
        return frontCategoryBusiness.getById(request);
    }

    @Operation(summary = "前台分类树(应用于组件弹窗)")
    @PostMapping("tree_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:FRONT:TREE_SIMPLE')")
    public ScmFrontCategoryTreeOutputDto treeSimple() {
        return frontCategoryBusiness.treeSimple();
    }

    @Operation(summary = "前台分类树(应用于组织管理菜单)")
    @PostMapping("tree_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:FRONT:TREE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_FRONT_CATEGORY,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询前台分类树(组织管理菜单)")
    public ScmFrontCategoryTreeExpandOutputDto treeExpand() {
        return frontCategoryBusiness.treeExpand();
    }

}