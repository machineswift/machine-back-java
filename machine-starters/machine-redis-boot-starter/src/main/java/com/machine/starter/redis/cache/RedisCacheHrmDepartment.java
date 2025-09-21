package com.machine.starter.redis.cache;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.hrm.department.IHrmDepartmentClient;
import com.machine.client.hrm.department.dto.output.HrmDepartmentSimpleOutputDto;
import com.machine.client.hrm.department.dto.output.HrmDepartmentTreeOutputDto;
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
        HrmDepartmentTreeOutputDto allTreeOutputDto = treeAllSimple();

        //找到指定的节点
        HrmDepartmentTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, departmentId);
        if (null == targetNode) {
            return Set.of();
        }

        //获取节点以及子节点的所有数据
        List<HrmDepartmentTreeOutputDto> outputDtoList = TreeUtil.collectAllNodes(targetNode);
        return outputDtoList.stream().map(HrmDepartmentTreeOutputDto::getId).collect(Collectors.toSet());
    }

    public Set<String> recursionListSubIdSet(Set<String> departmentIdSet) {
        //查询部门树
        HrmDepartmentTreeOutputDto allTreeOutputDto = treeAllSimple();

        //找到指定的节点
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

        //获取节点以及子节点的所有数据
        Set<String> resultSet = new HashSet<>();
        for (HrmDepartmentTreeOutputDto targetNode : targetNodeList) {
            TreeUtil.collectAllNodes(targetNode).stream().map(HrmDepartmentTreeOutputDto::getId).forEach(resultSet::add);
        }
        return resultSet;
    }


    public HrmDepartmentTreeOutputDto treeAllSimple() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(HRM_DEPARTMENT_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(HRM_DEPARTMENT_TREE_DATA + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return JSONUtil.toBean(treeJson, HrmDepartmentTreeOutputDto.class);
            }
        }
        return departmentClient.treeAllSimple();
    }

    public Map<String, HrmDepartmentSimpleOutputDto> mapByIdSet(IdSetRequest request) {
        //获取Tree
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
}
