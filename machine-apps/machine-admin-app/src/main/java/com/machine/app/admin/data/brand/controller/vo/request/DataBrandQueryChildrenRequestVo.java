package com.machine.app.admin.data.brand.controller.vo.request;

import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataBrandQueryChildrenRequestVo extends PageRequest {

    @NotBlank(message = "父品牌ID不能为空")
    @Schema(description = "父品牌ID")
    private String parentId;
}


