package com.machine.starter.redis.cache;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.permission.IIamPermissionClient;
import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Permission.IAM_PERMISSION_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Permission.IAM_PERMISSION_TREE_KEY;

@Slf4j
@Component
public class RedisCacheIamPermission {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IIamPermissionClient permissionClient;

    public List<String> listSubId(String permissionId) {
        PermissionTreeOutputDto treeOutputDto = treeAll();
        PermissionTreeOutputDto targetNode = TreeUtil.findNode(treeOutputDto, permissionId);
        if (null == targetNode || CollectionUtil.isEmpty(targetNode.getChildren())) {
            return List.of();
        }

        List<String> childIdList = new ArrayList<>();
        for (PermissionTreeOutputDto child : targetNode.getChildren()) {
            childIdList.add(child.getId());
        }
        return childIdList;
    }

    public List<PermissionTreeOutputDto> listSub(String permissionId) {
        PermissionTreeOutputDto treeOutputDto = treeAll();
        PermissionTreeOutputDto targetNode = TreeUtil.findNode(treeOutputDto, permissionId);
        if (null == targetNode || CollectionUtil.isEmpty(targetNode.getChildren())) {
            return List.of();
        }

        List<PermissionTreeOutputDto> children = targetNode.getChildren();
        for (PermissionTreeOutputDto child : children) {
            child.setChildren(null);
        }
        return children;
    }

    public List<PermissionTreeOutputDto> listByIdSet(Collection<String> permissionIdSet) {
        PermissionTreeOutputDto allTreeOutputDto = treeAll();
        List<PermissionTreeOutputDto> outputDtoList = new ArrayList<>();
        for (String id : permissionIdSet) {
            PermissionTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, id);
            if (null != targetNode) {
                outputDtoList.add(targetNode);
            }
        }
        return outputDtoList;
    }

    public PermissionTreeOutputDto treeAll() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(IAM_PERMISSION_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(IAM_PERMISSION_TREE_DATA + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                return JSONUtil.toBean(treeJson, PermissionTreeOutputDto.class);
            }
        }
        return permissionClient.treeAll();
    }
}
