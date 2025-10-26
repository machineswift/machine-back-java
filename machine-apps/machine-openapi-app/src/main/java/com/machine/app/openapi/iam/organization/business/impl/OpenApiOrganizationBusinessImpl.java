package com.machine.app.openapi.iam.organization.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.iam.organization.business.IOpenApiOrganizationBusiness;
import com.machine.app.openapi.iam.organization.controller.vo.request.OpenApiOrganizationIdRequestVo;
import com.machine.app.openapi.iam.organization.controller.vo.request.OpenApiOrganizationRootIdRequestVo;
import com.machine.app.openapi.iam.organization.controller.vo.response.OpenApiOrganizationDetailResponseVo;
import com.machine.client.iam.organization.IIamOrganizationClient;
import com.machine.client.iam.organization.dto.output.IamOrganizationDetailOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.tree.TreeNode;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.iam.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OpenApiOrganizationBusinessImpl implements IOpenApiOrganizationBusiness {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IIamOrganizationClient organizationClient;

    @Override
    public String rootId(OpenApiOrganizationRootIdRequestVo request) {
        return request.getType().getCode().toLowerCase();
    }

    @Override
    public OpenApiOrganizationDetailResponseVo detail(OpenApiOrganizationIdRequestVo request) {
        IamOrganizationDetailOutputDto outputDto = organizationClient.detail(new IdRequest(request.getId()));
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiOrganizationDetailResponseVo.class);
    }

    @Override
    public List<String> listSubId(OpenApiOrganizationIdRequestVo request) {
        return listSub(request).stream().map(TreeNode::getId).toList();
    }

    @Override
    public List<IamOrganizationTreeSimpleOutputDto> listSub(OpenApiOrganizationIdRequestVo request) {
        IamOrganizationDetailOutputDto detailOutputDto = organizationClient.detail(new IdRequest(request.getId()));
        if (null == detailOutputDto) {
            return List.of();
        }

        //查询组织树
        IamOrganizationTreeSimpleOutputDto treeOutputDto = organizationCache.treeAllSimple(detailOutputDto.getType());

        //找到指定的节点
        IamOrganizationTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, request.getId());
        if (null == treeNode) {
            return List.of();
        }

        //获取对应节点的子节点
        List<IamOrganizationTreeSimpleOutputDto> outputDtoList = treeNode.getChildren();
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }
        for (IamOrganizationTreeSimpleOutputDto outputDto : outputDtoList) {
            outputDto.setChildren(null);
        }
        return outputDtoList;
    }

    @Override
    public List<String> listParentByTarget(OpenApiOrganizationIdRequestVo request) {
        IamOrganizationDetailOutputDto detailOutputDto = organizationClient.detail(new IdRequest(request.getId()));
        if (null == detailOutputDto) {
            return List.of();
        }

        //查询组织树
        IamOrganizationTreeSimpleOutputDto treeOutputDto = organizationCache.treeAllSimple(detailOutputDto.getType());

        //找到指定的节点
        IamOrganizationTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, request.getId());
        if (null == treeNode) {
            return List.of();
        }

        //获取指定组织的所有父组织ID列表（list元素第一个是当前组织ID，最后一个是根组织ID，从左至右组织层级递增）
        List<String> parentIdList = new ArrayList<>();
        do {
            parentIdList.add(treeNode.getId());
            treeNode = TreeUtil.findNode(treeOutputDto, treeNode.getParentId());
        } while (null != treeNode);

        return parentIdList;
    }
}
