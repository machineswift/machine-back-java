package com.machine.starter.redis.cache.scm;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.scm.category.IScmFrontCategoryClient;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeOutputDto;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.FrontCategory.SCM_FRONT_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.FrontCategory.SCM_FRONT_CATEGORY_TREE_KEY;

@Slf4j
@Component
public class RedisScmFrontCategoryCache {

    private final AtomicReference<String> version = new AtomicReference<>();
    private volatile ScmFrontCategoryTreeOutputDto cachedData;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IScmFrontCategoryClient frontCategoryClient;

    public Set<String> recursionSubId(String id) {
        ScmFrontCategoryTreeOutputDto treeOutputDto = treeSimple();
        ScmFrontCategoryTreeOutputDto targetNode = TreeUtil.findNode(treeOutputDto, id);
        if (null == targetNode || CollectionUtil.isEmpty(targetNode.getChildren())) {
            return Set.of();
        }

        Set<String> childIdList = new HashSet<>();
        for (ScmFrontCategoryTreeOutputDto child : targetNode.getChildren()) {
            childIdList.add(child.getId());
        }
        return childIdList;
    }

    public List<ScmFrontCategoryTreeOutputDto> recursionSub(String id) {
        ScmFrontCategoryTreeOutputDto treeOutputDto = treeSimple();
        ScmFrontCategoryTreeOutputDto targetNode = TreeUtil.findNode(treeOutputDto, id);
        if (null == targetNode || CollectionUtil.isEmpty(targetNode.getChildren())) {
            return List.of();
        }

        List<ScmFrontCategoryTreeOutputDto> children = targetNode.getChildren();
        for (ScmFrontCategoryTreeOutputDto child : children) {
            child.setChildren(null);
        }
        return children;
    }

    public List<ScmFrontCategoryTreeOutputDto> recursionByIdSet(Collection<String> idSet) {
        ScmFrontCategoryTreeOutputDto allTreeOutputDto = treeSimple();
        List<ScmFrontCategoryTreeOutputDto> outputDtoList = new ArrayList<>();
        for (String id : idSet) {
            ScmFrontCategoryTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, id);
            if (null != targetNode) {
                outputDtoList.add(targetNode);
            }
        }
        return outputDtoList;
    }

    public ScmFrontCategoryTreeOutputDto treeSimple() {
        String keyCode = customerRedisCommands.get(SCM_FRONT_CATEGORY_TREE_KEY);

        // 二级缓存命中：版本号未变，直接返回深拷贝
        if (StrUtil.isNotEmpty(keyCode) &&
                keyCode.equals(version.get()) &&
                cachedData != null) {
            return copyTree(cachedData);
        }

        // 版本号变了或缓存为空，同步加载
        synchronized (this) {
            // Double-Check：防止并发情况下重复加载
            String recheckVersion = customerRedisCommands.get(SCM_FRONT_CATEGORY_TREE_KEY);
            if (StrUtil.isNotEmpty(recheckVersion) &&
                    recheckVersion.equals(version.get()) &&
                    cachedData != null) {
                return copyTree(cachedData);
            }

            ScmFrontCategoryTreeOutputDto treeOutputDto = null;

            // Redis加载数据（一级缓存）
            if (StrUtil.isNotEmpty(recheckVersion)) {
                String treeJson = customerRedisCommands.get(SCM_FRONT_CATEGORY_TREE_DATA + recheckVersion);
                if (StrUtil.isNotEmpty(treeJson)) {
                    treeOutputDto = JSONUtil.toBean(treeJson, ScmFrontCategoryTreeOutputDto.class);
                }
            }

            // Redis无数据，从远程加载
            if (null == treeOutputDto) {
                Tuples.Tuple2<String, ScmFrontCategoryTreeOutputDto> tuple2 = frontCategoryClient.treeAllSimple();
                recheckVersion = tuple2._1();
                treeOutputDto = tuple2._2();
            }

            cachedData = treeOutputDto;
            version.set(recheckVersion);
            return copyTree(cachedData);
        }
    }

    /**
     * 递归深拷贝分类树，防止调用方修改缓存数据影响业务
     */
    private ScmFrontCategoryTreeOutputDto copyTree(ScmFrontCategoryTreeOutputDto source) {
        if (null == source) {
            return null;
        }
        ScmFrontCategoryTreeOutputDto target = new ScmFrontCategoryTreeOutputDto();
        target.setId(source.getId());
        target.setParentId(source.getParentId());
        target.setName(source.getName());
        target.setSort(source.getSort());
        target.setCode(source.getCode());

        if (CollectionUtil.isNotEmpty(source.getChildren())) {
            List<ScmFrontCategoryTreeOutputDto> children = new ArrayList<>(source.getChildren().size());
            for (ScmFrontCategoryTreeOutputDto child : source.getChildren()) {
                children.add(copyTree(child));
            }
            target.setChildren(children);
        } else {
            target.setChildren(List.of());
        }
        return target;
    }
}