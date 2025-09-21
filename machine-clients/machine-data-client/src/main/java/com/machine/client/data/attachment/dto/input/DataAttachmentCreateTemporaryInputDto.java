package com.machine.client.data.attachment.dto.input;

import com.machine.sdk.common.envm.data.attachment.DataAttachmentTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentCreateTemporaryInputDto {

    @NotNull(message = "附件类型不能为空")
    @Schema(description = "附件类型（DataAttachmentTypeEnum）")
    private DataAttachmentTypeEnum type;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @NotBlank(message = "obs文件信息不能为空")
    @Schema(description = "obs文件信息")
    private String fileInfo;

    @Schema(description = "大小（字节）")
    private Long size;
}


