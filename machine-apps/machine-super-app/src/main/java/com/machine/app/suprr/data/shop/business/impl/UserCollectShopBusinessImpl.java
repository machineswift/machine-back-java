package com.machine.app.suprr.data.shop.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.suprr.data.shop.business.IUserCollectShopBusiness;
import com.machine.app.suprr.data.shop.controller.vo.request.*;
import com.machine.app.suprr.data.shop.controller.vo.response.SuperShopListSimpleResponseVo;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.data.shop.IDataUserCollectShopClient;
import com.machine.client.data.shop.dto.input.DataShopMapByShopIdSetInputDto;
import com.machine.client.data.shop.dto.input.DataShopQueryPageInputDto;
import com.machine.client.data.shop.dto.input.SuperShopCollectIdInputDto;
import com.machine.client.data.shop.dto.input.SuperShopListCollectShopInputDto;
import com.machine.client.data.shop.dto.output.DataShopListOutputDto;
import com.machine.client.iam.organization.IIamOrganizationClient;
import com.machine.client.iam.organization.dto.output.IamOrganizationDetailOutputDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.starter.redis.cache.RedisCacheIamDataPermission;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserCollectShopBusinessImpl implements IUserCollectShopBusiness {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private RedisCacheIamDataPermission redisCacheIamDataPermission;

    @Autowired
    private IDataShopClient shopClient;

    @Autowired
    private IIamOrganizationClient organizationClient;

    @Autowired
    private IDataUserCollectShopClient dataUserCollectShopClient;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public void collectShop(SuperShopCollectRequestVo request) {
        SuperShopCollectIdInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), SuperShopCollectIdInputDto.class);
        dataUserCollectShopClient.collectShop(inputDto);
    }

    @Override
    public void collectShopByCondition(SuperShopCollectByConditionRequestVo request) {
        SuperShopPageSelfShopRequestVo pageRequest = JSONUtil.toBean(JSONUtil.toJsonStr(request), SuperShopPageSelfShopRequestVo.class);
        pageRequest.setCurrent(1L);
        pageRequest.setSize((long) Integer.MAX_VALUE);
        PageResponse<SuperShopListSimpleResponseVo> pageResponse = pageSelfShop(pageRequest);
        if (CollectionUtil.isEmpty(pageResponse.getRecords())) {
            return;
        }

        //收藏门店
        SuperShopCollectIdInputDto inputDto = new SuperShopCollectIdInputDto();
        Set<String> shopIdSet = pageResponse.getRecords().stream().map(SuperShopListSimpleResponseVo::getId).collect(Collectors.toSet());
        if (request.getCollected()) {
            inputDto.setCollectShopIdSet(shopIdSet);
        } else {
            inputDto.setUnCollectShopIdSet(shopIdSet);
        }
        dataUserCollectShopClient.collectShop(inputDto);
    }

    @Override
    public Integer number(SuperShopNumberRequestVo request) {
        DataPermissionDto dataPermissionDto = redisCacheIamDataPermission.dataPermission4SuperApp();
        if (null == dataPermissionDto.getShopIdSet()) {
            return 0;
        }
        return dataPermissionDto.getShopIdSet().size();
    }

    @Override
    public PageResponse<SuperShopListSimpleResponseVo> pageCollectShop(SuperShopListCollectShopRequestVo request) {
        SuperShopListCollectShopInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), SuperShopListCollectShopInputDto.class);
        DataPermissionDto dataPermissionDto = redisCacheIamDataPermission.dataPermission4SuperApp();
        if (CollectionUtil.isEmpty(dataPermissionDto.getShopIdSet())) {
            //数据权限为空，直接返回
            return new PageResponse<>(request.getCurrent(), request.getCurrent(), 0);
        } else {
            inputDto.setShopIdSet(dataPermissionDto.getShopIdSet());
        }

        //分页查询用户收藏信息
        PageResponse<DataShopListOutputDto> pageOutputDto = dataUserCollectShopClient.pageCollectShop(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(
                    pageOutputDto.getCurrent(),
                    pageOutputDto.getSize(),
                    pageOutputDto.getTotal());
        }
        PageResponse<SuperShopListSimpleResponseVo> pageResponse = new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), SuperShopListSimpleResponseVo.class));

        //是否收藏
        for (SuperShopListSimpleResponseVo vo : pageResponse.getRecords()) {
            vo.setCollected(Boolean.TRUE);
        }
        return pageResponse;
    }

    @Override
    public PageResponse<SuperShopListSimpleResponseVo> pageSelfShop(SuperShopPageSelfShopRequestVo request) {
        DataPermissionDto dataPermissionDto = redisCacheIamDataPermission.dataPermission4SuperApp();
        if (CollectionUtil.isEmpty(dataPermissionDto.getShopIdSet())) {
            //数据权限为空，直接返回
            return new PageResponse<>(request.getCurrent(), request.getCurrent(), 0);
        }

        Set<String> finallyShopIdSet = dataPermissionDto.getShopIdSet();

        //处理组织参数
        if (CollectionUtil.isNotEmpty(dataPermissionDto.getOrganizationIdSet()) &&
                CollectionUtil.isNotEmpty(request.getOrganizationIdSet())) {
            //递归查询所有子节点
            Set<String> requestOrganizationIdSet = organizationCache.recursionListSubIds(request.getOrganizationIdSet());
            if (CollectionUtil.isEmpty(requestOrganizationIdSet)) {
                return new PageResponse<>(request.getCurrent(), request.getCurrent(), 0);
            }

            //数据权限和入参的部门取交集
            Set<String> finallyOrganizationIdSet = dataPermissionDto.getOrganizationIdSet().stream()
                    .filter(requestOrganizationIdSet::contains)
                    .collect(Collectors.toSet());
            if (CollectionUtil.isEmpty(finallyOrganizationIdSet)) {
                return new PageResponse<>(request.getCurrent(), request.getCurrent(), 0);
            }

            //查询组织关联的门店id
            List<String> dbShopIdSet = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(
                    new IdSetRequest(finallyOrganizationIdSet));
            if (CollectionUtil.isEmpty(dbShopIdSet)) {
                return new PageResponse<>(request.getCurrent(), request.getCurrent(), 0);
            }

            //数据权限与入参的门店取交集
            finallyShopIdSet = dataPermissionDto.getShopIdSet().stream()
                    .filter(dbShopIdSet::contains)
                    .collect(Collectors.toSet());
            if (CollectionUtil.isEmpty(finallyShopIdSet)) {
                return new PageResponse<>(request.getCurrent(), request.getCurrent(), 0);
            }
        }

        if (CollectionUtil.isEmpty(finallyShopIdSet)) {
            //门店为空，直接返回
            return new PageResponse<>(request.getCurrent(), request.getCurrent(), 0);
        }

        //入参
        DataShopQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopQueryPageInputDto.class);
        inputDto.setShopIdSet(finallyShopIdSet);

        //分页查询
        PageResponse<DataShopListOutputDto> pageOutputDto = shopClient.page(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(
                    pageOutputDto.getCurrent(),
                    pageOutputDto.getSize(),
                    pageOutputDto.getTotal());
        }

        PageResponse<SuperShopListSimpleResponseVo> pageResponse = new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), SuperShopListSimpleResponseVo.class));


        //是否收藏
        Set<String> responseShopIdSet = pageResponse.getRecords().stream()
                .map(SuperShopListSimpleResponseVo::getId).collect(Collectors.toSet());
        Set<String> collectShopIdSet = new HashSet<>(dataUserCollectShopClient
                .listCollectedIdByShopIdSet(new IdSetRequest(responseShopIdSet)));
        for (SuperShopListSimpleResponseVo vo : pageResponse.getRecords()) {
            if (collectShopIdSet.contains(vo.getId())) {
                vo.setCollected(Boolean.TRUE);
            } else {
                vo.setCollected(Boolean.FALSE);
            }
        }

        //根据组织查询门店时加上门店关联的组织Id
        if (CollectionUtil.isNotEmpty(request.getOrganizationIdSet())) {
            //随机取一个查询组织信息，用于获取组织类型（本接口不会多个业务组织一起查询）
            String organizationId = request.getOrganizationIdSet().iterator().next();
            IamOrganizationDetailOutputDto outputDto = organizationClient.detail(new IdRequest(organizationId));
            Set<String> shopIdSet = pageResponse.getRecords().stream().map(SuperShopListSimpleResponseVo::getId).collect(Collectors.toSet());
            Map<String, String> shopIdMap = shopOrganizationRelationClient.mapByShopIdSet(
                    new DataShopMapByShopIdSetInputDto(outputDto.getType(), shopIdSet));
            if (CollectionUtil.isNotEmpty(shopIdMap)) {
                for (SuperShopListSimpleResponseVo vo : pageResponse.getRecords()) {
                    vo.setOrganizationId(shopIdMap.get(vo.getId()));
                }
            }
        }

        return pageResponse;
    }

}
