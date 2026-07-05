package com.machine.starter.redis.cache.scm;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.scm.category.IScmBackCategoryClient;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.BackCategory.SCM_BACK_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.BackCategory.SCM_BACK_CATEGORY_TREE_KEY;

@Slf4j
@Component
public class RedisScmBackCategoryCache {

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
        //获取树的动态key
        String keyCode = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_DATA + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                return JSONUtil.toBean(treeJson, ScmBackCategoryTreeSimpleOutputDto.class);
            }
        }
        return backCategoryClient.treeAllSimple();
    }
}
