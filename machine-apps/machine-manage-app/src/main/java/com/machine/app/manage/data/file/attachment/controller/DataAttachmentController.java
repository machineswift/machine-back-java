package com.machine.app.manage.data.file.attachment.controller;

import com.machine.app.manage.data.file.attachment.business.IDataAttachmentBusiness;
import com.machine.app.manage.data.file.attachment.controller.vo.response.DataAttachmentUrlResponseVo;
import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.sdk.common.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "【DATA】附件模块")
@RestController
@RequestMapping("manage/data/file/attachment")
public class DataAttachmentController {

    @Autowired
    private IDataAttachmentBusiness attachmentBusiness;

    @PostMapping("upload")
    public IdResponse<String> upload(@RequestParam("entity") ModuleEntityEnum entity,
                                     @RequestParam("entityId") String entityId,
                                     @RequestParam(value = "version", defaultValue = "v1") String version,
                                     @RequestParam("file") MultipartFile file) {
        log.info("上传附件, entity:{} entityId:{} version:{} fileName:{} length:{}",
                entity, entityId, version, file.getOriginalFilename(), file.getSize());
        return new IdResponse<>(attachmentBusiness.upload(entity, entityId, version, file));
    }

    @PostMapping("upload_image")
    public IdResponse<String> uploadImage(@RequestParam("entity") ModuleEntityEnum entity,
                                          @RequestParam("entityId") String entityId,
                                          @RequestParam(value = "version", defaultValue = "v1") String version,
                                          @RequestParam(value = "thumbnailWeight", defaultValue = "320") int thumbnailWeight,
                                          @RequestParam(value = "thumbnailHeight", defaultValue = "320") int thumbnailHeight,
                                          @RequestParam("file") MultipartFile file) {
        log.info("上传图片附件, entity:{} entityId:{} version:{} thumbnailWeight:{} thumbnailHeight:{} fileName:{} length:{}",
                entity, entityId, version, thumbnailWeight, thumbnailHeight, file.getOriginalFilename(), file.getSize());
        return new IdResponse<>(attachmentBusiness.uploadImage(entity, entityId, version, thumbnailWeight, thumbnailHeight, file));
    }

    @Operation(summary = "获取附件地址")
    @GetMapping("get_url")
    public DataAttachmentUrlResponseVo getUrl(@RequestParam("attachmentId") String attachmentId,
                                              @RequestParam(value = "expireSecond", defaultValue = "7200") Integer expireSecond) {
        String url = attachmentBusiness.getPresignedUrl(attachmentId, expireSecond);
        return new DataAttachmentUrlResponseVo(url);
    }

    @Operation(summary = "获取附件缩略图地址")
    @GetMapping("get_thumbnail_url")
    public DataAttachmentUrlResponseVo getThumbnailUrl(@RequestParam("attachmentId") String attachmentId,
                                                       @RequestParam(value = "expireSecond", defaultValue = "7200") Integer expireSecond) {
        String url = attachmentBusiness.getThumbnailUrl(attachmentId, expireSecond);
        return new DataAttachmentUrlResponseVo(url);
    }
}

