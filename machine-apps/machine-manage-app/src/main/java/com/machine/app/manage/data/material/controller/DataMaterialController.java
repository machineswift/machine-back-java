package com.machine.app.manage.data.material.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.material.business.IDataMaterialBusiness;
import com.machine.app.manage.data.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.manage.data.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.manage.data.material.controller.vo.response.DataMaterialUrlResponseVo;
import com.machine.app.manage.data.material.controller.vo.resquest.*;
import com.machine.sdk.common.envm.data.material.DataMaterIalTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "【DATA】素材模块")
@RestController
@RequestMapping("manage/data/material")
public class DataMaterialController {

    @Autowired
    private IDataMaterialBusiness materialBusiness;

    @PostMapping("upload")
    public IdResponse<String> upload(@RequestParam("materIalType") DataMaterIalTypeEnum materIalType,
                                     @RequestParam("file") MultipartFile file) {
        log.info("上传素材, materIalType:{} fileName:{} length:{}",
                materIalType, file.getOriginalFilename(), file.getSize());
        return new IdResponse<>(materialBusiness.upload(materIalType, file));
    }

    @PostMapping("upload_image")
    public IdResponse<String> uploadImage(@RequestParam("thumbnailWeight") int thumbnailWeight,
                                          @RequestParam("thumbnailHeight") int thumbnailHeight,
                                          @RequestParam("file") MultipartFile file) {
        log.info("上传图片素材, thumbnailWeight:{} thumbnailHeight:{} fileName:{} length:{}",
                thumbnailWeight, thumbnailHeight, file.getOriginalFilename(), file.getSize());
        return new IdResponse<>(materialBusiness.uploadImage(thumbnailWeight, thumbnailHeight, file));
    }

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

