package com.machine.client.ai.resource.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class AiResourceModelListInputDto {

    @Schema(description = "厂商ID")
    private String providerId;
}
