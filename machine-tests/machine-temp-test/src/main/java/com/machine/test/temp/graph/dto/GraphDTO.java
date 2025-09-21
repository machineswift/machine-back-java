package com.machine.test.temp.graph.dto;

import lombok.Data;

import java.util.List;

@Data
public class GraphDTO<T> {
    private List<T> vertices;
    private List<EdgeDTO<T>> edges;
    private int vertexCount;
    private int edgeCount;
}