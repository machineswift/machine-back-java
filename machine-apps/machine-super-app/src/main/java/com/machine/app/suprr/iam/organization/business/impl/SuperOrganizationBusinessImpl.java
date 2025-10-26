package com.machine.app.suprr.iam.organization.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.suprr.iam.organization.business.ISuperOrganizationBusiness;
import com.machine.app.suprr.iam.organization.business.bo.SuperOrganizationTreeExpandBo;
import com.machine.app.suprr.iam.organization.controller.vo.request.SupeOrganizationTreeAllRequestVo;
import com.machine.app.suprr.iam.organization.controller.vo.request.SupeOrganizationTreeRequestVo;
import com.machine.app.suprr.iam.organization.controller.vo.response.SuperOrganizationTreeExpandSelfResponseVo;
import com.machine.app.suprr.iam.organization.controller.vo.response.SuperOrganizationTreeSimpleSelfResponseVo;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.data.shop.dto.output.DataShopOrganizationRelationListOutputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationShopRelationQueryListInputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.iam.RedisCacheIamDataPermission;
import com.machine.starter.redis.cache.iam.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SuperOrganizationBusinessImpl implements ISuperOrganizationBusiness {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private RedisCacheIamDataPermission redisCacheIamDataPermission;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public IamOrganizationTreeSimpleOutputDto treeAllSimple(SupeOrganizationTreeAllRequestVo request) {
        return organizationCache.treeAllSimple(request.getType());
    }

    @Override
    public SuperOrganizationTreeSimpleSelfResponseVo treeSelfSimple(SupeOrganizationTreeRequestVo request) {
        IamOrganizationTreeSimpleOutputDto allTreeOutput = organizationCache.treeAllSimple(request.getType());

        DataPermissionDto dataPermissionDto = redisCacheIamDataPermission.dataPermission4SuperApp();
        if (CollectionUtil.isEmpty(dataPermissionDto.getShopIdSet())) {
            allTreeOutput.setChildren(List.of());
            return JSONUtil.toBean(JSONUtil.toJsonStr(allTreeOutput), SuperOrganizationTreeSimpleSelfResponseVo.class);
        }

        //获取当前门店关联的组织信息
        IamOrganizationShopRelationQueryListInputDto queryListInputDto = new IamOrganizationShopRelationQueryListInputDto();
        queryListInputDto.setOrganizationType(request.getType());
        queryListInputDto.setShopIdSet(dataPermissionDto.getShopIdSet());
        List<DataShopOrganizationRelationListOutputDto> relationListOutputDtoList = shopOrganizationRelationClient.listByCondition(queryListInputDto);

        //获取拥有权限的树
        IamOrganizationTreeSimpleOutputDto filterTreeOutput = TreeUtil.filterTree(allTreeOutput,
                relationListOutputDtoList.stream().map(DataShopOrganizationRelationListOutputDto::getOrganizationId).collect(Collectors.toSet()));
        if (null == filterTreeOutput) {
            return null;
        }
        //返回
        return JSONUtil.toBean(JSONUtil.toJsonStr(filterTreeOutput), SuperOrganizationTreeSimpleSelfResponseVo.class);
    }

    @Override
    public SuperOrganizationTreeExpandSelfResponseVo treeSelfExpand(SupeOrganizationTreeRequestVo request) {
        IamOrganizationTreeSimpleOutputDto allTreeOutput = organizationCache.treeAllSimple(request.getType());

        DataPermissionDto dataPermissionDto = redisCacheIamDataPermission.dataPermission4SuperApp();
        if (CollectionUtil.isEmpty(dataPermissionDto.getShopIdSet())) {
            allTreeOutput.setChildren(List.of());

            SuperOrganizationTreeExpandSelfResponseVo responseVo =
                    JSONUtil.toBean(JSONUtil.toJsonStr(allTreeOutput), SuperOrganizationTreeExpandSelfResponseVo.class);
            responseVo.setShopNumber(0);
            return responseVo;
        }

        //获取当前门店关联的组织信息
        IamOrganizationShopRelationQueryListInputDto queryListInputDto = new IamOrganizationShopRelationQueryListInputDto();
        queryListInputDto.setOrganizationType(request.getType());
        queryListInputDto.setShopIdSet(dataPermissionDto.getShopIdSet());
        List<DataShopOrganizationRelationListOutputDto> relationListOutputDtoList = shopOrganizationRelationClient.listByCondition(queryListInputDto);

        //获取拥有权限的树
        IamOrganizationTreeSimpleOutputDto filterTreeOutput = TreeUtil.filterTree(allTreeOutput,
                relationListOutputDtoList.stream().map(DataShopOrganizationRelationListOutputDto::getOrganizationId).collect(Collectors.toSet()));

        if (null == filterTreeOutput) {
            return null;
        }

        // 使用Stream API进行转换
        Map<String, Set<String>> organizationIdShopIdSetmap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(relationListOutputDtoList)) {
            organizationIdShopIdSetmap = relationListOutputDtoList.stream()
                    .collect(Collectors.groupingBy(
                            DataShopOrganizationRelationListOutputDto::getOrganizationId,
                            Collectors.mapping(DataShopOrganizationRelationListOutputDto::getShopId,
                                    Collectors.toSet())));
        }

        //中间对象
        SuperOrganizationTreeExpandBo treeExpandBo = JSONUtil.toBean(JSONUtil.toJsonStr(filterTreeOutput), SuperOrganizationTreeExpandBo.class);
        setShopIdSetRecursively(treeExpandBo, organizationIdShopIdSetmap);
        postorderTraversalAndCountChildren(treeExpandBo);

        //返回
        return JSONUtil.toBean(JSONUtil.toJsonStr(treeExpandBo), SuperOrganizationTreeExpandSelfResponseVo.class);
    }

    private void setShopIdSetRecursively(SuperOrganizationTreeExpandBo root,
                                         Map<String, Set<String>> organizationIdShopIdSetmap) {
        if (root == null) {
            return;
        }
        // 获取当前节点对应的 organizationId
        String organizationId = root.getId();
        // 根据 organizationId 从映射中获取对应的 shopId 集合，并设置到当前节点的 shopIdSet 属性
        Set<String> shopIdSet = organizationIdShopIdSetmap.get(organizationId);
        if (shopIdSet != null) {
            root.setShopIdSet(shopIdSet);
        }

        // 递归处理子节点
        List<SuperOrganizationTreeExpandBo> children = root.getChildren();
        if (children != null && !children.isEmpty()) {
            for (SuperOrganizationTreeExpandBo child : children) {
                setShopIdSetRecursively(child, organizationIdShopIdSetmap);
            }
        }
    }

    private void postorderTraversalAndCountChildren(SuperOrganizationTreeExpandBo node) {
        if (node == null) {
            return;
        }

        for (SuperOrganizationTreeExpandBo child : node.getChildren()) {
            postorderTraversalAndCountChildren(child);
        }

        for (SuperOrganizationTreeExpandBo child : node.getChildren()) {
            node.getShopIdSet().addAll(child.getShopIdSet());
            child.setShopIdSet(null);
        }
        node.setShopNumber(node.getShopIdSet().size());
    }

}
