package com.machine.sdk.common.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IdSetRequest {

    @NotEmpty(message = "ID集合不能为空")
    @Schema(description = "ID集合")
    private Set<String> idSet;

    public IdSetRequest(Set<String> idSet) {
        this.idSet = idSet;
    }
}
