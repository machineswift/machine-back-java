package com.machine.client.data.brand.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataBrandUpdateLogoAttachmentIdInputDto {

    @NotBlank(message = "品牌ID不能为空")
    @Schema(description = "品牌ID")
    private String id;

    @NotBlank(message = "LOGO附件ID不能为空")
    @Schema(description = "LOGO附件ID")
    private String logoAttachmentId;

    public DataBrandUpdateLogoAttachmentIdInputDto(String id,
                                                   String logoAttachmentId) {
        this.id = id;
        this.logoAttachmentId = logoAttachmentId;
    }
}
