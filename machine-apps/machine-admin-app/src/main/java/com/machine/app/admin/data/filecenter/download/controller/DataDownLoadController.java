package com.machine.app.admin.data.filecenter.download.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.filecenter.download.business.IDownLoadCenterBusiness;
import com.machine.app.admin.data.filecenter.download.controller.vo.request.DataDownloadPageRequestVo;
import com.machine.app.admin.data.filecenter.download.controller.vo.response.DataDownloadDetailResponseVo;
import com.machine.app.admin.data.filecenter.download.controller.vo.response.DataDownloadListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import com.machine.starter.web.operateLog.annotation.WebOperationLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【下载中心】")
@RestController
@RequestMapping("admin/data/file_center/download")
public class DataDownLoadController {

    @Autowired
    private IDownLoadCenterBusiness downLoadBusiness;

    @Operation(summary = "重试")
    @PostMapping("retry")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:DATA:DOWNLOAD:RETRY')")
    @WebOperationLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_DOWNLOAD,
            operateType = ActionTypeEnum.UNKNOWN,
            operateName = "下载中心重试",
            moduleEntityId = "#request.id",
            content = "'下载中心重试：' + #request.id",
            diff = false)
    public void retry(@RequestBody @Validated IdRequest request) {
        log.info("下载中心重试，request={}", JSONUtil.toJsonStr(request));
        downLoadBusiness.retry(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:DATA:DOWNLOAD:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_DOWNLOAD,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询下载中心详情")
    public DataDownloadDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return downLoadBusiness.detail(request);
    }

    @Operation(summary = "分页查询(应用于角色管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:DATA:DOWNLOAD:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_DOWNLOAD,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询下载记录(管理菜单)")
    public PageResponse<DataDownloadListResponseVo> pageExpand(@RequestBody @Validated DataDownloadPageRequestVo request) {
        return downLoadBusiness.pageExpand(request);
    }

}