package com.machine.app.admin.data.area.controller.vo.request;

import com.machine.sdk.base.envm.data.DataCountryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAreaTreeRequestVo {

    @NotNull(message = "国家不能为空")
    @Schema(description = "国家(DataCountryEnum)，默认:CHINA")
    private DataCountryEnum country;

    public DataAreaTreeRequestVo(DataCountryEnum country) {
        this.country = country;
    }

}
