package com.machine.client.data.material.dto.input;

import com.machine.sdk.common.envm.data.material.DataMaterIalTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialCreateTemporaryInputDto {

    @Schema(description = "素材类型（DataMaterIalTypeEnum）")
    private DataMaterIalTypeEnum type;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "obs文件信息")
    private String fileInfo;

    @Schema(description = "大小（字节）")
    private Long size;
}


