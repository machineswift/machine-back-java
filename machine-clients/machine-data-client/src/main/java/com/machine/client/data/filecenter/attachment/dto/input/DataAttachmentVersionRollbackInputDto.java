package com.machine.client.data.filecenter.attachment.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentVersionRollbackInputDto {

    @NotBlank(message = "附件Id不能为空")
    @Schema(description = "附件Id")
    private String attachmentId;

    @NotNull(message = "目标版本号不能为空")
    @Schema(description = "目标版本号")
    private Integer targetVersionNo;
}
