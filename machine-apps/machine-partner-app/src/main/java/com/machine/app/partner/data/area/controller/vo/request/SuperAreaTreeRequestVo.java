package com.machine.app.partner.data.area.controller.vo.request;

import com.machine.sdk.base.envm.data.DataCountryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema
public class SuperAreaTreeRequestVo {

    @NotNull(message = "国家不能为空")
    @Schema(description = "国家(DataCountryEnum)，默认:CHINA")
    private DataCountryEnum country;

    public SuperAreaTreeRequestVo(DataCountryEnum country) {
        this.country = country;
    }
}
