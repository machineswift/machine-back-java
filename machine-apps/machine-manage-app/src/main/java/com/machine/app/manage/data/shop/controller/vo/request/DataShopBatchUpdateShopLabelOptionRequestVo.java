package com.machine.app.manage.data.shop.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataShopBatchUpdateShopLabelOptionRequestVo {

    @NotBlank(message = "标签选项ID不能为空")
    @Schema(description = "标签选项ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String labelOptionIdId;

    @NotNull(message = "门店ID不能为空")
    @Schema(description = "门店ID集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<String> shopIdSet;

}
