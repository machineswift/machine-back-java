package com.machine.app.suprr.data.area.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.suprr.data.area.business.ISuperAreaBusiness;
import com.machine.app.suprr.data.area.business.bo.SuperAreaTreeExpandSelfBo;
import com.machine.app.suprr.data.area.controller.vo.request.SuperAreaTreeRequestVo;
import com.machine.app.suprr.data.area.controller.vo.response.SuperAreaTreeExpandSelfResponseVo;
import com.machine.app.suprr.data.area.controller.vo.response.SupperAreaTreeSimpleResponseVo;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.dto.output.DataShopDetailOutputDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.RedisCacheDataArea;
import com.machine.starter.redis.cache.RedisCacheIamDataPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SuperAreaBusinessImpl implements ISuperAreaBusiness {

    @Autowired
    private RedisCacheDataArea redisCacheDataArea;

    @Autowired
    private RedisCacheIamDataPermission redisCacheIamDataPermission;

    @Autowired
    private IDataShopClient shopClient;

    @Override
    public DataAreaTreeOutputDto treeSelfSimple(SuperAreaTreeRequestVo request) {
        DataAreaTreeOutputDto allTreeOutput = redisCacheDataArea.tree(request.getCountryCode());
        //数据权限
        DataPermissionDto dataPermissionDto = redisCacheIamDataPermission.dataPermission4SuperApp();
        if (CollectionUtil.isEmpty(dataPermissionDto.getShopIdSet())) {
            allTreeOutput.setChildren(List.of());
            return allTreeOutput;
        }

        //获取门店关联的区编码
        List<String> areaCodeList = shopClient.listAreaCodeByShopIdSet(new IdSetRequest(dataPermissionDto.getShopIdSet()));

        //获取拥有权限的树
        return TreeUtil.filterTree(allTreeOutput, new HashSet<>(areaCodeList));
    }

    @Override
    public SuperAreaTreeExpandSelfResponseVo treeSelfExpand(SuperAreaTreeRequestVo request) {
        DataAreaTreeOutputDto allTreeOutput = redisCacheDataArea.tree(request.getCountryCode());
        //数据权限
        DataPermissionDto dataPermissionDto = redisCacheIamDataPermission.dataPermission4SuperApp();
        if (CollectionUtil.isEmpty(dataPermissionDto.getShopIdSet())) {
            allTreeOutput.setChildren(List.of());
            SuperAreaTreeExpandSelfResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(allTreeOutput),
                    SuperAreaTreeExpandSelfResponseVo.class);
            responseVo.setShopNumber(0);
            return responseVo;
        }

        //获取门店关联的区编码
        List<DataShopDetailOutputDto> shopSimpleOutputDtoList =
                shopClient.listByIdSet(new IdSetRequest(dataPermissionDto.getShopIdSet()));
        if (CollectionUtil.isEmpty(shopSimpleOutputDtoList)) {
            allTreeOutput.setChildren(List.of());
            SuperAreaTreeExpandSelfResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(allTreeOutput),
                    SuperAreaTreeExpandSelfResponseVo.class);
            responseVo.setShopNumber(0);
            return responseVo;
        }

        //获取拥有权限的树
        Set<String> areaCodeSet = shopSimpleOutputDtoList.stream().map(DataShopDetailOutputDto::getAreaCode).collect(Collectors.toSet());
        DataAreaTreeOutputDto filterTreeOutput = TreeUtil.filterTree(allTreeOutput, areaCodeSet);

        // 使用Stream API进行转换
        Map<String, Set<String>> areaCodeShopIdSetmap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(shopSimpleOutputDtoList)) {
            areaCodeShopIdSetmap = shopSimpleOutputDtoList.stream()
                    .collect(Collectors.groupingBy(
                            DataShopDetailOutputDto::getAreaCode,
                            Collectors.mapping(DataShopDetailOutputDto::getId,
                                    Collectors.toSet())));
        }

        //中间对象
        SuperAreaTreeExpandSelfBo treeExpandBo = JSONUtil.toBean(JSONUtil.toJsonStr(filterTreeOutput), SuperAreaTreeExpandSelfBo.class);
        setShopIdSetRecursively(treeExpandBo, areaCodeShopIdSetmap);
        postorderTraversalAndCountChildren(treeExpandBo);

        //返回
        return JSONUtil.toBean(JSONUtil.toJsonStr(treeExpandBo), SuperAreaTreeExpandSelfResponseVo.class);
    }

    @Override
    public SupperAreaTreeSimpleResponseVo treeAllSimple(SuperAreaTreeRequestVo request) {
        DataAreaTreeOutputDto areaTree = redisCacheDataArea.tree(request.getCountryCode());
        if (areaTree == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(areaTree), SupperAreaTreeSimpleResponseVo.class);
    }

    private void setShopIdSetRecursively(SuperAreaTreeExpandSelfBo root,
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
        List<SuperAreaTreeExpandSelfBo> children = root.getChildren();
        if (children != null && !children.isEmpty()) {
            for (SuperAreaTreeExpandSelfBo child : children) {
                setShopIdSetRecursively(child, organizationIdShopIdSetmap);
            }
        }
    }

    private void postorderTraversalAndCountChildren(SuperAreaTreeExpandSelfBo node) {
        if (node == null) {
            return;
        }

        for (SuperAreaTreeExpandSelfBo child : node.getChildren()) {
            postorderTraversalAndCountChildren(child);
        }

        for (SuperAreaTreeExpandSelfBo child : node.getChildren()) {
            node.getShopIdSet().addAll(child.getShopIdSet());
            child.setShopIdSet(null);
        }
        node.setShopNumber(node.getShopIdSet().size());
    }
}
