package com.machine.client.data.filecenter.attachment.dto.input;

import com.machine.client.data.filecenter.attachment.dto.DataFileTempCreateDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentVersionUpdateInputDto {

    @NotNull(message = "实体不能为空")
    @Schema(description = "实体（ModuleEntityEnum）")
    private ModuleEntityEnum entity;

    @NotBlank(message = "实体Id不能为空")
    @Schema(description = "实体Id")
    private String entityId;

    @NotBlank(message = "附件分组不能为空")
    @Schema(description = "附件分组")
    private String attachmentGroup;

    @Schema(description = "变更说明")
    private String changeDesc;

    @NotEmpty(message = "临时文件列表不能为空")
    @Schema(description = "临时文件列表")
    private List<DataFileTempCreateDto> fileTempList;
}
