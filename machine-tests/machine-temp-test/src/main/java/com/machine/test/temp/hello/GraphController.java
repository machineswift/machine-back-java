package com.machine.test.temp.hello;

import com.machine.test.temp.graph.UndirectedGraph;
import com.machine.test.temp.graph.Vertex;
import com.machine.test.temp.graph.dto.EdgeDTO;
import com.machine.test.temp.graph.dto.GraphDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("temp/graph")
public class GraphController {

    @GetMapping("test")
    public GraphDTO<String> test() {
        // 创建无向图
        UndirectedGraph<String> graph = new UndirectedGraph<>();

        int vertexCount = 20;
        int edgeCount = 20;

        // 生成顶点
        List<String> vertices = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            String vertexId = "V" + i;
            graph.addVertex(vertexId);
            vertices.add(vertexId);
        }

        // 生成边 - 确保图是连通的
        Random random = new Random(42); // 使用固定种子以便重现结果

        // 首先创建一个生成树确保连通性
        List<String> connectedVertices = new ArrayList<>();
        connectedVertices.add(vertices.get(0));

        for (int i = 1; i < vertexCount; i++) {
            String newVertex = vertices.get(i);
            String existingVertex = connectedVertices.get(random.nextInt(connectedVertices.size()));
            graph.addEdge(existingVertex, newVertex);
            connectedVertices.add(newVertex);
        }

        // 添加剩余的边
        int remainingEdges = edgeCount - (vertexCount - 1);
        for (int i = 0; i < remainingEdges; i++) {
            String vertex1, vertex2;
            do {
                vertex1 = vertices.get(random.nextInt(vertexCount));
                vertex2 = vertices.get(random.nextInt(vertexCount));
            } while (vertex1.equals(vertex2) || graph.containsEdge(vertex1, vertex2));

            graph.addEdge(vertex1, vertex2);
        }

        // 转换为 DTO
        GraphDTO<String> dto = new GraphDTO<>();
        dto.setVertices(graph.getVertices().stream()
                .map(Vertex::getData)
                .collect(Collectors.toList()));
        dto.setEdges(graph.getEdges().stream()
                .map(edge -> new EdgeDTO<>(edge.getSource().getData(), edge.getDestination().getData()))
                .collect(Collectors.toList()));
        dto.setVertexCount(graph.getVertexCount());
        dto.setEdgeCount(graph.getEdgeCount());

        log.info("生成图完成: {} 个顶点, {} 条边", dto.getVertexCount(), dto.getEdgeCount());

        return dto;
    }

    // 可选：添加一个更高效的生成方法，避免返回所有边的数据（对于大规模图）
    @GetMapping("test/lightweight")
    public Map<String, Object> testLightweight() {
        UndirectedGraph<String> graph = new UndirectedGraph<>();

        int vertexCount = 20000;
        int edgeCount = 20000;

        // 生成顶点
        List<String> vertices = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            String vertexId = "V" + i;
            graph.addVertex(vertexId);
            vertices.add(vertexId);
        }

        // 生成边
        Random random = new Random(42);

        // 确保连通性
        List<String> connectedVertices = new ArrayList<>();
        connectedVertices.add(vertices.get(0));

        for (int i = 1; i < vertexCount; i++) {
            String newVertex = vertices.get(i);
            String existingVertex = connectedVertices.get(random.nextInt(connectedVertices.size()));
            graph.addEdge(existingVertex, newVertex);
            connectedVertices.add(newVertex);
        }

        // 添加剩余边
        int remainingEdges = edgeCount - (vertexCount - 1);
        for (int i = 0; i < remainingEdges; i++) {
            String vertex1, vertex2;
            do {
                vertex1 = vertices.get(random.nextInt(vertexCount));
                vertex2 = vertices.get(random.nextInt(vertexCount));
            } while (vertex1.equals(vertex2) || graph.containsEdge(vertex1, vertex2));

            graph.addEdge(vertex1, vertex2);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("vertexCount", graph.getVertexCount());
        result.put("edgeCount", graph.getEdgeCount());
        result.put("message", "图生成完成，包含 " + vertexCount + " 个顶点和 " + edgeCount + " 条边");

        return result;
    }
}