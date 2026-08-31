package com.machine.app.openapi.iam.permission.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.machine.app.openapi.iam.permission.business.IOpenApiPermissionBusiness;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionIdRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionListSubRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionQueryAppListRequestVo;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.starter.redis.cache.biam.RedisBIamPermissionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OpenApiPermissionBusinessImpl implements IOpenApiPermissionBusiness {

    @Autowired
    private RedisBIamPermissionCache permissionCache;

    @Override
    public List<BIamPermissionTreeOutputDto> listApp(OpenApiPermissionQueryAppListRequestVo request) {
        BIamPermissionTreeOutputDto treeOutputDto = permissionCache.treeAll();
        List<BIamPermissionTreeOutputDto> children = treeOutputDto.getChildren();
        for (BIamPermissionTreeOutputDto child : children) {
            child.setChildren(null);
        }
        return children;
    }

    @Override
    public BIamPermissionTreeOutputDto detail(OpenApiPermissionIdRequestVo request) {
        BIamPermissionTreeOutputDto treeOutputDto = permissionCache.treeAll();
        BIamPermissionTreeOutputDto targetNode = TreeUtil.findNode(treeOutputDto, request.getId());
        if (null == targetNode) {
            return null;
        }
        targetNode.setChildren(null);
        return targetNode;
    }

    @Override
    public List<String> listParentByTarget(OpenApiPermissionIdRequestVo request) {
        BIamPermissionTreeOutputDto treeOutputDto = permissionCache.treeAll();
        BIamPermissionTreeOutputDto targetNode = TreeUtil.findNode(treeOutputDto, request.getId());
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
    public List<BIamPermissionTreeOutputDto> listSub(OpenApiPermissionListSubRequestVo request) {
        BIamPermissionTreeOutputDto treeOutputDto = permissionCache.treeAll();
        BIamPermissionTreeOutputDto targetNode = TreeUtil.findNode(treeOutputDto, request.getId());
        if (null == targetNode || CollectionUtil.isEmpty(targetNode.getChildren())) {
            return List.of();
        }

        List<BIamPermissionTreeOutputDto> children = targetNode.getChildren();
        for (BIamPermissionTreeOutputDto child : children) {
            child.setChildren(null);
        }
        return children;
    }

}
