package com.machine.app.manage.data.file.material.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.file.material.business.IDataMaterialBusiness;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialUrlResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCreateRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialQueryPageRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialUpdateRequestVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】素材模块")
@RestController
@RequestMapping("manage/data/file/material")
public class DataMaterialController {

    @Autowired
    private IDataMaterialBusiness materialBusiness;

    @Operation(summary = "新增素材")
    @PostMapping("create")
    public void create(@RequestBody @Validated DataMaterialCreateRequestVo request) {
        log.info("新增素材，request={}", JSONUtil.toJsonStr(request));
        materialBusiness.create(request);
    }

    @Operation(summary = "修改素材")
    @PostMapping("update")
    public void update(@RequestBody @Validated DataMaterialUpdateRequestVo request) {
        log.info("修改素材，request={}", JSONUtil.toJsonStr(request));
        materialBusiness.update(request);
    }

    @PermitAll
    @Operation(summary = "获取素材地址")
    @GetMapping("get_url")
    public DataMaterialUrlResponseVo getUrl(@RequestParam("materialId") String materialId,
                                            @RequestParam(value = "expireSecond", required = false) Integer expireSecond) {
        String url;
        if (null != expireSecond && expireSecond > 0) {
            url = materialBusiness.getPresignedUrl(materialId, expireSecond);
        } else {
            url = materialBusiness.getPresignedUrl(materialId);
        }
        return new DataMaterialUrlResponseVo(url);
    }

    @PermitAll
    @Operation(summary = "获取素材缩略图地址")
    @GetMapping("get_thumbnail_url")
    public DataMaterialUrlResponseVo getThumbnailUrl(@RequestParam("materialId") String materialId,
                                                     @RequestParam(value = "expireSecond", required = false) Integer expireSecond) {
        String url;
        if (null != expireSecond && expireSecond > 0) {
            url = materialBusiness.getThumbnailUrl(materialId, expireSecond);
        } else {
            url = materialBusiness.getThumbnailUrl(materialId);
        }
        return new DataMaterialUrlResponseVo(url);
    }

    @Operation(summary = "素材详情")
    @PostMapping("detail")
    public DataMaterialDetailResponseVo detail(@RequestBody IdRequest request) {
        return materialBusiness.detail(request);
    }

    @Operation(summary = "分页查询素材(应用于角色管理菜单)")
    @PostMapping("page_expand")
    public PageResponse<DataMaterialExpandListResponseVo> pageExpand(@RequestBody @Validated DataMaterialQueryPageRequestVo request) {
        return materialBusiness.pageExpand(request);
    }
}

