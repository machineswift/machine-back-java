package com.machine.sdk.common.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Schema
@NoArgsConstructor
public class MapResponse<K,V> {

    @Schema(description = "MAP")
    public Map<K,V> map;


    public MapResponse(Map<K,V> map) {
        this.map = map;
    }
}
