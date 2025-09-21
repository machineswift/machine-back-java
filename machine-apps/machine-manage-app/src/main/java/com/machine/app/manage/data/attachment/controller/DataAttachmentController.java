package com.machine.app.manage.data.attachment.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.attachment.business.IDataAttachmentBusiness;
import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentDetailResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentExpandListResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentUrlResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentQueryPageRequestVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentUpdateRequestVo;
import com.machine.sdk.common.envm.data.attachment.DataAttachmentTypeEnum;
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
@Tag(name = "【DATA】附件模块")
@RestController
@RequestMapping("manage/data/attachment")
public class DataAttachmentController {

    @Autowired
    private IDataAttachmentBusiness attachmentBusiness;

    @PostMapping("upload")
    public IdResponse<String> upload(@RequestParam("attachmentType") DataAttachmentTypeEnum attachmentType,
                                     @RequestParam("file") MultipartFile file) {
        log.info("上传附件, attachmentType:{} fileName:{} length:{}",
                attachmentType, file.getOriginalFilename(), file.getSize());
        return new IdResponse<>(attachmentBusiness.upload(attachmentType, file));
    }

    @PostMapping("upload_image")
    public IdResponse<String> uploadImage(@RequestParam("thumbnailWeight") int thumbnailWeight,
                                          @RequestParam("thumbnailHeight") int thumbnailHeight,
                                          @RequestParam("file") MultipartFile file) {
        log.info("上传图片附件, thumbnailWeight:{} thumbnailHeight:{} fileName:{} length:{}",
                thumbnailWeight, thumbnailHeight, file.getOriginalFilename(), file.getSize());
        return new IdResponse<>(attachmentBusiness.uploadImage(thumbnailWeight, thumbnailHeight, file));
    }

    @PermitAll
    @Operation(summary = "获取附件地址")
    @GetMapping("get_url")
    public DataAttachmentUrlResponseVo getUrl(@RequestParam("attachmentId") String attachmentId,
                                              @RequestParam(value = "expireSecond", required = false) Integer expireSecond) {
        String url;
        if (null != expireSecond && expireSecond > 0) {
            url = attachmentBusiness.getPresignedUrl(attachmentId, expireSecond);
        } else {
            url = attachmentBusiness.getPresignedUrl(attachmentId);
        }
        return new DataAttachmentUrlResponseVo(url);
    }

    @PermitAll
    @Operation(summary = "获取附件缩略图地址")
    @GetMapping("get_thumbnail_url")
    public DataAttachmentUrlResponseVo getThumbnailUrl(@RequestParam("attachmentId") String attachmentId,
                                                       @RequestParam(value = "expireSecond", required = false) Integer expireSecond) {
        String url;
        if (null != expireSecond && expireSecond > 0) {
            url = attachmentBusiness.getThumbnailUrl(attachmentId, expireSecond);
        } else {
            url = attachmentBusiness.getThumbnailUrl(attachmentId);
        }
        return new DataAttachmentUrlResponseVo(url);
    }

    @Operation(summary = "修改附件")
    @PostMapping("update")
    public void update(@RequestBody @Validated DataAttachmentUpdateRequestVo request) {
        log.info("修改附件，request={}", JSONUtil.toJsonStr(request));
        attachmentBusiness.update(request);
    }

    @Operation(summary = "附件详情")
    @PostMapping("detail")
    public DataAttachmentDetailResponseVo detail(@RequestBody IdRequest request) {
        return attachmentBusiness.detail(request);
    }

    @Operation(summary = "分页查询附件(应用于角色管理菜单)")
    @PostMapping("page_expand")
    public PageResponse<DataAttachmentExpandListResponseVo> pageExpand(@RequestBody @Validated DataAttachmentQueryPageRequestVo request) {
        return attachmentBusiness.pageExpand(request);
    }
}

