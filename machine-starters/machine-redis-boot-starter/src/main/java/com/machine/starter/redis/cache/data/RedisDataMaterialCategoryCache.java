package com.machine.starter.redis.cache.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.filecenter.material.IDataMaterialCategoryClient;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonDataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_VIRTUAL_NODE;
import static com.machine.sdk.base.constant.CommonDataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_VIRTUAL_NODE_NAME;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_TREE_KEY;

@Slf4j
@Component
public class RedisDataMaterialCategoryCache {

    private final AtomicReference<String> version = new AtomicReference<>();
    private volatile DataMaterialCategoryTreeSimpleOutputDto cachedData;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataMaterialCategoryClient materialCategoryClient;

    public Set<String> recursionListSubId(String categoryId) {
        // 查询组织树
        DataMaterialCategoryTreeSimpleOutputDto treeOutputDto = treeAllSimple();

        // 找到指定的节点
        DataMaterialCategoryTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, categoryId);
        if (null == treeNode) {
            return Set.of();
        }

        // 获取节点以及子节点的所有数据
        List<DataMaterialCategoryTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeNode);
        for (DataMaterialCategoryTreeSimpleOutputDto outputDto : outputDtoList) {
            outputDto.setChildren(null);
        }
        return outputDtoList.stream().map(DataMaterialCategoryTreeSimpleOutputDto::getId).collect(Collectors.toSet());
    }

    public Set<String> recursionListSubId(Set<String> categoryIdSet) {
        // 获取所有节点
        List<DataMaterialCategoryTreeSimpleOutputDto> treeOutputDtoList = new ArrayList<>();

        // 获取Tree
        DataMaterialCategoryTreeSimpleOutputDto allTreeOutputDto = treeAllSimple();
        for (String id : categoryIdSet) {
            DataMaterialCategoryTreeSimpleOutputDto node = TreeUtil.findNode(allTreeOutputDto, id);
            if (null != node) {
                treeOutputDtoList.add(node);
            }
        }

        // 循环递归获取所有节点
        Set<String> idSet = new HashSet<>();
        for (DataMaterialCategoryTreeSimpleOutputDto treeOutputDto : treeOutputDtoList) {
            List<DataMaterialCategoryTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeOutputDto);
            idSet.addAll(outputDtoList.stream().map(DataMaterialCategoryTreeSimpleOutputDto::getId).toList());
        }
        return idSet;
    }

    public DataMaterialCategoryTreeSimpleOutputDto treeAllSimple() {
        String keyCode = customerRedisCommands.get(DATA_MATERIAL_CATEGORY_TREE_KEY);

        // 本地缓存命中
        if (StrUtil.isNotEmpty(keyCode)
                && keyCode.equals(version.get())
                && cachedData != null) {
            return copyTreeWithVirtualNode(cachedData);
        }

        synchronized (this) {
            // Double-Check：防止并发情况下重复加载
            String recheckVersion = customerRedisCommands.get(DATA_MATERIAL_CATEGORY_TREE_KEY);
            if (StrUtil.isNotEmpty(recheckVersion)
                    && recheckVersion.equals(version.get())
                    && cachedData != null) {
                return copyTreeWithVirtualNode(cachedData);
            }

            DataMaterialCategoryTreeSimpleOutputDto treeSimpleOutputDto = null;

            // Redis加载数据（一级缓存）
            if (StrUtil.isNotEmpty(recheckVersion)) {
                String treeJson = customerRedisCommands.get(DATA_MATERIAL_CATEGORY_TREE_DATA + recheckVersion);
                if (StrUtil.isNotEmpty(treeJson)) {
                    treeSimpleOutputDto = JSONUtil.toBean(treeJson, DataMaterialCategoryTreeSimpleOutputDto.class);
                }
            }

            // Redis无数据，从远程加载
            if (null == treeSimpleOutputDto) {
                Tuples.Tuple2<String, DataMaterialCategoryTreeSimpleOutputDto> tuple2 = materialCategoryClient
                        .treeAllSimple();
                recheckVersion = tuple2._1();
                treeSimpleOutputDto = tuple2._2();
            }

            cachedData = treeSimpleOutputDto;
            version.set(recheckVersion);
            return copyTreeWithVirtualNode(cachedData);
        }
    }

    /**
     * 深拷贝素材分类树，并为根节点附加【未分配】虚拟节点。
     */
    private DataMaterialCategoryTreeSimpleOutputDto copyTreeWithVirtualNode(
            DataMaterialCategoryTreeSimpleOutputDto source) {
        DataMaterialCategoryTreeSimpleOutputDto target = copyTree(source);
        if (null == target) {
            return null;
        }

        // 添加【未分配】
        DataMaterialCategoryTreeSimpleOutputDto virtualSimpleTreeBo = new DataMaterialCategoryTreeSimpleOutputDto();
        virtualSimpleTreeBo.setId(DATA_MATERIAL_CATEGORY_VIRTUAL_NODE);
        virtualSimpleTreeBo.setParentId(target.getId());
        virtualSimpleTreeBo.setName(DATA_MATERIAL_CATEGORY_VIRTUAL_NODE_NAME);
        virtualSimpleTreeBo.setSort(Long.MAX_VALUE);
        virtualSimpleTreeBo.setCode(DATA_MATERIAL_CATEGORY_VIRTUAL_NODE.toUpperCase());
        if (CollectionUtil.isEmpty(target.getChildren())) {
            target.setChildren(List.of(virtualSimpleTreeBo));
        } else {
            target.getChildren().addFirst(virtualSimpleTreeBo);
        }
        return target;
    }

    /**
     * 递归深拷贝素材分类树，防止调用方修改缓存数据影响业务
     */
    private DataMaterialCategoryTreeSimpleOutputDto copyTree(DataMaterialCategoryTreeSimpleOutputDto source) {
        if (null == source) {
            return null;
        }
        DataMaterialCategoryTreeSimpleOutputDto target = new DataMaterialCategoryTreeSimpleOutputDto();
        target.setId(source.getId());
        target.setParentId(source.getParentId());
        target.setName(source.getName());
        target.setSort(source.getSort());
        target.setCode(source.getCode());

        if (CollectionUtil.isNotEmpty(source.getChildren())) {
            List<DataMaterialCategoryTreeSimpleOutputDto> children = new ArrayList<>(source.getChildren().size());
            for (DataMaterialCategoryTreeSimpleOutputDto child : source.getChildren()) {
                children.add(copyTree(child));
            }
            target.setChildren(children);
        } else {
            target.setChildren(List.of());
        }
        return target;
    }

    public Map<String, DataMaterialCategoryTreeSimpleOutputDto> mapByIdSet(Set<String> organizationIdSet) {
        Map<String, DataMaterialCategoryTreeSimpleOutputDto> outputDtoMap = new HashMap<>();

        // 获取Tree
        DataMaterialCategoryTreeSimpleOutputDto treeOutputDto = treeAllSimple();
        for (String id : organizationIdSet) {
            DataMaterialCategoryTreeSimpleOutputDto node = TreeUtil.findNode(treeOutputDto, id);
            if (null != node) {
                DataMaterialCategoryTreeSimpleOutputDto outputDto = new DataMaterialCategoryTreeSimpleOutputDto();
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
