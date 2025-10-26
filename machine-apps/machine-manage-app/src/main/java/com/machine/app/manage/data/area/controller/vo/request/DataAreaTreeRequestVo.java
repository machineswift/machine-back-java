package com.machine.app.manage.data.area.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAreaTreeRequestVo {

    @NotBlank(message = "国家编码不能为空")
    @Schema(description = "国家编码，默认:CHINA")
    private String countryCode;

    public DataAreaTreeRequestVo(String countryCode) {
        this.countryCode = countryCode;
    }

}
