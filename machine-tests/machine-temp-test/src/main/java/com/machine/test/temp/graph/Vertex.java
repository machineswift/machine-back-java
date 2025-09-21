package com.machine.test.temp.graph;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 顶点
 */
@Data
@NoArgsConstructor
public class Vertex<T> {
    private T data;
    private List<Edge<T>> edges;

    Vertex(T data) {
        this.data = data;
        this.edges = new ArrayList<>();
    }

    // 添加边
    public void addEdge(Edge<T> edge) {
        edges.add(edge);
    }

    // 移除边
    public void removeEdge(Edge<T> edge) {
        edges.remove(edge);
    }

    // 获取邻接顶点
    public List<Vertex<T>> getAdjacentVertices() {
        List<Vertex<T>> adjacent = new ArrayList<>();
        for (Edge<T> edge : edges) {
            adjacent.add(edge.getDestination());
        }
        return adjacent;
    }

    // 获取度数（出度）
    public int getDegree() {
        return edges.size();
    }

    // 自定义toString()避免循环引用
    @Override
    public String toString() {
        return "Vertex{" +
                "data=" + data +
                ", edgesCount=" + (edges != null ? edges.size() : 0) +
                '}';
    }
}
