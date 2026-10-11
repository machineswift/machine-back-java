package com.machine.app.admin.data.brand.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class DataBrandUpdateCategoryRequestVo {

    @NotBlank(message = "品牌ID不能为空")
    @Schema(description = "品牌ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "所属分类ID集合，可多选")
    private Set<String> categoryIdSet;

}
