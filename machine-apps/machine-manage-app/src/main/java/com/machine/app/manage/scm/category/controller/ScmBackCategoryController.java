package com.machine.app.manage.scm.category.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.scm.category.business.IScmBackCategoryBusiness;
import com.machine.app.manage.scm.category.controller.vo.request.ScmBackCategoryCreateRequestVo;
import com.machine.app.manage.scm.category.controller.vo.request.ScmBackCategoryUpdateParentRequestVo;
import com.machine.app.manage.scm.category.controller.vo.request.ScmBackCategoryUpdateRequestVo;
import com.machine.app.manage.scm.category.controller.vo.response.ScmBackCategoryDetailResponseVo;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeExprandOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
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
@RequestMapping("manage/scm/back_category")
public class ScmBackCategoryController {

    @Autowired
    private IScmBackCategoryBusiness backCategoryBusiness;

    @Operation(summary = "创建后台分类")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:BACK_CATEGORY:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated ScmBackCategoryCreateRequestVo request) {
        log.info("创建后台分类，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(backCategoryBusiness.create(request));
    }

    @Operation(summary = "删除后台分类")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:BACK_CATEGORY:DELETE')")
    public void deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除后台分类，id={}", request.getId());
        backCategoryBusiness.deleteById(request);
    }

    @Operation(summary = "修改后台分类")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:BACK_CATEGORY:UPDATE')")
    public void update(@RequestBody @Validated ScmBackCategoryUpdateRequestVo request) {
        log.info("修改后台分类，request={}", JSONUtil.toJsonStr(request));
        backCategoryBusiness.update(request);
    }

    @Operation(summary = "修改父分类ID")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:BACK_CATEGORY:UPDATE_PARENT')")
    public void updateParent(@RequestBody @Validated ScmBackCategoryUpdateParentRequestVo request) {
        log.info("修改父分类，request={}", JSONUtil.toJsonStr(request));
        backCategoryBusiness.updateParent(request);
    }

    @Operation(summary = "查询后台分类详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:BACK_CATEGORY:DETAIL')")
    public ScmBackCategoryDetailResponseVo getById(@RequestBody @Valid IdRequest request) {
        return backCategoryBusiness.getById(request);
    }

    @Operation(summary = "后台分类树(应用于组件弹窗)")
    @PostMapping("tree_simple")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:BACK_CATEGORY:TREE_SIMPLE')")
    public ScmBackCategoryTreeSimpleOutputDto treeSimple() {
        return backCategoryBusiness.treeSimple();
    }

    @Operation(summary = "后台分类树(应用于组织管理菜单)")
    @PostMapping("tree_expand")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:BACK_CATEGORY:TREE_EXPAND')")
    public ScmBackCategoryTreeExprandOutputDto treeExpand() {
        return backCategoryBusiness.treeExpand();
    }

}