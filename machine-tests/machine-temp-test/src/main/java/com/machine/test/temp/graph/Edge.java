package com.machine.test.temp.graph;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 边
 */
@Data
@NoArgsConstructor
public class Edge<T> {
    private Vertex<T> source;
    private Vertex<T> destination;

    Edge(Vertex<T> source, Vertex<T> destination) {
        this.source = source;
        this.destination = destination;
    }

    // 添加equals和hashCode方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;
        return Objects.equals(source, edge.source) &&
                Objects.equals(destination, edge.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }

    // 自定义toString()避免循环引用
    @Override
    public String toString() {
        return "Edge{" +
                "source=" + (source != null ? source.getData() : null) +
                ", destination=" + (destination != null ? destination.getData() : null) +
                '}';
    }

}