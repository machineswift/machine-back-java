package com.machine.starter.redis.cache.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.attachment.IDataAttachmentCategoryClient;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonDataConstant.AttachmentCategory.DATA_ATTACHMENT_CATEGORY_VIRTUAL_NODE;
import static com.machine.sdk.common.constant.CommonDataConstant.AttachmentCategory.DATA_ATTACHMENT_CATEGORY_VIRTUAL_NODE_NAME;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.AttachmentCategory.DATA_ATTACHMENT_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.AttachmentCategory.DATA_ATTACHMENT_CATEGORY_TREE_KEY;

@Slf4j
@Component
public class RedisCacheDataAttachmentCategory {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataAttachmentCategoryClient attachmentCategoryClient;

    public Set<String> recursionListSubId(String categoryId) {
        //查询组织树
        DataAttachmentCategoryTreeSimpleOutputDto treeOutputDto = treeAllSimple();

        //找到指定的节点
        DataAttachmentCategoryTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, categoryId);
        if (null == treeNode) {
            return Set.of();
        }

        //获取节点以及子节点的所有数据
        List<DataAttachmentCategoryTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeNode);
        for (DataAttachmentCategoryTreeSimpleOutputDto outputDto : outputDtoList) {
            outputDto.setChildren(null);
        }
        return outputDtoList.stream().map(DataAttachmentCategoryTreeSimpleOutputDto::getId).collect(Collectors.toSet());
    }

    public Set<String> recursionListSubId(Set<String> categoryIdSet) {
        //获取所有节点
        List<DataAttachmentCategoryTreeSimpleOutputDto> treeOutputDtoList = new ArrayList<>();

        //获取Tree
        DataAttachmentCategoryTreeSimpleOutputDto allTreeOutputDto = treeAllSimple();
        for (String id : categoryIdSet) {
            DataAttachmentCategoryTreeSimpleOutputDto node = TreeUtil.findNode(allTreeOutputDto, id);
            if (null != node) {
                treeOutputDtoList.add(node);
            }
        }

        //循环递归获取所有节点
        Set<String> idSet = new HashSet<>();
        for (DataAttachmentCategoryTreeSimpleOutputDto treeOutputDto : treeOutputDtoList) {
            List<DataAttachmentCategoryTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeOutputDto);
            idSet.addAll(outputDtoList.stream().map(DataAttachmentCategoryTreeSimpleOutputDto::getId).toList());
        }
        return idSet;
    }


    public DataAttachmentCategoryTreeSimpleOutputDto treeAllSimple() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(DATA_ATTACHMENT_CATEGORY_TREE_KEY);

        DataAttachmentCategoryTreeSimpleOutputDto treeSimpleOutputDto = null;
        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_ATTACHMENT_CATEGORY_TREE_DATA + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                treeSimpleOutputDto = JSONUtil.toBean(treeJson, DataAttachmentCategoryTreeSimpleOutputDto.class);
            }
        }

        //查询树
        if (null == treeSimpleOutputDto) {
            treeSimpleOutputDto = attachmentCategoryClient.treeAllSimple();
        }

        { //添加【未分配】
            DataAttachmentCategoryTreeSimpleOutputDto virtualSimpleTreeBo = new DataAttachmentCategoryTreeSimpleOutputDto();
            virtualSimpleTreeBo.setId(DATA_ATTACHMENT_CATEGORY_VIRTUAL_NODE);
            virtualSimpleTreeBo.setParentId(treeSimpleOutputDto.getId());
            virtualSimpleTreeBo.setName(DATA_ATTACHMENT_CATEGORY_VIRTUAL_NODE_NAME);
            virtualSimpleTreeBo.setSort(Long.MAX_VALUE);
            virtualSimpleTreeBo.setCode(DATA_ATTACHMENT_CATEGORY_VIRTUAL_NODE.toUpperCase());
            if (CollectionUtil.isEmpty(treeSimpleOutputDto.getChildren())) {
                treeSimpleOutputDto.setChildren(List.of(virtualSimpleTreeBo));
            } else {
                treeSimpleOutputDto.getChildren().addFirst(virtualSimpleTreeBo);
            }
        }

        return treeSimpleOutputDto;
    }

    public Map<String, DataAttachmentCategoryTreeSimpleOutputDto> mapByIdSet(Set<String> organizationIdSet) {
        Map<String, DataAttachmentCategoryTreeSimpleOutputDto> outputDtoMap = new HashMap<>();

        //获取Tree
        DataAttachmentCategoryTreeSimpleOutputDto treeOutputDto = treeAllSimple();
        for (String id : organizationIdSet) {
            DataAttachmentCategoryTreeSimpleOutputDto node = TreeUtil.findNode(treeOutputDto, id);
            if (null != node) {
                DataAttachmentCategoryTreeSimpleOutputDto outputDto = new DataAttachmentCategoryTreeSimpleOutputDto();
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
