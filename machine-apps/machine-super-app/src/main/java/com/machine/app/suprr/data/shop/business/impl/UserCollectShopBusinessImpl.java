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
import com.machine.client.data.shop.dto.input.DataSuperShopCollectIdInputDto;
import com.machine.client.data.shop.dto.input.DataSuperShopListCollectShopInputDto;
import com.machine.client.data.shop.dto.output.DataShopListOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.starter.redis.cache.iam.RedisCacheIamDataPermission;
import com.machine.starter.redis.cache.iam.RedisCacheIamOrganization;
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
    private IDataUserCollectShopClient dataUserCollectShopClient;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public void collectShop(SuperShopCollectRequestVo request) {
        DataSuperShopCollectIdInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataSuperShopCollectIdInputDto.class);
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
        DataSuperShopCollectIdInputDto inputDto = new DataSuperShopCollectIdInputDto();
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
        DataSuperShopListCollectShopInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataSuperShopListCollectShopInputDto.class);
        DataPermissionDto dataPermissionDto = redisCacheIamDataPermission.dataPermission4SuperApp();
        if (CollectionUtil.isEmpty(dataPermissionDto.getShopIdSet())) {
            //数据权限为空，直接返回
            return new PageResponse<>(request.getCurrent(), request.getCurrent(), 0);
        } else {
            inputDto.setShopIdSet(dataPermissionDto.getShopIdSet());
        }

        //分页查询用户收藏信息
        inputDto.setUserId(AppContext.getContext().getUserId());
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
        if (CollectionUtil.isNotEmpty(request.getOrganizationIdSet())) {
            IamOrganizationTypeEnum organizationType = request.getOrganizationType();
            Set<String> organizationIdSet = dataPermissionDto.getOrganizationIdMap().get(organizationType);
            if (CollectionUtil.isNotEmpty(organizationIdSet) &&
                    CollectionUtil.isNotEmpty(request.getOrganizationIdSet())) {
                //递归查询所有子节点
                Set<String> requestOrganizationIdSet = organizationCache.recursionListSubIds(request.getOrganizationType(),request.getOrganizationIdSet());
                if (CollectionUtil.isEmpty(requestOrganizationIdSet)) {
                    return new PageResponse<>(request.getCurrent(), request.getCurrent(), 0);
                }

                //数据权限和入参的部门取交集
                Set<String> finallyOrganizationIdSet = organizationIdSet.stream()
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
        }

        if (CollectionUtil.isEmpty(finallyShopIdSet)) {
            //门店为空，直接返回
            return new PageResponse<>(request.getCurrent(), request.getCurrent(), 0);
        }

        //入参
        DataShopQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopQueryPageInputDto.class);
        inputDto.setShopIdSet(finallyShopIdSet);

        //分页查询
        PageResponse<DataShopListOutputDto> pageOutputDto = shopClient.selectPage(inputDto);
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
            Set<String> shopIdSet = pageResponse.getRecords().stream().map(SuperShopListSimpleResponseVo::getId).collect(Collectors.toSet());
            Map<String, String> shopIdMap = shopOrganizationRelationClient.mapByShopIdSet(
                    new DataShopMapByShopIdSetInputDto(request.getOrganizationType(), shopIdSet));
            if (CollectionUtil.isNotEmpty(shopIdMap)) {
                for (SuperShopListSimpleResponseVo vo : pageResponse.getRecords()) {
                    vo.setOrganizationId(shopIdMap.get(vo.getId()));
                }
            }
        }

        return pageResponse;
    }

}
