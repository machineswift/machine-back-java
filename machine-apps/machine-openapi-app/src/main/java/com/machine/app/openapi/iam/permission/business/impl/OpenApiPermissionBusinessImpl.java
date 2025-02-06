package com.machine.app.openapi.iam.permission.business.impl;

import com.machine.app.openapi.iam.permission.business.IOpenApiPermissionBusiness;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionIdRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionListSubRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionQueryAppListRequestVo;
import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.RedisCacheIamPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OpenApiPermissionBusinessImpl implements IOpenApiPermissionBusiness {

    @Autowired
    private RedisCacheIamPermission permissionCache;

    @Override
    public List<PermissionTreeOutputDto> listApp(OpenApiPermissionQueryAppListRequestVo request) {
        PermissionTreeOutputDto treeOutputDto = permissionCache.treeAll();
        List<PermissionTreeOutputDto> children = treeOutputDto.getChildren();
        for (PermissionTreeOutputDto child : children) {
            child.setChildren(null);
        }
        return children;
    }

    @Override
    public PermissionTreeOutputDto detail(OpenApiPermissionIdRequestVo request) {
        PermissionTreeOutputDto treeOutputDto = permissionCache.treeAll();
        PermissionTreeOutputDto targetNode = TreeUtil.findNode(treeOutputDto, request.getId());
        if (null == targetNode) {
            return null;
        }
        targetNode.setChildren(null);
        return targetNode;
    }

    @Override
    public List<String> listParentByTarget(OpenApiPermissionIdRequestVo request) {
        PermissionTreeOutputDto treeOutputDto = permissionCache.treeAll();
        PermissionTreeOutputDto targetNode = TreeUtil.findNode(treeOutputDto, request.getId());
        if (null == targetNode) {
            return List.of();
        }

        //获取指定组织的所有父组织ID列表（list元素第一个是当前组织ID，最后一个是父组织ID，从左至右组织层级递增）
        List<String> parentIdList = new ArrayList<>();
        do {
            parentIdList.add(targetNode.getId());
            targetNode = TreeUtil.findNode(treeOutputDto, targetNode.getParentId());
        } while (null != targetNode);

        return parentIdList;
    }

    @Override
    public List<String> listSubId(OpenApiPermissionListSubRequestVo request) {
        return permissionCache.listSubId(request.getId());
    }

    @Override
    public List<PermissionTreeOutputDto> listSub(OpenApiPermissionListSubRequestVo request) {
        return permissionCache.listSub(request.getId());
    }


}
