package com.machine.starter.redis.cache.data;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.tag.IDataTagCategoryClient;
import com.machine.client.data.tag.dto.output.DataTagCategorySimpleOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.TagCategory.DATA_TAG_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.TagCategory.DATA_TAG_CATEGORY_TREE_KEY;

@Slf4j
@Component
public class RedisCacheDataTagCategory {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataTagCategoryClient tagCategoryClient;
    

    public Set<String> recursionListSubId(ProfileSubjectTypeEnum type,
                                          String tagCategoryId) {
        //查询智能标签分类树
        DataTagCategoryTreeSimpleOutputDto treeOutputDto = treeAllSimple(type);

        //找到指定的节点
        DataTagCategoryTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, tagCategoryId);
        if (null == treeNode) {
            return Set.of();
        }

        //获取节点以及子节点的所有数据
        List<DataTagCategoryTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeNode);
        return outputDtoList.stream().map(DataTagCategoryTreeSimpleOutputDto::getId).collect(Collectors.toSet());
    }

    public Set<String> recursionListSubIds(ProfileSubjectTypeEnum type,
                                           Set<String> tagCategoryIdSet) {

        List<DataTagCategoryTreeSimpleOutputDto> treeOutputDtoList = new ArrayList<>();
        for (String id : tagCategoryIdSet) {
            DataTagCategoryTreeSimpleOutputDto node = TreeUtil.findNode(treeAllSimple(type), id);
            if (null != node) {
                treeOutputDtoList.add(node);
            }
        }
        //循环递归获取所有节点
        Set<String> idSet = new HashSet<>();
        for (DataTagCategoryTreeSimpleOutputDto treeOutputDto : treeOutputDtoList) {
            List<DataTagCategoryTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeOutputDto);
            idSet.addAll(outputDtoList.stream().map(DataTagCategoryTreeSimpleOutputDto::getId).toList());
        }
        return idSet;
    }

    public Set<String> recursionListSubIds(Set<String> tagCategoryIdSet) {
        //获取所有节点
        List<DataTagCategoryTreeSimpleOutputDto> treeOutputDtoList = new ArrayList<>();
        for (ProfileSubjectTypeEnum type : ProfileSubjectTypeEnum.values()) {
            //获取Tree
            DataTagCategoryTreeSimpleOutputDto treeOutputDto = treeAllSimple(type);
            for (String id : tagCategoryIdSet) {
                DataTagCategoryTreeSimpleOutputDto node = TreeUtil.findNode(treeOutputDto, id);
                if (null != node) {
                    treeOutputDtoList.add(node);
                }
            }

            if (treeOutputDtoList.size() == tagCategoryIdSet.size()) {
                break;
            }
        }

        //循环递归获取所有节点
        Set<String> idSet = new HashSet<>();
        for (DataTagCategoryTreeSimpleOutputDto treeOutputDto : treeOutputDtoList) {
            List<DataTagCategoryTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeOutputDto);
            idSet.addAll(outputDtoList.stream().map(DataTagCategoryTreeSimpleOutputDto::getId).toList());
        }
        return idSet;
    }


    public DataTagCategoryTreeSimpleOutputDto treeAllSimple(ProfileSubjectTypeEnum type) {
        String typeName = type.getName();
        //获取树的动态key
        String keyCode = customerRedisCommands.get(DATA_TAG_CATEGORY_TREE_KEY + typeName);

        DataTagCategoryTreeSimpleOutputDto treeSimpleOutputDto = null;
        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_TAG_CATEGORY_TREE_DATA + typeName + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                treeSimpleOutputDto = JSONUtil.toBean(treeJson, DataTagCategoryTreeSimpleOutputDto.class);
            }
        }

        //查询树
        if (null == treeSimpleOutputDto) {
            treeSimpleOutputDto = tagCategoryClient.treeAllSimple(type);
        }

        return treeSimpleOutputDto;
    }

    public Map<String, DataTagCategorySimpleOutputDto> mapByIdSet(Set<String> tagCategoryIdSet) {
        Map<String, DataTagCategorySimpleOutputDto> outputDtoMap = new HashMap<>();
        for (ProfileSubjectTypeEnum type : ProfileSubjectTypeEnum.values()) {
            //获取Tree
            DataTagCategoryTreeSimpleOutputDto treeOutputDto = treeAllSimple(type);
            for (String id : tagCategoryIdSet) {
                DataTagCategoryTreeSimpleOutputDto node = TreeUtil.findNode(treeOutputDto, id);
                if (null != node) {
                    DataTagCategorySimpleOutputDto outputDto = new DataTagCategorySimpleOutputDto();
                    outputDto.setId(node.getId());
                    outputDto.setParentId(node.getParentId());
                    outputDto.setCode(node.getCode());
                    outputDto.setName(node.getName());
                    outputDto.setType(node.getType());
                    outputDto.setSort(node.getSort());
                    outputDtoMap.put(id, outputDto);
                }
            }

            if (outputDtoMap.size() == tagCategoryIdSet.size()) {
                break;
            }
        }
        return outputDtoMap;
    }

}
