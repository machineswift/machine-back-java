package com.machine.app.admin.data.shop.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.shop.business.IDataShopBusiness;
import com.machine.app.admin.data.shop.controller.vo.request.*;
import com.machine.app.admin.data.shop.controller.vo.response.DataShopCertificateResponseVo;
import com.machine.app.admin.data.shop.controller.vo.response.DataShopDetailResponseVo;
import com.machine.app.admin.data.shop.controller.vo.response.DataShopExpandListResponseVo;
import com.machine.app.admin.data.shop.controller.vo.response.DataShopSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.model.response.PageResponse;
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
@Tag(name = "【DATA】门店模块")
@RestController
@RequestMapping("admin/data/shop")
public class DataShopController {

    @Autowired
    private IDataShopBusiness shopBusiness;

    @Operation(summary = "创建门店")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建门店",
            moduleEntityId = "#request.name",
            content = "'创建门店：' + #request.name",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated DataShopCreateRequestVo request) {
        log.info("创建门店，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(shopBusiness.create(request));
    }

    @Operation(summary = "修改门店")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改门店",
            moduleEntityId = "#request.id",
            content = "'修改门店：' + #request.name")
    public void update(@RequestBody @Validated DataShopUpdateRequestVo request) {
        log.info("修改门店，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.update(request);
    }

    @Operation(summary = "修改门店经营状态")
    @PostMapping("update_business_status")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:UPDATE_BUSINESS_STATUS')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改门店经营状态",
            moduleEntityId = "#request.id",
            content = "'修改门店经营状态：' + #request.id",
            diff = false)
    public void updateBusinessStatus(@RequestBody @Validated DataShopUpdateShopBusinessStatusRequestVo request) {
        log.info("修改门店经营状态，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.updateBusinessStatus(request);
    }

    @Operation(summary = "修改门店运营状态")
    @PostMapping("update_operation_status")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:UPDATE_OPERATION_STATUS')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改门店运营状态",
            moduleEntityId = "#request.id",
            content = "'修改门店运营状态：' + #request.id",
            diff = false)
    public void updateOperationStatus(@RequestBody @Validated DataShopUpdateShopOperationStatusRequestVo request) {
        log.info("修改门店运营状态，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.updateOperationStatus(request);
    }

    @Operation(summary = "修改门店物理状态")
    @PostMapping("update_physical_status")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:UPDATE_PHYSICAL_STATUS')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改门店物理状态",
            moduleEntityId = "#request.id",
            content = "'修改门店物理状态：' + #request.id",
            diff = false)
    public void updatePhysicalStatus(@RequestBody @Validated DataShopUpdateShopPhysicalStatusRequestVo request) {
        log.info("修改门店物理状态，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.updatePhysicalStatus(request);
    }

    @Operation(summary = "修改门店证件")
    @PostMapping("update_certificate")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:UPDATE_CERTIFICATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改门店证件",
            moduleEntityId = "#request.id",
            content = "'修改门店证件：' + #request.id",
            diff = false)
    public void updateCertificate(@RequestBody @Validated DataShopUpdateCertificateRequestVo request) {
        log.info("修改门店证件，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.updateCertificate(request);
    }

    @Operation(summary = "修改门店标签选项")
    @PostMapping("update_label_option")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:UPDATE_LABEL_OPTION')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改门店标签选项",
            moduleEntityId = "#request.id",
            content = "'修改门店标签选项：' + #request.id")
    public void updateLabelOption(@RequestBody @Validated DataShopUpdateShopLabelOptionRequestVo request) {
        log.info("修改门店标签选项，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.updateLabelOption(request);
    }

    @Operation(summary = "批量修改门店标签选项")
    @PostMapping("batch_update_label_option")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:BATCH_UPDATE_LABEL_OPTION')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "批量修改门店标签选项",
            moduleEntityId = "''",
            content = "'批量修改门店标签选项'",
            diff = false)
    public void batchUpdateLabelOption(@RequestBody @Validated DataShopBatchUpdateShopLabelOptionRequestVo request) {
        log.info("批量修改门店标签选项，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.batchUpdateLabelOption(request);
    }

    @Operation(summary = "门店绑定组织")
    @PostMapping("bind_organization")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:BIND_ORGANIZATION')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "门店绑定组织",
            moduleEntityId = "''",
            content = "'门店绑定组织：' + #request.organizationId",
            diff = false)
    public void bindOrganization(@RequestBody @Validated DataShopBindOrganizationRequestVo request) {
        log.info("门店绑定组织，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.bindOrganization(request);
    }

    @Operation(summary = "查询门店详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询门店详情")
    public DataShopDetailResponseVo detail(@RequestBody @Valid IdRequest request) {
        return shopBusiness.detail(request);
    }

    @Operation(summary = "查询门店证件")
    @PostMapping("get_certificate")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:GET_CERTIFICATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询门店证件")
    public DataShopCertificateResponseVo getCertificate(@RequestBody @Valid IdRequest request) {
        return shopBusiness.getCertificate(request);
    }

    @Operation(summary = "分页查询门店(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:PAGE_SIMPLE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询门店(组件弹窗)")
    public PageResponse<DataShopSimpleListResponseVo> pageSimple(@RequestBody @Validated DataShopQueryPageRequestVo request) {
        return shopBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询门店(应用于门店菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询门店(门店菜单)")
    public PageResponse<DataShopExpandListResponseVo> pageExpand(@RequestBody @Validated DataShopQueryPageRequestVo request) {
        return shopBusiness.pageExpand(request);
    }

    @Operation(summary = "导出")
    @PostMapping("export")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:BASIC_DATA:SHOP:EXPORT')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_SHOP,
            operateType = ActionTypeEnum.EXPORT,
            operateName = "导出门店")
    public void export(@RequestBody @Validated DataShopExportRequestVo request) {
        log.info("导出门店，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.export(request);
    }

}