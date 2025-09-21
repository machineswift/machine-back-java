package com.machine.starter.redis.cache;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.organization.IIamOrganizationClient;
import com.machine.client.iam.organization.dto.output.*;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.SEPARATOR_COLON;
import static com.machine.sdk.common.constant.CommonIamConstant.Organization.ORGANIZATION_VIRTUAL_NODE;
import static com.machine.sdk.common.constant.CommonIamConstant.Organization.ORGANIZATION_VIRTUAL_NODE_NAME;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Organization.DATA_ORGANIZATION_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Organization.DATA_ORGANIZATION_TREE_KEY;

@Slf4j
@Component
public class RedisCacheIamOrganization {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IIamOrganizationClient organizationClient;

    public Set<String> recursionListSubId(IamOrganizationTypeEnum type,
                                          String organizationId) {
        //查询组织树
        IamOrganizationTreeSimpleOutputDto treeOutputDto = treeAllSimple(type);

        //找到指定的节点
        IamOrganizationTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, organizationId);
        if (null == treeNode) {
            return Set.of();
        }

        //获取节点以及子节点的所有数据
        List<IamOrganizationTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeNode);
        return outputDtoList.stream().map(IamOrganizationTreeSimpleOutputDto::getId).collect(Collectors.toSet());
    }

    public Set<String> recursionListSubIds(IamOrganizationTypeEnum type,
                                           Set<String> organizationIdSet) {

        List<IamOrganizationTreeSimpleOutputDto> treeOutputDtoList = new ArrayList<>();
        for (String id : organizationIdSet) {
            IamOrganizationTreeSimpleOutputDto node = TreeUtil.findNode(treeAllSimple(type), id);
            if (null != node) {
                treeOutputDtoList.add(node);
            }
        }
        //循环递归获取所有节点
        Set<String> idSet = new HashSet<>();
        for (IamOrganizationTreeSimpleOutputDto treeOutputDto : treeOutputDtoList) {
            List<IamOrganizationTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeOutputDto);
            idSet.addAll(outputDtoList.stream().map(IamOrganizationTreeSimpleOutputDto::getId).toList());
        }
        return idSet;
    }

    public Set<String> recursionListSubIds(Set<String> organizationIdSet) {
        //获取所有节点
        List<IamOrganizationTreeSimpleOutputDto> treeOutputDtoList = new ArrayList<>();
        for (IamOrganizationTypeEnum type : IamOrganizationTypeEnum.values()) {
            //获取Tree
            IamOrganizationTreeSimpleOutputDto treeOutputDto = treeAllSimple(type);
            for (String id : organizationIdSet) {
                IamOrganizationTreeSimpleOutputDto node = TreeUtil.findNode(treeOutputDto, id);
                if (null != node) {
                    treeOutputDtoList.add(node);
                }
            }

            if (treeOutputDtoList.size() == organizationIdSet.size()) {
                break;
            }
        }

        //循环递归获取所有节点
        Set<String> idSet = new HashSet<>();
        for (IamOrganizationTreeSimpleOutputDto treeOutputDto : treeOutputDtoList) {
            List<IamOrganizationTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeOutputDto);
            idSet.addAll(outputDtoList.stream().map(IamOrganizationTreeSimpleOutputDto::getId).toList());
        }
        return idSet;
    }


    public IamOrganizationTreeSimpleOutputDto treeAllSimple(IamOrganizationTypeEnum type) {
        String typeName = type.getName();
        //获取树的动态key
        String keyCode = customerRedisCommands.get(DATA_ORGANIZATION_TREE_KEY + typeName);

        IamOrganizationTreeSimpleOutputDto treeSimpleOutputDto = null;
        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_ORGANIZATION_TREE_DATA + typeName + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                treeSimpleOutputDto = JSONUtil.toBean(treeJson, IamOrganizationTreeSimpleOutputDto.class);
            }
        }

        //查询树
        if (null == treeSimpleOutputDto) {
            treeSimpleOutputDto = organizationClient.treeAllSimple(type);
        }

        { //添加【未分配】
            IamOrganizationTreeSimpleOutputDto virtualSimpleTreeBo = new IamOrganizationTreeSimpleOutputDto();
            virtualSimpleTreeBo.setId(typeName + SEPARATOR_COLON + ORGANIZATION_VIRTUAL_NODE);
            virtualSimpleTreeBo.setParentId(treeSimpleOutputDto.getId());
            virtualSimpleTreeBo.setName(ORGANIZATION_VIRTUAL_NODE_NAME);
            virtualSimpleTreeBo.setSort(Long.MAX_VALUE);
            virtualSimpleTreeBo.setCode(typeName + SEPARATOR_COLON + ORGANIZATION_VIRTUAL_NODE.toUpperCase());
            virtualSimpleTreeBo.setType(type);
            if (CollectionUtil.isEmpty(treeSimpleOutputDto.getChildren())) {
                treeSimpleOutputDto.setChildren(List.of(virtualSimpleTreeBo));
            } else {
                treeSimpleOutputDto.getChildren().addFirst(virtualSimpleTreeBo);
            }
        }

        return treeSimpleOutputDto;
    }

    public Map<String, IamOrganizationSimpleOutputDto> mapByIdSet(Set<String> organizationIdSet) {
        Map<String, IamOrganizationSimpleOutputDto> outputDtoMap = new HashMap<>();
        for (IamOrganizationTypeEnum type : IamOrganizationTypeEnum.values()) {
            //获取Tree
            IamOrganizationTreeSimpleOutputDto treeOutputDto = treeAllSimple(type);
            for (String id : organizationIdSet) {
                IamOrganizationTreeSimpleOutputDto node = TreeUtil.findNode(treeOutputDto, id);
                if (null != node) {
                    IamOrganizationSimpleOutputDto outputDto = new IamOrganizationSimpleOutputDto();
                    outputDto.setId(node.getId());
                    outputDto.setParentId(node.getParentId());
                    outputDto.setCode(node.getCode());
                    outputDto.setName(node.getName());
                    outputDto.setType(node.getType());
                    outputDto.setSort(node.getSort());
                    outputDtoMap.put(id, outputDto);
                }
            }

            if (outputDtoMap.size() == organizationIdSet.size()) {
                break;
            }
        }
        return outputDtoMap;
    }

}
