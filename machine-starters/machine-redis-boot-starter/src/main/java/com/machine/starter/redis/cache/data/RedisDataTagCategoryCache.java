package com.machine.starter.redis.cache.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.tag.IDataTagCategoryClient;
import com.machine.client.data.tag.dto.output.DataTagCategorySimpleOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.TagCategory.DATA_TAG_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.TagCategory.DATA_TAG_CATEGORY_TREE_KEY;

@Slf4j
@Component
public class RedisDataTagCategoryCache {

    /**
     * 各画像主体类型的本地缓存（版本号 + 树数据）
     */
    private final Map<ProfileSubjectTypeEnum, TagCategoryLocalCache> typeTreeCacheMap = new EnumMap<>(
            ProfileSubjectTypeEnum.class);
    {
        for (ProfileSubjectTypeEnum type : ProfileSubjectTypeEnum.values()) {
            typeTreeCacheMap.put(type, new TagCategoryLocalCache());
        }
    }

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataTagCategoryClient tagCategoryClient;

    public Set<String> recursionListSubId(ProfileSubjectTypeEnum type,
            String tagCategoryId) {
        // 查询智能标签分类树
        DataTagCategoryTreeSimpleOutputDto treeOutputDto = treeAllSimple(type);

        // 找到指定的节点
        DataTagCategoryTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, tagCategoryId);
        if (null == treeNode) {
            return Set.of();
        }

        // 获取节点以及子节点的所有数据
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
        // 循环递归获取所有节点
        Set<String> idSet = new HashSet<>();
        for (DataTagCategoryTreeSimpleOutputDto treeOutputDto : treeOutputDtoList) {
            List<DataTagCategoryTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeOutputDto);
            idSet.addAll(outputDtoList.stream().map(DataTagCategoryTreeSimpleOutputDto::getId).toList());
        }
        return idSet;
    }

    public Set<String> recursionListSubIds(Set<String> tagCategoryIdSet) {
        // 获取所有节点
        List<DataTagCategoryTreeSimpleOutputDto> treeOutputDtoList = new ArrayList<>();
        for (ProfileSubjectTypeEnum type : ProfileSubjectTypeEnum.values()) {
            // 获取Tree
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

        // 循环递归获取所有节点
        Set<String> idSet = new HashSet<>();
        for (DataTagCategoryTreeSimpleOutputDto treeOutputDto : treeOutputDtoList) {
            List<DataTagCategoryTreeSimpleOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeOutputDto);
            idSet.addAll(outputDtoList.stream().map(DataTagCategoryTreeSimpleOutputDto::getId).toList());
        }
        return idSet;
    }

    public DataTagCategoryTreeSimpleOutputDto treeAllSimple(ProfileSubjectTypeEnum type) {
        String typeName = type.getName();
        String keyCode = customerRedisCommands.get(DATA_TAG_CATEGORY_TREE_KEY + typeName);

        // 本地缓存命中：版本号未变，直接返回深拷贝
        TagCategoryLocalCache typeCache = typeTreeCacheMap.get(type);
        if (StrUtil.isNotEmpty(keyCode)
                && keyCode.equals(typeCache.version.get())
                && typeCache.cachedData != null) {
            return copyTree(typeCache.cachedData);
        }

        // 版本号变了或缓存为空，同步加载
        synchronized (typeCache) {
            // Double-Check：防止并发情况下重复加载
            String recheckKeyCode = customerRedisCommands.get(DATA_TAG_CATEGORY_TREE_KEY + typeName);
            if (StrUtil.isNotEmpty(recheckKeyCode)
                    && recheckKeyCode.equals(typeCache.version.get())
                    && typeCache.cachedData != null) {
                return copyTree(typeCache.cachedData);
            }

            DataTagCategoryTreeSimpleOutputDto treeSimpleOutputDto = null;

            // Redis加载数据（一级缓存）
            if (StrUtil.isNotEmpty(recheckKeyCode)) {
                String treeJson = customerRedisCommands.get(DATA_TAG_CATEGORY_TREE_DATA + typeName + recheckKeyCode);
                if (StrUtil.isNotEmpty(treeJson)) {
                    treeSimpleOutputDto = JSONUtil.toBean(treeJson, DataTagCategoryTreeSimpleOutputDto.class);
                }
            }

            // Redis无数据，从远程加载
            if (null == treeSimpleOutputDto) {
                Tuples.Tuple2<String, DataTagCategoryTreeSimpleOutputDto> tuple2 = tagCategoryClient
                        .treeAllSimple(type);
                recheckKeyCode = tuple2._1();
                treeSimpleOutputDto = tuple2._2();
            }

            typeCache.cachedData = treeSimpleOutputDto;
            typeCache.version.set(recheckKeyCode);
            return copyTree(typeCache.cachedData);
        }
    }

    /**
     * 递归深拷贝标签分类树，防止调用方修改缓存数据影响业务
     */
    private DataTagCategoryTreeSimpleOutputDto copyTree(DataTagCategoryTreeSimpleOutputDto source) {
        if (null == source) {
            return null;
        }
        DataTagCategoryTreeSimpleOutputDto target = new DataTagCategoryTreeSimpleOutputDto();
        target.setId(source.getId());
        target.setParentId(source.getParentId());
        target.setName(source.getName());
        target.setSort(source.getSort());
        target.setCode(source.getCode());
        target.setType(source.getType());

        if (CollectionUtil.isNotEmpty(source.getChildren())) {
            List<DataTagCategoryTreeSimpleOutputDto> children = new ArrayList<>(source.getChildren().size());
            for (DataTagCategoryTreeSimpleOutputDto child : source.getChildren()) {
                children.add(copyTree(child));
            }
            target.setChildren(children);
        } else {
            target.setChildren(List.of());
        }
        return target;
    }

    private static class TagCategoryLocalCache {
        private final AtomicReference<String> version = new AtomicReference<>();
        private volatile DataTagCategoryTreeSimpleOutputDto cachedData;
    }

    public Map<String, DataTagCategorySimpleOutputDto> mapByIdSet(Set<String> tagCategoryIdSet) {
        Map<String, DataTagCategorySimpleOutputDto> outputDtoMap = new HashMap<>();
        for (ProfileSubjectTypeEnum type : ProfileSubjectTypeEnum.values()) {
            // 获取Tree
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
