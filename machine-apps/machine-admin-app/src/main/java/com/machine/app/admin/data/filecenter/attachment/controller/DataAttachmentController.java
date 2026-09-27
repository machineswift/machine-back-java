package com.machine.app.admin.data.filecenter.attachment.controller;

import com.machine.app.admin.data.filecenter.attachment.business.IDataAttachmentBusiness;
import com.machine.app.admin.data.filecenter.attachment.controller.vo.response.DataAttachmentUrlResponseVo;
import com.machine.app.admin.data.filecenter.attachment.controller.vo.response.DataAttachmentUrlsResponseVo;
import com.machine.sdk.base.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Tag(name = "【DATA】附件模块")
@RestController
@RequestMapping("admin/data/file_center/attachment")
public class DataAttachmentController {

    @Autowired
    private IDataAttachmentBusiness attachmentBusiness;

    @Operation(summary = "上传")
    @PostMapping("upload")
    public IdResponse<String> upload(@RequestParam("file") MultipartFile file) {
        log.info("上传附件,  fileName:{} length:{}", file.getOriginalFilename(), file.getSize());
        return new IdResponse<>(attachmentBusiness.uploadTemp(file));
    }

    @Operation(summary = "缩略图")
    @GetMapping("thumbnail")
    public DataAttachmentUrlResponseVo thumbnail(@RequestParam("attachmentId") String attachmentId) {
        String url = attachmentBusiness.thumbnail(attachmentId);
        return new DataAttachmentUrlResponseVo(url);
    }

    @Operation(summary = "缩略图(批量)")
    @GetMapping("batch_thumbnail")
    public DataAttachmentUrlsResponseVo batchThumbnail(@RequestParam("attachmentId") String attachmentId) {
        List<String> urlList = attachmentBusiness.batchThumbnail(attachmentId);
        return new DataAttachmentUrlsResponseVo(urlList);
    }

    @Operation(summary = "预览")
    @GetMapping("preview")
    public DataAttachmentUrlResponseVo preview(@RequestParam("attachmentId") String attachmentId)  {
        String url = attachmentBusiness.preview(attachmentId);
        return new DataAttachmentUrlResponseVo(url);
    }

    @Operation(summary = "预览(批量)")
    @GetMapping("batch_preview")
    public DataAttachmentUrlsResponseVo batchPreview(@RequestParam("attachmentId") String attachmentId) {
        List<String> urlList = attachmentBusiness.batchPreview(attachmentId);
        return new DataAttachmentUrlsResponseVo(urlList);
    }

    @Operation(summary = "下载")
    @GetMapping("download")
    public DataAttachmentUrlResponseVo download(@RequestParam("attachmentId") String attachmentId) {
        String url = attachmentBusiness.download(attachmentId);
        return new DataAttachmentUrlResponseVo(url);
    }

    @Operation(summary = "下载(批量)")
    @GetMapping("batch_download")
    public DataAttachmentUrlsResponseVo batchDownload(@RequestParam("attachmentId") String attachmentId) {
        List<String> urlList = attachmentBusiness.batchDownload(attachmentId);
        return new DataAttachmentUrlsResponseVo(urlList);
    }
}

