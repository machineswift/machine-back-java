package com.machine.sdk.common.model.dto.data;

import com.machine.sdk.common.envm.data.material.DataMaterIalTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Schema
@Validated
public class MaterialDto {

    @NotNull(message = "素材类型不能为空")
    @Schema(description = "素材类型（MaterIalTypeEnum）")
    private DataMaterIalTypeEnum type;

    @Schema(description = "大小（字节）")
    private Long size;

    @Schema(description = "名称")
    private String name;

    @NotNull(message = "url地址不能为空")
    @Schema(description = "url地址")
    private String url;
}
