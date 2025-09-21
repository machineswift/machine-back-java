package com.machine.sdk.common.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IdResponse<T> {

    @Schema(description = "ID")
    public T id;

    public IdResponse(T id) {
        this.id = id;
    }
}
