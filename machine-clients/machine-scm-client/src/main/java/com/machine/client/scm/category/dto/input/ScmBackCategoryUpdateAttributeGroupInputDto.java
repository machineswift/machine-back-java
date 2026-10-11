package com.machine.client.scm.category.dto.input;

import com.machine.client.scm.category.dto.ScmBackCategoryAttributeGroupItemDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class ScmBackCategoryUpdateAttributeGroupInputDto {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "后台分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Valid
    @NotNull(message = "属性分组列表不能为空")
    @Schema(description = "属性分组列表（全量替换，传空列表表示清空关联）", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ScmBackCategoryAttributeGroupItemDto> attributeGroupItemList;
}
