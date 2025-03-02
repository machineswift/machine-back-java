package com.machine.app.manage.data.file.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.file.business.IDownLoadCenterBusiness;
import com.machine.app.manage.data.file.controller.vo.request.DownloadFilePageRequestVo;
import com.machine.app.manage.data.file.controller.vo.response.QueryDownloadFileDetailResponseVo;
import com.machine.app.manage.data.file.controller.vo.response.QueryDownloadFileListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Tag(name = "【下载中心】")
@RestController
@RequestMapping("manage/data/download")
public class DownLoadCenterController {

    @Autowired
    private IDownLoadCenterBusiness downLoadFileBusiness;

    @Operation(summary = "下载中心调度任务")
    @PostMapping("schedule_task")
    public void scheduleTask(@RequestBody @Validated IdRequest request) {
        log.info("下载中心调度任务，request={}", JSONUtil.toJsonStr(request));
        downLoadFileBusiness.scheduleTask(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    public QueryDownloadFileDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return downLoadFileBusiness.detail(request);
    }

    @Operation(summary = "分页")
    @PostMapping("page")
    public PageResponse<QueryDownloadFileListResponseVo> page(@RequestBody @Validated DownloadFilePageRequestVo request) {
        return downLoadFileBusiness.page(request);
    }
}