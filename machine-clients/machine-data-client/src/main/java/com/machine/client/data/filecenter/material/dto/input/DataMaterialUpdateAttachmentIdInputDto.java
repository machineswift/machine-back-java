package com.machine.client.data.filecenter.material.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialUpdateAttachmentIdInputDto {

    @NotBlank(message = "素材ID不能为空")
    @Schema(description = "素材ID")
    private String id;

    @NotBlank(message = "附件ID不能为空")
    @Schema(description = "附件ID")
    private String attachmentId;

    public DataMaterialUpdateAttachmentIdInputDto(String id,
                                                  String attachmentId) {
        this.id = id;
        this.attachmentId = attachmentId;
    }
}
