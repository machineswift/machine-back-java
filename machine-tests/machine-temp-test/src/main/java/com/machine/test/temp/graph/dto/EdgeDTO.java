package com.machine.test.temp.graph.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EdgeDTO<T> {
    private T source;
    private T destination;

    public EdgeDTO(T source, T destination) {
        this.source = source;
        this.destination = destination;
    }
}