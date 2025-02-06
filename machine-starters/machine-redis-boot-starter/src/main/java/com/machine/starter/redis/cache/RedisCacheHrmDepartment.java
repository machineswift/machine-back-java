package com.machine.starter.redis.cache;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.hrm.department.IHrmDepartmentClient;
import com.machine.client.hrm.department.dto.output.DepartmentSimpleOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentTreeOutputDto;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.starter.redis.constant.RedisPrefix4HrmConstant.Department.HRM_DEPARTMENT_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4HrmConstant.Department.HRM_DEPARTMENT_TREE_KEY;

@Slf4j
@Component
public class RedisCacheHrmDepartment {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IHrmDepartmentClient departmentClient;

    public Set<String> recursionListSubId(String departmentId) {
        //查询部门树
        DepartmentTreeOutputDto allTreeOutputDto = treeAllSimple();

        //找到指定的节点
        DepartmentTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, departmentId);
        if (null == targetNode) {
            return Set.of();
        }

        //获取节点以及子节点的所有数据
        List<DepartmentTreeOutputDto> outputDtoList = TreeUtil.collectAllNodes(targetNode);
        for (DepartmentTreeOutputDto outputDto : outputDtoList) {
            outputDto.setChildren(null);
        }
        return outputDtoList.stream().map(DepartmentTreeOutputDto::getId).collect(Collectors.toSet());
    }

    public Set<String> recursionListSubIdSet(Set<String> departmentIdSet) {
        //查询部门树
        DepartmentTreeOutputDto allTreeOutputDto = treeAllSimple();

        //找到指定的节点
        List<DepartmentTreeOutputDto> targetNodeList = new ArrayList<>();
        for (String departmentId : departmentIdSet) {
            DepartmentTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, departmentId);
            if (null != targetNode) {
                targetNodeList.add(targetNode);
            }
        }

        if (targetNodeList.isEmpty()) {
            return Set.of();
        }

        //获取节点以及子节点的所有数据
        Set<String> resultSet = new HashSet<>();
        for (DepartmentTreeOutputDto targetNode : targetNodeList) {
            TreeUtil.collectAllNodes(targetNode).stream().map(DepartmentTreeOutputDto::getId).forEach(resultSet::add);
        }
        return resultSet;
    }


    public DepartmentTreeOutputDto treeAllSimple() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(HRM_DEPARTMENT_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(HRM_DEPARTMENT_TREE_DATA + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return JSONUtil.toBean(treeJson, DepartmentTreeOutputDto.class);
            }
        }
        return departmentClient.treeAllSimple();
    }

    public Map<String, DepartmentSimpleOutputDto> mapByIdSet(IdSetRequest request) {
        //获取Tree
        DepartmentTreeOutputDto treeOutputDto = treeAllSimple();
        if (treeOutputDto == null) {
            return Map.of();
        }

        Map<String, DepartmentSimpleOutputDto> outputDtoMap = new HashMap<>();
        for (String id : request.getIdSet()) {
            DepartmentTreeOutputDto node = TreeUtil.findNode(treeOutputDto, id);
            if (null != node) {
                DepartmentSimpleOutputDto outputDto = new DepartmentSimpleOutputDto();
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
}
