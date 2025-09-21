package com.machine.app.manage.data.material.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialUrlResponseVo {

    @Schema(description = "预签名URL")
    private String url;

    public DataMaterialUrlResponseVo(String url) {
        this.url = url;
    }
}


