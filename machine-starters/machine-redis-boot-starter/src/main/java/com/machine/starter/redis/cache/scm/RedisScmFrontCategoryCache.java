package com.machine.starter.redis.cache.scm;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.scm.category.IScmFrontCategoryClient;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeOutputDto;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.FrontCategory.SCM_FRONT_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.FrontCategory.SCM_FRONT_CATEGORY_TREE_KEY;

@Slf4j
@Component
public class RedisScmFrontCategoryCache {

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

        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(SCM_FRONT_CATEGORY_TREE_DATA + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                return JSONUtil.toBean(treeJson, ScmFrontCategoryTreeOutputDto.class);
            }
        }
        return frontCategoryClient.treeAllSimple();
    }
}