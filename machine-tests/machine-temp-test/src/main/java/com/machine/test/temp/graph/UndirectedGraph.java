package com.machine.test.temp.graph;

import java.util.*;

/**
 * 无向图实现
 */
public class UndirectedGraph<T> implements Graph<T> {
    private Map<T, Vertex<T>> vertices;
    private List<Edge<T>> edges;

    public UndirectedGraph() {
        this.vertices = new HashMap<>();
        this.edges = new ArrayList<>();
    }

    @Override
    public void addVertex(T data) {
        vertices.putIfAbsent(data, new Vertex<>(data));
    }

    @Override
    public void removeVertex(T data) {
        Vertex<T> vertexToRemove = vertices.get(data);
        if (vertexToRemove != null) {
            // 移除所有相关的边
            List<Edge<T>> edgesToRemove = new ArrayList<>();
            for (Edge<T> edge : edges) {
                if (edge.getSource().equals(vertexToRemove) ||
                        edge.getDestination().equals(vertexToRemove)) {
                    edgesToRemove.add(edge);
                }
            }
            edges.removeAll(edgesToRemove);

            // 从其他顶点的边列表中移除相关边
            for (Vertex<T> vertex : vertices.values()) {
                vertex.getEdges().removeIf(edge ->
                        edge.getSource().equals(vertexToRemove) ||
                                edge.getDestination().equals(vertexToRemove));
            }

            vertices.remove(data);
        }
    }


    @Override
    public void addEdge(T source, T destination) {
        addVertex(source);
        addVertex(destination);

        Vertex<T> sourceVertex = vertices.get(source);
        Vertex<T> destVertex = vertices.get(destination);

        // 检查边是否已存在
        if (containsEdge(source, destination)) {
            return;
        }

        // 创建双向边（无向图）
        Edge<T> edge1 = new Edge<>(sourceVertex, destVertex);
        Edge<T> edge2 = new Edge<>(destVertex, sourceVertex);

        sourceVertex.addEdge(edge1);
        destVertex.addEdge(edge2);
        edges.add(edge1);  // 修复：添加第一条边
        edges.add(edge2);  // 修复：添加第二条边（之前漏掉了这一行）
    }


    @Override
    public void removeEdge(T source, T destination) {
        Vertex<T> sourceVertex = vertices.get(source);
        Vertex<T> destVertex = vertices.get(destination);

        if (sourceVertex != null && destVertex != null) {
            // 找到要移除的边对象（而不是创建新对象）
            List<Edge<T>> edgesToRemove = new ArrayList<>();

            for (Edge<T> edge : edges) {
                if ((edge.getSource().equals(sourceVertex) && edge.getDestination().equals(destVertex)) ||
                        (edge.getSource().equals(destVertex) && edge.getDestination().equals(sourceVertex))) {
                    edgesToRemove.add(edge);
                }
            }

            // 从顶点和边列表中移除
            for (Edge<T> edge : edgesToRemove) {
                edge.getSource().removeEdge(edge);
                edge.getDestination().removeEdge(edge);
                edges.remove(edge);
            }
        }
    }

    @Override
    public List<Vertex<T>> getVertices() {
        return new ArrayList<>(vertices.values());
    }

    @Override
    public List<Edge<T>> getEdges() {
        return new ArrayList<>(edges);
    }

    @Override
    public Vertex<T> getVertex(T data) {
        return vertices.get(data);
    }

    @Override
    public boolean containsVertex(T data) {
        return vertices.containsKey(data);
    }

    @Override
    public boolean containsEdge(T source, T destination) {
        Vertex<T> sourceVertex = vertices.get(source);
        Vertex<T> destVertex = vertices.get(destination);

        if (sourceVertex == null || destVertex == null) {
            return false;
        }

        // 检查两个方向的边
        for (Edge<T> edge : edges) {
            if ((edge.getSource().equals(sourceVertex) && edge.getDestination().equals(destVertex)) ||
                    (edge.getSource().equals(destVertex) && edge.getDestination().equals(sourceVertex))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<T> depthFirstTraversal(T root) {
        Set<T> visited = new LinkedHashSet<>();
        Stack<Vertex<T>> stack = new Stack<>();

        Vertex<T> rootVertex = vertices.get(root);
        if (rootVertex != null) {
            stack.push(rootVertex);

            while (!stack.isEmpty()) {
                Vertex<T> vertex = stack.pop();
                if (!visited.contains(vertex.getData())) {
                    visited.add(vertex.getData());
                    for (Edge<T> edge : vertex.getEdges()) {
                        stack.push(edge.getDestination());
                    }
                }
            }
        }
        return visited;
    }

    @Override
    public Set<T> breadthFirstTraversal(T root) {
        Set<T> visited = new LinkedHashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();

        Vertex<T> rootVertex = vertices.get(root);
        if (rootVertex != null) {
            queue.add(rootVertex);
            visited.add(rootVertex.getData());

            while (!queue.isEmpty()) {
                Vertex<T> vertex = queue.poll();
                for (Edge<T> edge : vertex.getEdges()) {
                    Vertex<T> neighbor = edge.getDestination();
                    if (!visited.contains(neighbor.getData())) {
                        visited.add(neighbor.getData());
                        queue.add(neighbor);
                    }
                }
            }
        }
        return visited;
    }

    @Override
    public void printGraph() {
        System.out.println("图的邻接表表示:");
        for (Vertex<T> vertex : vertices.values()) {
            System.out.print(vertex + " -> ");
            for (Edge<T> edge : vertex.getEdges()) {
                System.out.print(edge.getDestination());
            }
            System.out.println();
        }
    }

    // 获取顶点数量
    public int getVertexCount() {
        return vertices.size();
    }

    // 获取边数量
    public int getEdgeCount() {
        return edges.size() / 2;
    }
}