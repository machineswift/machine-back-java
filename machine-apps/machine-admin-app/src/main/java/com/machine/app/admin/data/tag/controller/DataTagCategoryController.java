package com.machine.app.admin.data.tag.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.tag.business.IDataTagCategoryBusiness;
import com.machine.app.admin.data.tag.controller.vo.request.*;
import com.machine.app.admin.data.tag.controller.vo.response.DataTagCategoryDetailResponseVo;
import com.machine.client.data.tag.dto.output.DataTagCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【DATA】智能标签分类")
@RestController
@RequestMapping("admin/data/tag_category")
public class DataTagCategoryController {

    @Autowired
    private IDataTagCategoryBusiness tagCategoryBusiness;

    @Operation(summary = "创建智能标签分类")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:TAG_CATEGORY:CREATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_TAG,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建智能标签分类",
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated DataTagCategoryCreateRequestVo request) {
        log.info("创建智能标签分类，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(tagCategoryBusiness.create(request));
    }

    @Operation(summary = "删除智能标签分类")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:TAG_CATEGORY:DELETE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_TAG,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除智能标签分类")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除智能标签分类，request={}", JSONUtil.toJsonStr(request));
        tagCategoryBusiness.delete(request);
    }

    @Operation(summary = "修改智能标签分类")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:TAG_CATEGORY:UPDATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_TAG,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改智能标签分类")
    public void update(@RequestBody @Validated DataTagCategoryUpdateRequestVo request) {
        log.info("修改智能标签分类，request={}", JSONUtil.toJsonStr(request));
        tagCategoryBusiness.update(request);
    }

    @Operation(summary = "修改智能标签分类排序")
    @PostMapping("update_sort")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:TAG_CATEGORY:UPDATE_SORT')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_TAG,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改智能标签分类排序")
    public void updateSort(@RequestBody @Validated DataTagCategoryUpdateSortRequestVo request) {
        log.info("修改智能标签分类排序，request={}", JSONUtil.toJsonStr(request));
        tagCategoryBusiness.updateSort(request);
    }

    @Operation(summary = "修改智能标签分类父ID")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:TAG_CATEGORY:UPDATE_PARENT')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_TAG,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改智能标签分类父ID")
    public void updateParent(@RequestBody @Validated DataTagCategoryUpdateParentRequestVo request) {
        log.info("修改智能标签分类父ID，request={}", JSONUtil.toJsonStr(request));
        tagCategoryBusiness.updateParent(request);
    }

    @Operation(summary = "智能标签分类详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:TAG_CATEGORY:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_TAG,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询智能标签分类详情")
    public DataTagCategoryDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return tagCategoryBusiness.detail(request);
    }

    @Operation(summary = "智能标签分类树(应用于组件弹窗)")
    @PostMapping("tree_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:TAG_CATEGORY:TREE_SIMPLE')")
    public DataTagCategoryTreeSimpleOutputDto treeSimple(@RequestBody @Validated DataTagCategoryTreeRequestVo request) {
        return tagCategoryBusiness.treeSimple(request);
    }

}

