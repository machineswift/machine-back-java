package com.machine.starter.redis.cache.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.brand.IDataBrandCategoryClient;
import com.machine.client.data.brand.dto.output.DataBrandCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonDataConstant.BrandCategory.DATA_BRAND_CATEGORY_VIRTUAL_NODE;
import static com.machine.sdk.base.constant.CommonDataConstant.BrandCategory.DATA_BRAND_CATEGORY_VIRTUAL_NODE_NAME;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.BrandCategory.DATA_BRAND_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.BrandCategory.DATA_BRAND_CATEGORY_TREE_KEY;

@Slf4j
@Component
public class RedisDataBrandCategoryCache {

    private final AtomicReference<String> version = new AtomicReference<>();
    private volatile DataBrandCategoryTreeSimpleOutputDto cachedData;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataBrandCategoryClient brandCategoryClient;

    public Set<String> recursionListSubId(String categoryId) {
        DataBrandCategoryTreeSimpleOutputDto tree = treeAllSimple();
        DataBrandCategoryTreeSimpleOutputDto node = TreeUtil.findNode(tree, categoryId);
        if (node == null) {
            return Set.of();
        }
        return TreeUtil.collectAllNodes(node).stream()
                .map(DataBrandCategoryTreeSimpleOutputDto::getId)
                .collect(Collectors.toSet());
    }

    public Set<String> recursionListSubId(Set<String> categoryIdSet) {
        // 获取Tree
        DataBrandCategoryTreeSimpleOutputDto allTree = treeAllSimple();

        // 找到指定的节点
        List<DataBrandCategoryTreeSimpleOutputDto> nodeList = new ArrayList<>();
        for (String categoryId : categoryIdSet) {
            DataBrandCategoryTreeSimpleOutputDto node = TreeUtil.findNode(allTree, categoryId);
            if (null != node) {
                nodeList.add(node);
            }
        }

        // 获取节点以及子节点的所有数据
        Set<String> idSet = new HashSet<>();
        for (DataBrandCategoryTreeSimpleOutputDto node : nodeList) {
            idSet.addAll(TreeUtil.collectAllNodes(node).stream()
                    .map(DataBrandCategoryTreeSimpleOutputDto::getId)
                    .toList());
        }
        return idSet;
    }

    public DataBrandCategoryTreeSimpleOutputDto treeAllSimple() {
        String keyCode = customerRedisCommands.get(DATA_BRAND_CATEGORY_TREE_KEY);
        if (StrUtil.isNotEmpty(keyCode) && keyCode.equals(version.get()) && cachedData != null) {
            return copyTreeWithVirtualNode(cachedData);
        }

        synchronized (this) {
            String recheckVersion = customerRedisCommands.get(DATA_BRAND_CATEGORY_TREE_KEY);
            if (StrUtil.isNotEmpty(recheckVersion)
                    && recheckVersion.equals(version.get())
                    && cachedData != null) {
                return copyTreeWithVirtualNode(cachedData);
            }

            DataBrandCategoryTreeSimpleOutputDto tree = null;
            if (StrUtil.isNotEmpty(recheckVersion)) {
                String treeJson = customerRedisCommands.get(DATA_BRAND_CATEGORY_TREE_DATA + recheckVersion);
                if (StrUtil.isNotEmpty(treeJson)) {
                    tree = JSONUtil.toBean(treeJson, DataBrandCategoryTreeSimpleOutputDto.class);
                }
            }

            if (tree == null) {
                Tuples.Tuple2<String, DataBrandCategoryTreeSimpleOutputDto> result =
                        brandCategoryClient.treeAllSimple();
                recheckVersion = result._1();
                tree = result._2();
            }

            cachedData = tree;
            version.set(recheckVersion);
            return copyTreeWithVirtualNode(cachedData);
        }
    }

    /**
     * 深拷贝品牌分类树，并为根节点附加【未分配】虚拟节点。
     */
    private DataBrandCategoryTreeSimpleOutputDto copyTreeWithVirtualNode(DataBrandCategoryTreeSimpleOutputDto source) {
        DataBrandCategoryTreeSimpleOutputDto target = copyTree(source);
        if (null == target) {
            return null;
        }

        // 添加【未分配】
        DataBrandCategoryTreeSimpleOutputDto virtualNode = new DataBrandCategoryTreeSimpleOutputDto();
        virtualNode.setId(DATA_BRAND_CATEGORY_VIRTUAL_NODE);
        virtualNode.setParentId(target.getId());
        virtualNode.setName(DATA_BRAND_CATEGORY_VIRTUAL_NODE_NAME);
        virtualNode.setSort(Long.MAX_VALUE);
        virtualNode.setCode(DATA_BRAND_CATEGORY_VIRTUAL_NODE.toUpperCase());
        if (CollectionUtil.isEmpty(target.getChildren())) {
            target.setChildren(List.of(virtualNode));
        } else {
            target.getChildren().addFirst(virtualNode);
        }
        return target;
    }

    private DataBrandCategoryTreeSimpleOutputDto copyTree(DataBrandCategoryTreeSimpleOutputDto source) {
        if (source == null) {
            return null;
        }
        DataBrandCategoryTreeSimpleOutputDto target = new DataBrandCategoryTreeSimpleOutputDto();
        target.setId(source.getId());
        target.setParentId(source.getParentId());
        target.setName(source.getName());
        target.setSort(source.getSort());
        target.setCode(source.getCode());

        if (CollectionUtil.isNotEmpty(source.getChildren())) {
            List<DataBrandCategoryTreeSimpleOutputDto> children = new ArrayList<>(source.getChildren().size());
            for (DataBrandCategoryTreeSimpleOutputDto child : source.getChildren()) {
                children.add(copyTree(child));
            }
            target.setChildren(children);
        } else {
            target.setChildren(List.of());
        }
        return target;
    }
}
