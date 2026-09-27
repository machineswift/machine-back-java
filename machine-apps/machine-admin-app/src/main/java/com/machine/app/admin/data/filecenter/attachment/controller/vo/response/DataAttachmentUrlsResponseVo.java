package com.machine.app.admin.data.filecenter.attachment.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentUrlsResponseVo {

    @Schema(description = "预签名URL")
    private List<String> urlList;


    public DataAttachmentUrlsResponseVo(List<String> urlList) {
        this.urlList = urlList;
    }
}

