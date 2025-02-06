package com.machine.sdk.common.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@Schema
@NoArgsConstructor
public class IdRequest {

    @NotBlank(message = "id 不能为空")
    @Schema(description = "ID")
    private String id;

    public IdRequest(String id) {
        this.id = id;
    }
}
