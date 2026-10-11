package com.machine.app.admin.scm.attribute.controller.vo.request;

import com.machine.client.scm.attribute.dto.ScmAttributeGroupAttributeItemDto;
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
public class ScmAttributeGroupUpdateAttributeRequestVo {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "属性分组ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Valid
    @NotNull(message = "属性列表不能为空")
    @Schema(description = "属性列表（全量替换，传空列表表示清空关联）", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ScmAttributeGroupAttributeItemDto> attributeItemList;
}
