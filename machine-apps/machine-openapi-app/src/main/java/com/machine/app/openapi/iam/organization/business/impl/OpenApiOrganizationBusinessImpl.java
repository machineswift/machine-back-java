package com.machine.app.openapi.iam.organization.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.iam.organization.business.IOpenApiOrganizationBusiness;
import com.machine.app.openapi.iam.organization.controller.vo.request.OpenApiOrganizationIdRequestVo;
import com.machine.app.openapi.iam.organization.controller.vo.request.OpenApiOrganizationRootIdRequestVo;
import com.machine.app.openapi.iam.organization.controller.vo.response.OpenApiOrganizationDetailResponseVo;
import com.machine.client.iam.biam.organization.IBIamOrganizationClient;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationDetailOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.tree.TreeNode;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.starter.redis.cache.biam.RedisBIamOrganizationCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OpenApiOrganizationBusinessImpl implements IOpenApiOrganizationBusiness {

    @Autowired
    private RedisBIamOrganizationCache organizationCache;

    @Autowired
    private IBIamOrganizationClient organizationClient;

    @Override
    public String rootId(OpenApiOrganizationRootIdRequestVo request) {
        return request.getType().getName().toLowerCase();
    }

    @Override
    public OpenApiOrganizationDetailResponseVo detail(OpenApiOrganizationIdRequestVo request) {
        BIamOrganizationDetailOutputDto outputDto = organizationClient.detail(new IdRequest(request.getId()));
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiOrganizationDetailResponseVo.class);
    }

    @Override
    public List<String> listSubId(OpenApiOrganizationIdRequestVo request) {
        return listSub(request).stream().map(TreeNode::getId).toList();
    }

    @Override
    public List<BIamOrganizationTreeSimpleOutputDto> listSub(OpenApiOrganizationIdRequestVo request) {
        BIamOrganizationDetailOutputDto detailOutputDto = organizationClient.detail(new IdRequest(request.getId()));
        if (null == detailOutputDto) {
            return List.of();
        }

        //查询组织树
        BIamOrganizationTreeSimpleOutputDto treeOutputDto = organizationCache.treeAllSimple(detailOutputDto.getType());

        //找到指定的节点
        BIamOrganizationTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, request.getId());
        if (null == treeNode) {
            return List.of();
        }

        //获取对应节点的子节点
        List<BIamOrganizationTreeSimpleOutputDto> outputDtoList = treeNode.getChildren();
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }
        for (BIamOrganizationTreeSimpleOutputDto outputDto : outputDtoList) {
            outputDto.setChildren(null);
        }
        return outputDtoList;
    }

    @Override
    public List<String> listParentByTarget(OpenApiOrganizationIdRequestVo request) {
        BIamOrganizationDetailOutputDto detailOutputDto = organizationClient.detail(new IdRequest(request.getId()));
        if (null == detailOutputDto) {
            return List.of();
        }

        //查询组织树
        BIamOrganizationTreeSimpleOutputDto treeOutputDto = organizationCache.treeAllSimple(detailOutputDto.getType());

        //找到指定的节点
        BIamOrganizationTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, request.getId());
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
