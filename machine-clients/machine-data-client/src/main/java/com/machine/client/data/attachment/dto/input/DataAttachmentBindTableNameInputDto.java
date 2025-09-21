package com.machine.client.data.attachment.dto.input;

import com.machine.sdk.common.envm.system.SystemTableNameEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentBindTableNameInputDto {

    @NotBlank(message = "id不能为空")
    @Schema(description = "Id")
    private String id;

    @NotNull(message = "表名不能为空")
    @Schema(description = "表名")
    private SystemTableNameEnum tableName;

    @NotBlank(message = "数据Id不能为空")
    @Schema(description = "数据Id")
    private String dataId;
}


