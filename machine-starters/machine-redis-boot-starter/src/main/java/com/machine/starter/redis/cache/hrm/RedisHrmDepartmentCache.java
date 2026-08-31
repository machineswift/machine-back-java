package com.machine.starter.redis.cache.hrm;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.hrm.department.IHrmDepartmentClient;
import com.machine.client.hrm.department.dto.output.HrmDepartmentSimpleOutputDto;
import com.machine.client.hrm.department.dto.output.HrmDepartmentTreeOutputDto;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.machine.starter.redis.constant.RedisPrefix4HrmConstant.Department.HRM_DEPARTMENT_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4HrmConstant.Department.HRM_DEPARTMENT_TREE_KEY;

@Slf4j
@Component
public class RedisHrmDepartmentCache {

    /**
     * 部门的二级缓存（版本号 + 本地树数据）
     */
    private final DepartmentTreeCache departmentCache = new DepartmentTreeCache();

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IHrmDepartmentClient departmentClient;

    public Set<String> recursionListSubId(String departmentId) {
        // 查询部门树
        HrmDepartmentTreeOutputDto allTreeOutputDto = treeAllSimple();

        // 找到指定的节点
        HrmDepartmentTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, departmentId);
        if (null == targetNode) {
            return Set.of();
        }

        // 获取节点以及子节点的所有数据
        List<HrmDepartmentTreeOutputDto> outputDtoList = TreeUtil.collectAllNodes(targetNode);
        return outputDtoList.stream().map(HrmDepartmentTreeOutputDto::getId).collect(Collectors.toSet());
    }

    public Set<String> recursionListSubIdSet(Set<String> departmentIdSet) {
        // 查询部门树
        HrmDepartmentTreeOutputDto allTreeOutputDto = treeAllSimple();

        // 找到指定的节点
        List<HrmDepartmentTreeOutputDto> targetNodeList = new ArrayList<>();
        for (String departmentId : departmentIdSet) {
            HrmDepartmentTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, departmentId);
            if (null != targetNode) {
                targetNodeList.add(targetNode);
            }
        }

        if (targetNodeList.isEmpty()) {
            return Set.of();
        }

        // 获取节点以及子节点的所有数据
        Set<String> resultSet = new HashSet<>();
        for (HrmDepartmentTreeOutputDto targetNode : targetNodeList) {
            TreeUtil.collectAllNodes(targetNode).stream().map(HrmDepartmentTreeOutputDto::getId)
                    .forEach(resultSet::add);
        }
        return resultSet;
    }

    public HrmDepartmentTreeOutputDto treeAllSimple() {
        String keyCode = customerRedisCommands.get(HRM_DEPARTMENT_TREE_KEY);

        // 二级缓存命中
        if (StrUtil.isNotEmpty(keyCode) &&
                keyCode.equals(departmentCache.version.get()) &&
                departmentCache.cachedData != null) {
            return copyTree(departmentCache.cachedData);
        }

        synchronized (departmentCache) {
            // Double-Check：防止并发情况下重复加载
            String recheckKeyCode = customerRedisCommands.get(HRM_DEPARTMENT_TREE_KEY);
            if (StrUtil.isNotEmpty(recheckKeyCode) &&
                    recheckKeyCode.equals(departmentCache.version.get()) &&
                    departmentCache.cachedData != null) {
                return copyTree(departmentCache.cachedData);
            }

            HrmDepartmentTreeOutputDto treeOutputDto = null;

            // Redis加载数据（一级缓存）
            if (StrUtil.isNotEmpty(recheckKeyCode)) {
                String treeJson = customerRedisCommands.get(HRM_DEPARTMENT_TREE_DATA + recheckKeyCode);
                if (StrUtil.isNotEmpty(treeJson)) {
                    treeOutputDto = JSONUtil.toBean(treeJson, HrmDepartmentTreeOutputDto.class);
                }
            }

            // Redis无数据，从远程加载
            if (null == treeOutputDto) {
                Tuples.Tuple2<String, HrmDepartmentTreeOutputDto> tuple2 = departmentClient.treeAllSimple();
                recheckKeyCode = tuple2._1();
                treeOutputDto = tuple2._2();
            }

            departmentCache.cachedData = treeOutputDto;
            departmentCache.version.set(recheckKeyCode);
            return copyTree(departmentCache.cachedData);
        }
    }

    /**
     * 递归深拷贝部门树，防止调用方修改缓存数据影响业务
     */
    private HrmDepartmentTreeOutputDto copyTree(HrmDepartmentTreeOutputDto source) {
        if (null == source) {
            return null;
        }
        HrmDepartmentTreeOutputDto target = new HrmDepartmentTreeOutputDto();
        target.setId(source.getId());
        target.setParentId(source.getParentId());
        target.setName(source.getName());
        target.setSort(source.getSort());
        target.setCode(source.getCode());

        if (CollectionUtil.isNotEmpty(source.getChildren())) {
            List<HrmDepartmentTreeOutputDto> children = new ArrayList<>(source.getChildren().size());
            for (HrmDepartmentTreeOutputDto child : source.getChildren()) {
                children.add(copyTree(child));
            }
            target.setChildren(children);
        } else {
            target.setChildren(List.of());
        }
        return target;
    }

    public Map<String, HrmDepartmentSimpleOutputDto> mapByIdSet(IdSetRequest request) {
        // 获取Tree
        HrmDepartmentTreeOutputDto treeOutputDto = treeAllSimple();
        if (treeOutputDto == null) {
            return Map.of();
        }

        Map<String, HrmDepartmentSimpleOutputDto> outputDtoMap = new HashMap<>();
        for (String id : request.getIdSet()) {
            HrmDepartmentTreeOutputDto node = TreeUtil.findNode(treeOutputDto, id);
            if (null != node) {
                HrmDepartmentSimpleOutputDto outputDto = new HrmDepartmentSimpleOutputDto();
                outputDto.setId(node.getId());
                outputDto.setParentId(node.getParentId());
                outputDto.setCode(node.getCode());
                outputDto.setName(node.getName());
                outputDto.setSort(node.getSort());
                outputDtoMap.put(id, outputDto);
            }
        }
        return outputDtoMap;
    }

    private static class DepartmentTreeCache {
        private final AtomicReference<String> version = new AtomicReference<>();
        private volatile HrmDepartmentTreeOutputDto cachedData;
    }

}
