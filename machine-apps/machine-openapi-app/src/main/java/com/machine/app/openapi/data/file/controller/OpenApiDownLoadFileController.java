package com.machine.app.openapi.data.file.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.file.business.IOpenApiDownLoadFileBusiness;
import com.machine.app.openapi.data.file.controller.vo.request.DownloadFileCreatFinishRequestVo;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.response.IdResponse;
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
@Tag(name = "【下载中心】")
@RestController
@RequestMapping("openapi/data/download_center")
public class OpenApiDownLoadFileController {

    @Autowired
    private IOpenApiDownLoadFileBusiness downLoadFileBusiness;

    @Operation(summary = "新增文件")
    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('data')")
    public IdResponse<String> create(@RequestBody @Validated DownloadFileCreatFinishRequestVo request) {
        log.info("下载中心新增文件, clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        return new IdResponse<>(downLoadFileBusiness.create(request));
    }
}