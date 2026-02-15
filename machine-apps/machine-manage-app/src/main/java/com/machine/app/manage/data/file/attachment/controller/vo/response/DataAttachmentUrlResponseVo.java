package com.machine.app.manage.data.file.attachment.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentUrlResponseVo {

    @Schema(description = "预签名URL")
    private String url;


    public DataAttachmentUrlResponseVo(String url) {
        this.url = url;
    }
}


