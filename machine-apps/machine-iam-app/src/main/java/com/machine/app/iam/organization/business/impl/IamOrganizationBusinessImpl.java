package com.machine.app.iam.organization.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.organization.business.IIamOrganizationBusiness;
import com.machine.app.iam.organization.business.bo.IamOrganizationExpandTreeBo;
import com.machine.app.iam.organization.controller.vo.request.*;
import com.machine.app.iam.organization.controller.vo.response.*;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.data.shop.dto.input.DataShopNotBindOrganizationInputDto;
import com.machine.client.data.shop.dto.input.DataShopQueryListAllInputDto;
import com.machine.client.data.shop.dto.output.DataShopListSimpleOutputDto;
import com.machine.client.data.shop.dto.output.DataShopOrganizationRelationListOutputDto;
import com.machine.client.iam.organization.IIamOrganizationClient;
import com.machine.client.iam.user.IIamUserOrganizationRelationClient;
import com.machine.client.iam.organization.dto.input.*;
import com.machine.client.iam.organization.dto.output.*;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.input.IamDataUserNotBindOrganizationInputDto;
import com.machine.client.iam.user.dto.output.IamUserOrganizationRelationOutputDto;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.SEPARATOR_COLON;
import static com.machine.sdk.common.constant.CommonIamConstant.Organization.ORGANIZATION_VIRTUAL_NODE;
import static com.machine.sdk.common.constant.CommonIamConstant.Organization.ORGANIZATION_VIRTUAL_NODE_NAME;
import static com.machine.sdk.common.constant.ContextConstant.SYSTEM_USER_ID;
import static com.machine.sdk.common.constant.ContextConstant.SYSTEM_USER_NAME;

@Slf4j
@Component
public class IamOrganizationBusinessImpl implements IIamOrganizationBusiness {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataShopClient shopClient;

    @Autowired
    private IIamOrganizationClient organizationClient;

    @Autowired
    private IIamUserOrganizationRelationClient userOrganizationRelationClient;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public String create(IamOrganizationCreateRequestVo request) {
        request.setName(request.getName().trim());

        IamOrganizationCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamOrganizationCreateInputDto.class);
        return organizationClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        organizationClient.delete(request);
    }

    @Override
    public void update(IamOrganizationUpdateRequestVo request) {
        request.setName(request.getName().trim());

        IamOrganizationUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamOrganizationUpdateInputDto.class);
        organizationClient.update(inputDto);
    }

    @Override
    public void updateParent(IamOrganizationUpdateParentRequestVo request) {
        IamOrganizationUpdateParentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamOrganizationUpdateParentInputDto.class);
        organizationClient.updateParent(inputDto);
    }

    @Override
    public IamOrganizationDetailResponseVo detail(IdRequest request) {
        IamOrganizationDetailOutputDto outputDto = organizationClient.detail(request);
        if (null == outputDto) {
            return null;
        }
        return getDetailResponse(outputDto);
    }

    @Override
    public IamOrganizationTreeSimpleOutputDto treeSimple(IamOrganizationQueryTreeRequestVo request) {
        return organizationCache.treeAllSimple(request.getType());
    }

    @Override
    public IamOrganizationExpandTreeResponseVo treeExpand(IamOrganizationQueryTreeRequestVo request) {
        String typeName = request.getType().getName();
        List<IamOrganizationListOutputDto> outputDtoList = organizationClient.listAllByType(request.getType());
        Set<String> organizationIdSet = outputDtoList.stream().map(IamOrganizationListOutputDto::getId).collect(Collectors.toSet());

        //查询组织关联的门店信息
        List<DataShopOrganizationRelationListOutputDto> shopOrganizationRelationListOutputDtoList = shopOrganizationRelationClient
                .listByOrganizationIdSet(new IdSetRequest(organizationIdSet));

        //查询组织关联的用户信息
        List<IamUserOrganizationRelationOutputDto> userOrganizationRelationOutputDtoList = userOrganizationRelationClient
                .listByOrganizationIdSet(new IdSetRequest(organizationIdSet));

        //组装中间对象信息
        List<IamOrganizationExpandTreeBo> expandTreeBoList = JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), IamOrganizationExpandTreeBo.class);

        Map<String, IamOrganizationExpandTreeBo> organizationMap = expandTreeBoList.stream()
                .collect(Collectors.toMap(IamOrganizationExpandTreeBo::getId, p -> p));

        {//门店数量
            for (DataShopOrganizationRelationListOutputDto dto : shopOrganizationRelationListOutputDtoList) {
                IamOrganizationExpandTreeBo bo = organizationMap.get(dto.getOrganizationId());
                bo.getShopIdSet().add(dto.getShopId());
                bo.setShopNumber(bo.getShopIdSet().size());
            }
        }

        { //用户数量
            for (IamUserOrganizationRelationOutputDto dto : userOrganizationRelationOutputDtoList) {
                IamOrganizationExpandTreeBo bo = organizationMap.get(dto.getOrganizationId());
                bo.getUserIdSet().add(dto.getUserId());
                bo.setUserNumber(bo.getUserIdSet().size());
            }
        }

        {//组织数量
            for (IamOrganizationExpandTreeBo bo : expandTreeBoList) {
                bo.getOrganizationIdSet().add(bo.getId());
                bo.setOrganizationNumber(bo.getOrganizationIdSet().size());
            }
        }

        { //填充修改人创建人信息
            Set<String> userIdSet = expandTreeBoList.stream().map(IamOrganizationExpandTreeBo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(expandTreeBoList.stream().map(IamOrganizationExpandTreeBo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (IamOrganizationExpandTreeBo bo : expandTreeBoList) {
                bo.setCreateName(userSimpleDetailMap.get(bo.getCreateBy()).getName());
                bo.setUpdateName(userSimpleDetailMap.get(bo.getUpdateBy()).getName());
            }
        }

        //组装成中间对象树
        IamOrganizationExpandTreeBo expandTreeBo = TreeUtil.buildTree(expandTreeBoList).getFirst();

        //tree 后序递归累计子节点的门店和用户数据并计算出门店和用户数量
        postorderTraversalAndCountChildren(expandTreeBo);

        { //添加【未分配】
            IamOrganizationExpandTreeBo virtualExpandTreeBo = new IamOrganizationExpandTreeBo();
            virtualExpandTreeBo.setId(typeName + SEPARATOR_COLON + ORGANIZATION_VIRTUAL_NODE);
            virtualExpandTreeBo.setParentId(expandTreeBo.getId());
            virtualExpandTreeBo.setName(ORGANIZATION_VIRTUAL_NODE_NAME);
            virtualExpandTreeBo.setSort(Long.MAX_VALUE);
            virtualExpandTreeBo.setCode(typeName + SEPARATOR_COLON + ORGANIZATION_VIRTUAL_NODE.toUpperCase());
            virtualExpandTreeBo.setCreateName(SYSTEM_USER_NAME);
            virtualExpandTreeBo.setCreateBy(SYSTEM_USER_ID);
            virtualExpandTreeBo.setCreateTime(System.currentTimeMillis());
            virtualExpandTreeBo.setUpdateName(SYSTEM_USER_NAME);
            virtualExpandTreeBo.setUpdateBy(SYSTEM_USER_ID);
            virtualExpandTreeBo.setUpdateTime(System.currentTimeMillis());

            //查询未绑定组织的门店数量
            int shopNumber = shopClient.countNotBindOrganization(
                    new DataShopNotBindOrganizationInputDto(request.getType()));
            virtualExpandTreeBo.setShopNumber(shopNumber);

            //查询未绑定组织的用户数量
            int userNumber = userClient.countNotBindOrganization(new IamDataUserNotBindOrganizationInputDto(request.getType()));
            virtualExpandTreeBo.setUserNumber(userNumber);


            if (CollectionUtil.isEmpty(expandTreeBoList)) {
                expandTreeBo.setChildren(List.of(virtualExpandTreeBo));
            } else {
                expandTreeBo.getChildren().addFirst(virtualExpandTreeBo);
            }
        }

        return JSONUtil.toBean(JSONUtil.toJsonStr(expandTreeBo), IamOrganizationExpandTreeResponseVo.class);
    }

    @Override
    public IamOrganizationWithShopTreeResponseVo treeExpandWithShop(IamOrganizationQueryTreeRequestVo request) {
        String typeName = request.getType().getName();
        List<IamOrganizationListOutputDto> outputDtoList = organizationClient.listAllByType(request.getType());
        Set<String> organizationIdSet = outputDtoList.stream().map(IamOrganizationListOutputDto::getId).collect(Collectors.toSet());

        //查询组织关联的门店信息
        List<DataShopOrganizationRelationListOutputDto> shopOrganizationRelationListOutputDtoList = shopOrganizationRelationClient
                .listByOrganizationIdSet(new IdSetRequest(organizationIdSet));

        //查询所有门店信息
        List<DataShopListSimpleOutputDto> shopListOutputDtoList = shopClient.listAll(new DataShopQueryListAllInputDto());
        Map<String, DataShopListSimpleOutputDto> shopOutputDtoMap = shopListOutputDtoList.stream()
                .collect(Collectors.toMap(DataShopListSimpleOutputDto::getId, dto -> dto));

        //组装中间对象信息
        List<IamOrganizationExpandTreeBo> expandTreeBoList = JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), IamOrganizationExpandTreeBo.class);

        Map<String, IamOrganizationExpandTreeBo> organizationMap = expandTreeBoList.stream()
                .collect(Collectors.toMap(IamOrganizationExpandTreeBo::getId, p -> p));

        {//门店数量
            for (DataShopOrganizationRelationListOutputDto dto : shopOrganizationRelationListOutputDtoList) {
                IamOrganizationExpandTreeBo bo = organizationMap.get(dto.getOrganizationId());
                bo.getShopIdSet().add(dto.getShopId());
                bo.setShopNumber(bo.getShopIdSet().size());
            }

            //门店信息
            for (IamOrganizationExpandTreeBo expandTreeBo : expandTreeBoList) {
                Set<String> shopIdSet = expandTreeBo.getShopIdSet();
                if (CollectionUtil.isNotEmpty(shopIdSet)) {
                    List<DataShopListSimpleOutputDto> bindShopList = new ArrayList<>(shopIdSet.size());
                    for (String shopId : shopIdSet) {
                        bindShopList.add(shopOutputDtoMap.get(shopId));
                    }
                    expandTreeBo.setBindShopList(bindShopList);
                }
            }
        }

        {//组织数量
            for (IamOrganizationExpandTreeBo bo : expandTreeBoList) {
                bo.getOrganizationIdSet().add(bo.getId());
                bo.setOrganizationNumber(bo.getOrganizationIdSet().size());
            }
        }

        { //填充修改人创建人信息
            Set<String> userIdSet = expandTreeBoList.stream().map(IamOrganizationExpandTreeBo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(expandTreeBoList.stream().map(IamOrganizationExpandTreeBo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (IamOrganizationExpandTreeBo bo : expandTreeBoList) {
                bo.setCreateName(userSimpleDetailMap.get(bo.getCreateBy()).getName());
                bo.setUpdateName(userSimpleDetailMap.get(bo.getUpdateBy()).getName());
            }
        }

        //组装成中间对象树
        IamOrganizationExpandTreeBo expandTreeBo = TreeUtil.buildTree(expandTreeBoList).getFirst();

        //tree 后序递归累计子节点的门店和用户数据并计算出门店和用户数量
        postorderTraversalAndCountChildren(expandTreeBo);

        { //添加【未分配】
            IamOrganizationExpandTreeBo virtualExpandTreeBo = new IamOrganizationExpandTreeBo();
            virtualExpandTreeBo.setId(typeName + SEPARATOR_COLON + ORGANIZATION_VIRTUAL_NODE);
            virtualExpandTreeBo.setParentId(expandTreeBo.getId());
            virtualExpandTreeBo.setName(ORGANIZATION_VIRTUAL_NODE_NAME);
            virtualExpandTreeBo.setSort(Long.MAX_VALUE);
            virtualExpandTreeBo.setCode(typeName + SEPARATOR_COLON + ORGANIZATION_VIRTUAL_NODE.toUpperCase());
            virtualExpandTreeBo.setCreateName(SYSTEM_USER_NAME);
            virtualExpandTreeBo.setCreateBy(SYSTEM_USER_ID);
            virtualExpandTreeBo.setCreateTime(System.currentTimeMillis());
            virtualExpandTreeBo.setUpdateBy(SYSTEM_USER_NAME);
            virtualExpandTreeBo.setUpdateBy(SYSTEM_USER_ID);
            virtualExpandTreeBo.setUpdateTime(System.currentTimeMillis());

            //查询未绑定组织的门店Id
            List<String> shopIdSet = shopClient.listNotBindOrganization(
                    new DataShopNotBindOrganizationInputDto(request.getType()));
            if (CollectionUtil.isNotEmpty(shopIdSet)) {
                List<DataShopListSimpleOutputDto> bindShopList = new ArrayList<>(shopIdSet.size());
                for (String shopId : shopIdSet) {
                    bindShopList.add(shopOutputDtoMap.get(shopId));
                }
                virtualExpandTreeBo.setBindShopList(bindShopList);
            }

            if (CollectionUtil.isEmpty(expandTreeBoList)) {
                expandTreeBo.setChildren(List.of(virtualExpandTreeBo));
            } else {
                expandTreeBo.getChildren().addFirst(virtualExpandTreeBo);
            }
        }

        return JSONUtil.toBean(JSONUtil.toJsonStr(expandTreeBo), IamOrganizationWithShopTreeResponseVo.class);
    }


    private void postorderTraversalAndCountChildren(IamOrganizationExpandTreeBo node) {
        if (node == null) {
            return;
        }

        for (IamOrganizationExpandTreeBo child : node.getChildren()) {
            postorderTraversalAndCountChildren(child);
        }

        for (IamOrganizationExpandTreeBo child : node.getChildren()) {
            node.getOrganizationIdSet().addAll(child.getOrganizationIdSet());
            child.setOrganizationIdSet(null);

            node.getShopIdSet().addAll(child.getShopIdSet());
            child.setShopIdSet(null);

            node.getUserIdSet().addAll(child.getUserIdSet());
            child.setUserIdSet(null);
        }
        node.setOrganizationNumber(node.getOrganizationIdSet().size());
        node.setShopNumber(node.getShopIdSet().size());
        node.setUserNumber(node.getUserIdSet().size());
    }


    private IamOrganizationDetailResponseVo getDetailResponse(IamOrganizationDetailOutputDto outputDto) {
        String organizationId = outputDto.getId();

        //递归查询id
        Set<String> organizationIdSet = new HashSet<>();
        organizationIdSet.add(organizationId);
        organizationIdSet.addAll(organizationCache.recursionListSubId(outputDto.getType(), organizationId));

        //查询组织关联的门店信息
        List<DataShopOrganizationRelationListOutputDto> shopOrganizationRelationListOutputDtoList = shopOrganizationRelationClient
                .listByOrganizationIdSet(new IdSetRequest(organizationIdSet));

        //查询组织关联的用户信息;
        List<IamUserOrganizationRelationOutputDto> userOrganizationRelationOutputDtoList = userOrganizationRelationClient
                .listByOrganizationIdSet(new IdSetRequest(organizationIdSet));

        //填充修改人创建人信息
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(outputDto.getCreateBy());
        userIdSet.add(outputDto.getUpdateBy());
        Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

        IamOrganizationDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), IamOrganizationDetailResponseVo.class);
        responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());

        //组织下门店数量
        if (CollectionUtil.isEmpty(shopOrganizationRelationListOutputDtoList)) {
            responseVo.setShopNumber(0);
        } else {
            Set<String> shopIdSet = shopOrganizationRelationListOutputDtoList.stream().map(DataShopOrganizationRelationListOutputDto::getShopId).collect(Collectors.toSet());
            responseVo.setShopNumber(shopIdSet.size());
        }

        //组织下人员数量
        if (CollectionUtil.isEmpty(userOrganizationRelationOutputDtoList)) {
            responseVo.setUserNumber(0);
        } else {
            Set<String> userIdSetTemp = userOrganizationRelationOutputDtoList.stream().map(IamUserOrganizationRelationOutputDto::getUserId).collect(Collectors.toSet());
            responseVo.setUserNumber(userIdSetTemp.size());
        }

        //组织下的组织数量
        responseVo.setOrganizationNumber(organizationIdSet.size());
        return responseVo;
    }
}
