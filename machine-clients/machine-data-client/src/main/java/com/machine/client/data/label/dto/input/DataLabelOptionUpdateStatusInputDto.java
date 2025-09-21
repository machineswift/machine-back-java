package com.machine.client.data.label.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataLabelOptionUpdateStatusInputDto {

    @NotBlank(message = "Id不能为空")
    private String id;

    @NotNull(message = "状态不能为空")
    private StatusEnum status;
}
