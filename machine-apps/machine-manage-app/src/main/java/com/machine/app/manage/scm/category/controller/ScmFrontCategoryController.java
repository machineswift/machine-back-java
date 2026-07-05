package com.machine.app.manage.scm.category.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.scm.category.business.IScmFrontCategoryBusiness;
import com.machine.app.manage.scm.category.controller.vo.request.ScmFrontCategoryCreateRequestVo;
import com.machine.app.manage.scm.category.controller.vo.request.ScmFrontCategoryUpdateParentRequestVo;
import com.machine.app.manage.scm.category.controller.vo.request.ScmFrontCategoryUpdateRequestVo;
import com.machine.app.manage.scm.category.controller.vo.response.ScmFrontCategoryDetailResponseVo;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeExpandOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeOutputDto;
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
@Tag(name = "【SCM】前台分类模块")
@RestController
@RequestMapping("manage/scm/front_category")
public class ScmFrontCategoryController {

    @Autowired
    private IScmFrontCategoryBusiness frontCategoryBusiness;

    @Operation(summary = "创建前台分类")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:FRONT_CATEGORY:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated ScmFrontCategoryCreateRequestVo request) {
        log.info("创建前台分类，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(frontCategoryBusiness.create(request));
    }

    @Operation(summary = "删除前台分类")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:FRONT_CATEGORY:DELETE')")
    public void deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除前台分类，id={}", request.getId());
        frontCategoryBusiness.deleteById(request);
    }

    @Operation(summary = "修改前台分类")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:FRONT_CATEGORY:UPDATE')")
    public void update(@RequestBody @Validated ScmFrontCategoryUpdateRequestVo request) {
        log.info("修改前台分类，request={}", JSONUtil.toJsonStr(request));
        frontCategoryBusiness.update(request);
    }

    @Operation(summary = "修改父分类ID")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:FRONT_CATEGORY:UPDATE_PARENT')")
    public void updateParent(@RequestBody @Validated ScmFrontCategoryUpdateParentRequestVo request) {
        log.info("修改前台分类父ID，request={}", JSONUtil.toJsonStr(request));
        frontCategoryBusiness.updateParent(request);
    }

    @Operation(summary = "查询前台分类详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:FRONT_CATEGORY:DETAIL')")
    public ScmFrontCategoryDetailResponseVo getById(@RequestBody @Valid IdRequest request) {
        return frontCategoryBusiness.getById(request);
    }

    @Operation(summary = "前台分类树(应用于组件弹窗)")
    @PostMapping("tree_simple")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:FRONT_CATEGORY:TREE_SIMPLE')")
    public ScmFrontCategoryTreeOutputDto treeSimple() {
        return frontCategoryBusiness.treeSimple();
    }

    @Operation(summary = "前台分类树(应用于组织管理菜单)")
    @PostMapping("tree_expand")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:FRONT_CATEGORY:TREE_EXPAND')")
    public ScmFrontCategoryTreeExpandOutputDto treeExpand() {
        return frontCategoryBusiness.treeExpand();
    }

}