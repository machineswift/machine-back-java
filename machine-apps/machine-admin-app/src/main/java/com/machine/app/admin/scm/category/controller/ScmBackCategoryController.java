package com.machine.app.admin.scm.category.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.category.business.IScmBackCategoryBusiness;
import com.machine.app.admin.scm.category.controller.vo.request.ScmBackCategoryCreateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmBackCategoryUpdateParentRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmBackCategoryUpdateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.response.ScmBackCategoryDetailResponseVo;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeExprandOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
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
@Tag(name = "【SCM】后台分类模块")
@RestController
@RequestMapping("admin/scm/back_category")
public class ScmBackCategoryController {

    @Autowired
    private IScmBackCategoryBusiness backCategoryBusiness;

    @Operation(summary = "创建后台分类")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:BACK:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_BACK_CATEGORY,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建后台分类",
            moduleEntityId = "#request.name",
            content = "'创建后台分类：' + #request.name",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated ScmBackCategoryCreateRequestVo request) {
        log.info("创建后台分类，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(backCategoryBusiness.create(request));
    }

    @Operation(summary = "删除后台分类")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:BACK:DELETE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_BACK_CATEGORY,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除后台分类",
            moduleEntityId = "#request.id",
            content = "'删除后台分类：' + #request.id")
    public void deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除后台分类，id={}", request.getId());
        backCategoryBusiness.deleteById(request);
    }

    @Operation(summary = "修改后台分类")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:BACK:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_BACK_CATEGORY,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改后台分类",
            moduleEntityId = "#request.id",
            content = "'修改后台分类：' + #request.name")
    public void update(@RequestBody @Validated ScmBackCategoryUpdateRequestVo request) {
        log.info("修改后台分类，request={}", JSONUtil.toJsonStr(request));
        backCategoryBusiness.update(request);
    }

    @Operation(summary = "修改父分类ID")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:BACK:UPDATE_PARENT')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_BACK_CATEGORY,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改后台分类父节点",
            moduleEntityId = "#request.id",
            content = "'修改后台分类父节点：' + #request.id",
            diff = false)
    public void updateParent(@RequestBody @Validated ScmBackCategoryUpdateParentRequestVo request) {
        log.info("修改父分类，request={}", JSONUtil.toJsonStr(request));
        backCategoryBusiness.updateParent(request);
    }

    @Operation(summary = "查询后台分类详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:BACK:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_BACK_CATEGORY,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询后台分类详情")
    public ScmBackCategoryDetailResponseVo getById(@RequestBody @Valid IdRequest request) {
        return backCategoryBusiness.getById(request);
    }

    @Operation(summary = "后台分类树(应用于组件弹窗)")
    @PostMapping("tree_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:BACK:TREE_SIMPLE')")
    public ScmBackCategoryTreeSimpleOutputDto treeSimple() {
        return backCategoryBusiness.treeSimple();
    }

    @Operation(summary = "后台分类树(应用于组织管理菜单)")
    @PostMapping("tree_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SCM:CATEGORY:BACK:TREE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.SCM,
            moduleEntity = ModuleEntityEnum.SCM_BACK_CATEGORY,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询后台分类树(组织管理菜单)")
    public ScmBackCategoryTreeExprandOutputDto treeExpand() {
        return backCategoryBusiness.treeExpand();
    }

}