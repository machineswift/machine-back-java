package com.machine.app.admin.data.brand.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.brand.business.IDataBrandCategoryBusiness;
import com.machine.app.admin.data.brand.controller.vo.request.DataBrandCategoryCreateRequestVo;
import com.machine.app.admin.data.brand.controller.vo.request.DataBrandCategoryUpdateParentRequestVo;
import com.machine.app.admin.data.brand.controller.vo.request.DataBrandCategoryUpdateRequestVo;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandCategoryDetailResponseVo;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandCategorySimpleTreeResponseVo;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import com.machine.starter.web.operateLog.annotation.WebOperationLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【DATA】品牌分类管理")
@RestController
@RequestMapping("admin/data/brand_category")
public class DataBrandCategoryController {

    @Autowired
    private IDataBrandCategoryBusiness brandCategoryBusiness;

    @Operation(summary = "新增品牌分类")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:BRAND_CATEGORY:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_BRAND_CATEGORY,
            operateType = ActionTypeEnum.CREATE,
            operateName = "新增品牌分类",
            moduleEntityId = "#request.name",
            content = "'新增品牌分类：' + #request.name",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated DataBrandCategoryCreateRequestVo request) {
        log.info("新增品牌分类，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(brandCategoryBusiness.create(request));
    }

    @Operation(summary = "删除品牌分类")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:BRAND_CATEGORY:DELETE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_BRAND_CATEGORY,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除品牌分类",
            moduleEntityId = "#request.id",
            content = "'删除品牌分类：' + #request.id")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除品牌分类，request={}", JSONUtil.toJsonStr(request));
        brandCategoryBusiness.delete(request);
    }

    @Operation(summary = "修改品牌分类")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:BRAND_CATEGORY:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_BRAND_CATEGORY,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改品牌分类",
            moduleEntityId = "#request.id",
            content = "'修改品牌分类：' + #request.name")
    public void update(@RequestBody @Validated DataBrandCategoryUpdateRequestVo request) {
        log.info("修改品牌分类，request={}", JSONUtil.toJsonStr(request));
        brandCategoryBusiness.update(request);
    }

    @Operation(summary = "修改品牌分类父节点")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:BRAND_CATEGORY:UPDATE_PARENT')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_BRAND_CATEGORY,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改品牌分类父节点",
            moduleEntityId = "#request.id",
            content = "'修改品牌分类父节点：' + #request.id")
    public void updateParent(@RequestBody @Validated DataBrandCategoryUpdateParentRequestVo request) {
        log.info("修改品牌分类父节点，request={}", JSONUtil.toJsonStr(request));
        brandCategoryBusiness.updateParent(request);
    }

    @Operation(summary = "品牌分类详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:BRAND_CATEGORY:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_BRAND_CATEGORY,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询品牌分类详情")
    public DataBrandCategoryDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return brandCategoryBusiness.detail(request);
    }

    @Operation(summary = "品牌分类树(选择器)")
    @GetMapping("tree_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:BRAND_CATEGORY:TREE_SIMPLE')")
    public DataBrandCategorySimpleTreeResponseVo treeAllSimple() {
        return brandCategoryBusiness.treeAllSimple();
    }
}
