package com.machine.starter.redis.cache.scm;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.scm.category.IScmBackCategoryClient;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.BackCategory.SCM_BACK_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.BackCategory.SCM_BACK_CATEGORY_TREE_KEY;

@Slf4j
@Component
public class RedisScmBackCategoryCache {

    private final AtomicReference<String> version = new AtomicReference<>();
    private volatile ScmBackCategoryTreeSimpleOutputDto cachedData;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IScmBackCategoryClient backCategoryClient;

    public Set<String> recursionSubId(String id) {
        ScmBackCategoryTreeSimpleOutputDto treeOutputDto = treeSimple();
        ScmBackCategoryTreeSimpleOutputDto targetNode = TreeUtil.findNode(treeOutputDto, id);
        if (null == targetNode || CollectionUtil.isEmpty(targetNode.getChildren())) {
            return Set.of();
        }

        Set<String> childIdList = new HashSet<>();
        for (ScmBackCategoryTreeSimpleOutputDto child : targetNode.getChildren()) {
            childIdList.add(child.getId());
        }
        return childIdList;
    }

    public List<ScmBackCategoryTreeSimpleOutputDto> recursionSub(String id) {
        ScmBackCategoryTreeSimpleOutputDto treeOutputDto = treeSimple();
        ScmBackCategoryTreeSimpleOutputDto targetNode = TreeUtil.findNode(treeOutputDto, id);
        if (null == targetNode || CollectionUtil.isEmpty(targetNode.getChildren())) {
            return List.of();
        }

        List<ScmBackCategoryTreeSimpleOutputDto> children = targetNode.getChildren();
        for (ScmBackCategoryTreeSimpleOutputDto child : children) {
            child.setChildren(null);
        }
        return children;
    }

    public List<ScmBackCategoryTreeSimpleOutputDto> recursionByIdSet(Collection<String> idSet) {
        ScmBackCategoryTreeSimpleOutputDto allTreeOutputDto = treeSimple();
        List<ScmBackCategoryTreeSimpleOutputDto> outputDtoList = new ArrayList<>();
        for (String id : idSet) {
            ScmBackCategoryTreeSimpleOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, id);
            if (null != targetNode) {
                outputDtoList.add(targetNode);
            }
        }
        return outputDtoList;
    }

    public ScmBackCategoryTreeSimpleOutputDto treeSimple() {
        String keyCode = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_KEY);

        // 二级缓存命中：版本号未变，直接返回深拷贝
        if (StrUtil.isNotEmpty(keyCode) &&
                keyCode.equals(version.get()) &&
                cachedData != null) {
            return copyTree(cachedData);
        }

        // 版本号变了或缓存为空，同步加载
        synchronized (this) {
            // Double-Check：防止并发情况下重复加载
            String recheckVersion = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_KEY);
            if (StrUtil.isNotEmpty(recheckVersion) &&
                    recheckVersion.equals(version.get())
                    && cachedData != null) {
                return copyTree(cachedData);
            }

            ScmBackCategoryTreeSimpleOutputDto treeOutputDto = null;

            // Redis加载数据（一级缓存）
            if (StrUtil.isNotEmpty(recheckVersion)) {
                String treeJson = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_DATA + recheckVersion);
                if (StrUtil.isNotEmpty(treeJson)) {
                    treeOutputDto = JSONUtil.toBean(treeJson, ScmBackCategoryTreeSimpleOutputDto.class);
                }
            }

            // Redis无数据，从远程加载
            if (null == treeOutputDto) {
                Tuples.Tuple2<String, ScmBackCategoryTreeSimpleOutputDto> tuple2 = backCategoryClient.treeAllSimple();
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
    private ScmBackCategoryTreeSimpleOutputDto copyTree(ScmBackCategoryTreeSimpleOutputDto source) {
        if (null == source) {
            return null;
        }
        ScmBackCategoryTreeSimpleOutputDto target = new ScmBackCategoryTreeSimpleOutputDto();
        target.setId(source.getId());
        target.setParentId(source.getParentId());
        target.setName(source.getName());
        target.setSort(source.getSort());
        target.setCode(source.getCode());

        if (CollectionUtil.isNotEmpty(source.getChildren())) {
            List<ScmBackCategoryTreeSimpleOutputDto> children = new ArrayList<>(source.getChildren().size());
            for (ScmBackCategoryTreeSimpleOutputDto child : source.getChildren()) {
                children.add(copyTree(child));
            }
            target.setChildren(children);
        } else {
            target.setChildren(List.of());
        }
        return target;
    }
}
