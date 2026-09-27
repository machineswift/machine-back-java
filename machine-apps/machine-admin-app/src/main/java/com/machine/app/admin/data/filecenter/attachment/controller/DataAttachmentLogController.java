package com.machine.app.admin.data.filecenter.attachment.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.filecenter.attachment.business.IDataAttachmentLogBusiness;
import com.machine.app.admin.data.filecenter.attachment.controller.vo.request.DataAttachmentLogQueryPageRequestVo;
import com.machine.app.admin.data.filecenter.attachment.controller.vo.response.DataAttachmentLogDetailResponseVo;
import com.machine.app.admin.data.filecenter.attachment.controller.vo.response.DataAttachmentLogExpandListResponseVo;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】附件日志模块")
@RestController
@RequestMapping("admin/data/file_center/attachment_log")
public class DataAttachmentLogController {

    @Autowired
    private IDataAttachmentLogBusiness attachmentLogBusiness;

    @Operation(summary = "查询附件操作日志详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:DATA:ATTACHMENT_LOG:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_ATTACHMENT,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询附件操作日志详情")
    public DataAttachmentLogDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        log.info("查询附件操作日志详情,request:{}", JSONUtil.toJsonStr(request));
        return attachmentLogBusiness.detail(request);
    }

    @Operation(summary = "分页查询附件操作日志")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:DATA:ATTACHMENT_LOG:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_ATTACHMENT,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询附件操作日志")
    public PageResponse<DataAttachmentLogExpandListResponseVo> pageExpand(@RequestBody @Validated DataAttachmentLogQueryPageRequestVo request) {
        log.info("分页查询附件操作日志,request:{}", JSONUtil.toJsonStr(request));
        return attachmentLogBusiness.pageExpand(request);
    }

}

