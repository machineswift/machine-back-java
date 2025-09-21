package com.machine.client.data.brand.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataBrandUpdateStatusInputDto {

    @NotBlank(message = "id不能为空")
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态（StatusEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private StatusEnum status;

}
