package com.machine.client.data.filecenter.attachment.dto.input;

import com.machine.client.data.filecenter.attachment.dto.DataFileTempCreateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentVersionCreateInputDto {

    @NotBlank(message = "附件Id不能为空")
    @Schema(description = "附件Id")
    private String attachmentId;

    @Schema(description = "变更说明")
    private String changeDesc;

    @NotEmpty(message = "临时文件列表不能为空")
    @Schema(description = "临时文件列表")
    private List<DataFileTempCreateDto> fileTempList;

}
