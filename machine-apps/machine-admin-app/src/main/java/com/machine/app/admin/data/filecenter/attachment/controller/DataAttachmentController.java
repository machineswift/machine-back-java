package com.machine.app.admin.data.filecenter.attachment.controller;

import com.machine.app.admin.data.filecenter.attachment.business.IDataAttachmentBusiness;
import com.machine.app.admin.data.filecenter.attachment.controller.vo.response.DataAttachmentUrlResponseVo;
import com.machine.sdk.base.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "【DATA】附件模块")
@RestController
@RequestMapping("admin/data/file_center/attachment")
public class DataAttachmentController {

    @Autowired
    private IDataAttachmentBusiness attachmentBusiness;

    @PostMapping("upload")
    public IdResponse<String> upload(@RequestParam("file") MultipartFile file) {
        log.info("上传附件,  fileName:{} length:{}", file.getOriginalFilename(), file.getSize());
        return new IdResponse<>(attachmentBusiness.uploadTemp(file));
    }


    @Operation(summary = "获取附件缩略图地址")
    @GetMapping("get_thumbnail_url")
    public DataAttachmentUrlResponseVo getThumbnailUrl(@RequestParam("attachmentId") String attachmentId,
                                                       @RequestParam(value = "expireSecond", defaultValue = "7200") Integer expireSecond) {
        String url = attachmentBusiness.getThumbnailUrl(attachmentId, expireSecond);
        return new DataAttachmentUrlResponseVo(url);
    }

}

