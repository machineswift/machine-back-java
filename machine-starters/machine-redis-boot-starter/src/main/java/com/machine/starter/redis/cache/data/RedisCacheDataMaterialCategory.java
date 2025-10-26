package com.machine.starter.redis.cache.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.material.IDataMaterialCategoryClient;
import com.machine.client.data.material.dto.output.DataMaterialCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonDataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_VIRTUAL_NODE;
import static com.machine.sdk.common.constant.CommonDataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_VIRTUAL_NODE_NAME;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_TREE_KEY;

@Slf4j
@Component
public class RedisCacheDataMaterialCategory {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataMaterialCategoryClient materialCategoryClient;

    public Set<String> recursionListSubId(String categoryId) {
        //查询组织树
        DataMaterialCategoryTreeSimpleOutputDto treeOutputDto = treeAllSimple();

        //找到指定的节点
        DataMaterialCategoryTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, categoryId);
        if (null == treeNode) {
            return Set.of();
        }

        //获取节点以及子节点的所有数据
        List<DataMaterialCategoryTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeNode);
        for (DataMaterialCategoryTreeSimpleOutputDto outputDto : outputDtoList) {
            outputDto.setChildren(null);
        }
        return outputDtoList.stream().map(DataMaterialCategoryTreeSimpleOutputDto::getId).collect(Collectors.toSet());
    }

    public Set<String> recursionListSubId(Set<String> categoryIdSet) {
        //获取所有节点
        List<DataMaterialCategoryTreeSimpleOutputDto> treeOutputDtoList = new ArrayList<>();

        //获取Tree
        DataMaterialCategoryTreeSimpleOutputDto allTreeOutputDto = treeAllSimple();
        for (String id : categoryIdSet) {
            DataMaterialCategoryTreeSimpleOutputDto node = TreeUtil.findNode(allTreeOutputDto, id);
            if (null != node) {
                treeOutputDtoList.add(node);
            }
        }

        //循环递归获取所有节点
        Set<String> idSet = new HashSet<>();
        for (DataMaterialCategoryTreeSimpleOutputDto treeOutputDto : treeOutputDtoList) {
            List<DataMaterialCategoryTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeOutputDto);
            idSet.addAll(outputDtoList.stream().map(DataMaterialCategoryTreeSimpleOutputDto::getId).toList());
        }
        return idSet;
    }


    public DataMaterialCategoryTreeSimpleOutputDto treeAllSimple() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(DATA_MATERIAL_CATEGORY_TREE_KEY);

        DataMaterialCategoryTreeSimpleOutputDto treeSimpleOutputDto = null;
        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_MATERIAL_CATEGORY_TREE_DATA + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                treeSimpleOutputDto = JSONUtil.toBean(treeJson, DataMaterialCategoryTreeSimpleOutputDto.class);
            }
        }

        //查询树
        if (null == treeSimpleOutputDto) {
            treeSimpleOutputDto = materialCategoryClient.treeAllSimple();
        }

        { //添加【未分配】
            DataMaterialCategoryTreeSimpleOutputDto virtualSimpleTreeBo = new DataMaterialCategoryTreeSimpleOutputDto();
            virtualSimpleTreeBo.setId(DATA_MATERIAL_CATEGORY_VIRTUAL_NODE);
            virtualSimpleTreeBo.setParentId(treeSimpleOutputDto.getId());
            virtualSimpleTreeBo.setName(DATA_MATERIAL_CATEGORY_VIRTUAL_NODE_NAME);
            virtualSimpleTreeBo.setSort(Long.MAX_VALUE);
            virtualSimpleTreeBo.setCode(DATA_MATERIAL_CATEGORY_VIRTUAL_NODE.toUpperCase());
            if (CollectionUtil.isEmpty(treeSimpleOutputDto.getChildren())) {
                treeSimpleOutputDto.setChildren(List.of(virtualSimpleTreeBo));
            } else {
                treeSimpleOutputDto.getChildren().addFirst(virtualSimpleTreeBo);
            }
        }

        return treeSimpleOutputDto;
    }

    public Map<String, DataMaterialCategoryTreeSimpleOutputDto> mapByIdSet(Set<String> organizationIdSet) {
        Map<String, DataMaterialCategoryTreeSimpleOutputDto> outputDtoMap = new HashMap<>();

        //获取Tree
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
