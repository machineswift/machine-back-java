package com.machine.test.temp.graph;

import java.util.List;
import java.util.Set;

/**
 * 图接口
 */
public interface Graph<T> {
    void addVertex(T data);

    void removeVertex(T data);

    void addEdge(T source, T destination);

    void removeEdge(T source, T destination);

    List<Vertex<T>> getVertices();

    List<Edge<T>> getEdges();

    Vertex<T> getVertex(T data);

    boolean containsVertex(T data);

    boolean containsEdge(T source, T destination);

    Set<T> depthFirstTraversal(T root);

    Set<T> breadthFirstTraversal(T root);

    void printGraph();
}