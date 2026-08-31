package com.machine.starter.redis.cache.biam;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.organization.IBIamOrganizationClient;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationSimpleOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonConstant.SEPARATOR_COLON;
import static com.machine.sdk.base.constant.CommonBIamConstant.Organization.DATA_ORGANIZATION_VIRTUAL_NODE;
import static com.machine.sdk.base.constant.CommonBIamConstant.Organization.DATA_ORGANIZATION_VIRTUAL_NODE_NAME;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Organization.BIAM_ORGANIZATION_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Organization.BIAM_ORGANIZATION_TREE_KEY;

@Slf4j
@Component
public class RedisBIamOrganizationCache {

    /**
     * 各组织类型的二级缓存（版本号 + 本地树数据）
     */
    private final Map<BIamOrganizationTypeEnum, TypeTreeCache> typeTreeCacheMap = new EnumMap<>(
            BIamOrganizationTypeEnum.class);

    {
        for (BIamOrganizationTypeEnum type : BIamOrganizationTypeEnum.values()) {
            typeTreeCacheMap.put(type, new TypeTreeCache());
        }
    }

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IBIamOrganizationClient organizationClient;

    public Set<String> recursionListSubId(BIamOrganizationTypeEnum type,
                                          String organizationId) {
        // 查询组织树
        BIamOrganizationTreeSimpleOutputDto treeOutputDto = treeAllSimple(type);

        // 找到指定的节点
        BIamOrganizationTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, organizationId);
        if (null == treeNode) {
            return Set.of();
        }

        // 获取节点以及子节点的所有数据
        List<BIamOrganizationTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeNode);
        return outputDtoList.stream().map(BIamOrganizationTreeSimpleOutputDto::getId).collect(Collectors.toSet());
    }

    public Set<String> recursionListSubIds(BIamOrganizationTypeEnum type,
                                           Set<String> organizationIdSet) {

        List<BIamOrganizationTreeSimpleOutputDto> treeOutputDtoList = new ArrayList<>();
        for (String id : organizationIdSet) {
            BIamOrganizationTreeSimpleOutputDto node = TreeUtil.findNode(treeAllSimple(type), id);
            if (null != node) {
                treeOutputDtoList.add(node);
            }
        }
        // 循环递归获取所有节点
        Set<String> idSet = new HashSet<>();
        for (BIamOrganizationTreeSimpleOutputDto treeOutputDto : treeOutputDtoList) {
            List<BIamOrganizationTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeOutputDto);
            idSet.addAll(outputDtoList.stream().map(BIamOrganizationTreeSimpleOutputDto::getId).toList());
        }
        return idSet;
    }

    public Set<String> recursionListSubIds(Set<String> organizationIdSet) {
        // 获取所有节点
        List<BIamOrganizationTreeSimpleOutputDto> treeOutputDtoList = new ArrayList<>();
        for (BIamOrganizationTypeEnum type : BIamOrganizationTypeEnum.values()) {
            // 获取Tree
            BIamOrganizationTreeSimpleOutputDto treeOutputDto = treeAllSimple(type);
            for (String id : organizationIdSet) {
                BIamOrganizationTreeSimpleOutputDto node = TreeUtil.findNode(treeOutputDto, id);
                if (null != node) {
                    treeOutputDtoList.add(node);
                }
            }

            if (treeOutputDtoList.size() == organizationIdSet.size()) {
                break;
            }
        }

        // 循环递归获取所有节点
        Set<String> idSet = new HashSet<>();
        for (BIamOrganizationTreeSimpleOutputDto treeOutputDto : treeOutputDtoList) {
            List<BIamOrganizationTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeOutputDto);
            idSet.addAll(outputDtoList.stream().map(BIamOrganizationTreeSimpleOutputDto::getId).toList());
        }
        return idSet;
    }

    public BIamOrganizationTreeSimpleOutputDto treeAllSimple(BIamOrganizationTypeEnum type) {
        String typeName = type.getName();
        String keyCode = customerRedisCommands.get(BIAM_ORGANIZATION_TREE_KEY + typeName);

        // 二级缓存命中
        TypeTreeCache typeCache = typeTreeCacheMap.get(type);
        if (StrUtil.isNotEmpty(keyCode) &&
                keyCode.equals(typeCache.version.get()) &&
                typeCache.cachedData != null) {
            return copyTreeWithVirtualNode(typeCache.cachedData, type);
        }

        synchronized (typeCache) {
            // Double-Check：防止并发情况下重复加载
            String recheckKeyCode = customerRedisCommands.get(BIAM_ORGANIZATION_TREE_KEY + typeName);
            if (StrUtil.isNotEmpty(recheckKeyCode) &&
                    recheckKeyCode.equals(typeCache.version.get()) &&
                    typeCache.cachedData != null) {
                return copyTreeWithVirtualNode(typeCache.cachedData, type);
            }

            BIamOrganizationTreeSimpleOutputDto treeSimpleOutputDto = null;

            // Redis加载数据（一级缓存）
            if (StrUtil.isNotEmpty(recheckKeyCode)) {
                String treeJson = customerRedisCommands.get(BIAM_ORGANIZATION_TREE_DATA + typeName + recheckKeyCode);
                if (StrUtil.isNotEmpty(treeJson)) {
                    treeSimpleOutputDto = JSONUtil.toBean(treeJson, BIamOrganizationTreeSimpleOutputDto.class);
                }
            }

            // Redis无数据，从远程加载
            if (null == treeSimpleOutputDto) {
                Tuples.Tuple2<String, BIamOrganizationTreeSimpleOutputDto> tuple2 = organizationClient.treeAllSimple(type);
                recheckKeyCode = tuple2._1();
                treeSimpleOutputDto = tuple2._2();
            }

            typeCache.cachedData = treeSimpleOutputDto;
            typeCache.version.set(recheckKeyCode);
            return copyTreeWithVirtualNode(typeCache.cachedData, type);
        }
    }

    public Map<String, BIamOrganizationSimpleOutputDto> mapByIdSet(Set<String> organizationIdSet) {
        Map<String, BIamOrganizationSimpleOutputDto> outputDtoMap = new HashMap<>();
        for (BIamOrganizationTypeEnum type : BIamOrganizationTypeEnum.values()) {
            // 获取Tree
            BIamOrganizationTreeSimpleOutputDto treeOutputDto = treeAllSimple(type);
            for (String id : organizationIdSet) {
                BIamOrganizationTreeSimpleOutputDto node = TreeUtil.findNode(treeOutputDto, id);
                if (null != node) {
                    BIamOrganizationSimpleOutputDto outputDto = new BIamOrganizationSimpleOutputDto();
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

    /**
     * 深拷贝组织树，并为根节点附加【未分配】虚拟节点。
     */
    private BIamOrganizationTreeSimpleOutputDto copyTreeWithVirtualNode(BIamOrganizationTreeSimpleOutputDto source,
                                                                        BIamOrganizationTypeEnum type) {
        BIamOrganizationTreeSimpleOutputDto target = copyTree(source);
        if (null == target) {
            return null;
        }

        // 添加【未分配】
        BIamOrganizationTreeSimpleOutputDto virtualSimpleTreeBo = new BIamOrganizationTreeSimpleOutputDto();
        virtualSimpleTreeBo.setId(type.getName() + SEPARATOR_COLON + DATA_ORGANIZATION_VIRTUAL_NODE);
        virtualSimpleTreeBo.setParentId(target.getId());
        virtualSimpleTreeBo.setName(DATA_ORGANIZATION_VIRTUAL_NODE_NAME);
        virtualSimpleTreeBo.setSort(Long.MAX_VALUE);
        virtualSimpleTreeBo.setCode(type.getName() + SEPARATOR_COLON + DATA_ORGANIZATION_VIRTUAL_NODE.toUpperCase());
        virtualSimpleTreeBo.setType(type);
        if (CollectionUtil.isEmpty(target.getChildren())) {
            target.setChildren(List.of(virtualSimpleTreeBo));
        } else {
            target.getChildren().addFirst(virtualSimpleTreeBo);
        }
        return target;
    }

    /**
     * 递归深拷贝组织树。
     */
    private BIamOrganizationTreeSimpleOutputDto copyTree(BIamOrganizationTreeSimpleOutputDto source) {
        if (null == source) {
            return null;
        }
        BIamOrganizationTreeSimpleOutputDto target = new BIamOrganizationTreeSimpleOutputDto();
        target.setId(source.getId());
        target.setParentId(source.getParentId());
        target.setName(source.getName());
        target.setSort(source.getSort());
        target.setCode(source.getCode());
        target.setType(source.getType());

        if (CollectionUtil.isNotEmpty(source.getChildren())) {
            List<BIamOrganizationTreeSimpleOutputDto> children = new ArrayList<>(source.getChildren().size());
            for (BIamOrganizationTreeSimpleOutputDto child : source.getChildren()) {
                children.add(copyTree(child));
            }
            target.setChildren(children);
        } else {
            target.setChildren(List.of());
        }
        return target;
    }

    private static class TypeTreeCache {
        private final AtomicReference<String> version = new AtomicReference<>();
        private volatile BIamOrganizationTreeSimpleOutputDto cachedData;
    }

}
