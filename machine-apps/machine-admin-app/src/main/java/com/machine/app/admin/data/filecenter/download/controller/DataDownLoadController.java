package com.machine.app.admin.data.filecenter.download.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.filecenter.download.business.IDownLoadCenterBusiness;
import com.machine.app.admin.data.filecenter.download.controller.vo.request.DataDownloadPageRequestVo;
import com.machine.app.admin.data.filecenter.download.controller.vo.response.DataDownloadDetailResponseVo;
import com.machine.app.admin.data.filecenter.download.controller.vo.response.DataDownloadListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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
    @PreAuthorize("hasAuthority('SYSTEM:DATA:DOWNLOAD:RETRY')")
    public void retry(@RequestBody @Validated IdRequest request) {
        log.info("下载中心重试，request={}", JSONUtil.toJsonStr(request));
        downLoadBusiness.retry(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:DATA:DOWNLOAD:DETAIL')")
    public DataDownloadDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return downLoadBusiness.detail(request);
    }

    @Operation(summary = "下载文件")
    @PostMapping("download_file")
    @PreAuthorize("hasAuthority('SYSTEM:DATA:DOWNLOAD:DOWNLOAD_FILE')")
    public void downloadFile(@RequestBody @Validated IdRequest request,
                             HttpServletResponse response) {
        log.info("下载中心下载文件，request={}", JSONUtil.toJsonStr(request));
        downLoadBusiness.downloadFile(request, response);
    }

    @Operation(summary = "分页查询(应用于角色管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('SYSTEM:DATA:DOWNLOAD:PAGE_EXPAND')")
    public PageResponse<DataDownloadListResponseVo> pageExpand(@RequestBody @Validated DataDownloadPageRequestVo request) {
        return downLoadBusiness.pageExpand(request);
    }

}