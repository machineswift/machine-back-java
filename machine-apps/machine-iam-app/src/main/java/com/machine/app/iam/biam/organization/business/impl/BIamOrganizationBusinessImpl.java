package com.machine.app.iam.biam.organization.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.organization.business.IBIamOrganizationBusiness;
import com.machine.app.iam.biam.organization.business.bo.BIamOrganizationExpandTreeBo;
import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationCreateRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationQueryTreeRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationUpdateParentRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationUpdateRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.response.BIamOrganizationDetailResponseVo;
import com.machine.app.iam.biam.organization.controller.vo.response.BIamOrganizationExpandTreeResponseVo;
import com.machine.app.iam.biam.organization.controller.vo.response.BIamOrganizationWithShopTreeResponseVo;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.data.shop.dto.input.DataShopNotBindOrganizationInputDto;
import com.machine.client.data.shop.dto.input.DataShopQueryListAllInputDto;
import com.machine.client.data.shop.dto.output.DataShopListSimpleOutputDto;
import com.machine.client.data.shop.dto.output.DataShopOrganizationRelationListOutputDto;
import com.machine.client.iam.biam.organization.IBIamOrganizationClient;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationCreateInputDto;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationUpdateInputDto;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationUpdateParentInputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationDetailOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationListOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationTreeSimpleOutputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.IBIamUserOrganizationRelationClient;
import com.machine.client.iam.biam.user.dto.input.BIamDataUserNotBindOrganizationInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserOrganizationRelationOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.starter.redis.cache.biam.RedisBIamOrganizationCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonConstant.SEPARATOR_COLON;
import static com.machine.sdk.base.constant.CommonBIamConstant.Organization.DATA_ORGANIZATION_VIRTUAL_NODE;
import static com.machine.sdk.base.constant.CommonBIamConstant.Organization.DATA_ORGANIZATION_VIRTUAL_NODE_NAME;
import static com.machine.sdk.base.constant.ContextConstant.SYSTEM_USER_ID;
import static com.machine.sdk.base.constant.ContextConstant.SYSTEM_USER_NAME;

@Slf4j
@Component
public class BIamOrganizationBusinessImpl implements IBIamOrganizationBusiness {

    @Autowired
    private RedisBIamOrganizationCache organizationCache;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IDataShopClient shopClient;

    @Autowired
    private IBIamOrganizationClient organizationClient;

    @Autowired
    private IBIamUserOrganizationRelationClient userOrganizationRelationClient;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public String create(BIamOrganizationCreateRequestVo request) {
        request.setName(request.getName().trim());

        BIamOrganizationCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamOrganizationCreateInputDto.class);
        return organizationClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        organizationClient.delete(request);
    }

    @Override
    public void update(BIamOrganizationUpdateRequestVo request) {
        request.setName(request.getName().trim());

        BIamOrganizationUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamOrganizationUpdateInputDto.class);
        organizationClient.update(inputDto);
    }

    @Override
    public void updateParent(BIamOrganizationUpdateParentRequestVo request) {
        BIamOrganizationUpdateParentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamOrganizationUpdateParentInputDto.class);
        organizationClient.updateParent(inputDto);
    }

    @Override
    public BIamOrganizationDetailResponseVo detail(IdRequest request) {
        BIamOrganizationDetailOutputDto outputDto = organizationClient.detail(request);
        if (null == outputDto) {
            return null;
        }
        return getDetailResponse(outputDto);
    }

    @Override
    public BIamOrganizationTreeSimpleOutputDto treeSimple(BIamOrganizationQueryTreeRequestVo request) {
        return organizationCache.treeAllSimple(request.getType());
    }

    @Override
    public BIamOrganizationExpandTreeResponseVo treeExpand(BIamOrganizationQueryTreeRequestVo request) {
        String typeName = request.getType().getName();
        List<BIamOrganizationListOutputDto> outputDtoList = organizationClient.listAllByType(request.getType());
        Set<String> organizationIdSet = outputDtoList.stream().map(BIamOrganizationListOutputDto::getId).collect(Collectors.toSet());

        //查询组织关联的门店信息
        List<DataShopOrganizationRelationListOutputDto> shopOrganizationRelationListOutputDtoList = shopOrganizationRelationClient
                .listByOrganizationIdSet(new IdSetRequest(organizationIdSet));

        //查询组织关联的用户信息
        List<BIamUserOrganizationRelationOutputDto> userOrganizationRelationOutputDtoList = userOrganizationRelationClient
                .listByOrganizationIdSet(new IdSetRequest(organizationIdSet));

        //组装中间对象信息
        List<BIamOrganizationExpandTreeBo> expandTreeBoList = JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), BIamOrganizationExpandTreeBo.class);

        Map<String, BIamOrganizationExpandTreeBo> organizationMap = expandTreeBoList.stream()
                .collect(Collectors.toMap(BIamOrganizationExpandTreeBo::getId, p -> p));

        {//门店数量
            for (DataShopOrganizationRelationListOutputDto dto : shopOrganizationRelationListOutputDtoList) {
                BIamOrganizationExpandTreeBo bo = organizationMap.get(dto.getOrganizationId());
                bo.getShopIdSet().add(dto.getShopId());
                bo.setShopNumber(bo.getShopIdSet().size());
            }
        }

        { //用户数量
            for (BIamUserOrganizationRelationOutputDto dto : userOrganizationRelationOutputDtoList) {
                BIamOrganizationExpandTreeBo bo = organizationMap.get(dto.getOrganizationId());
                bo.getUserIdSet().add(dto.getUserId());
                bo.setUserNumber(bo.getUserIdSet().size());
            }
        }

        {//组织数量
            for (BIamOrganizationExpandTreeBo bo : expandTreeBoList) {
                bo.getOrganizationIdSet().add(bo.getId());
                bo.setOrganizationNumber(bo.getOrganizationIdSet().size());
            }
        }

        { //填充修改人创建人信息
            Set<String> userIdSet = expandTreeBoList.stream().map(BIamOrganizationExpandTreeBo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(expandTreeBoList.stream().map(BIamOrganizationExpandTreeBo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (BIamOrganizationExpandTreeBo bo : expandTreeBoList) {
                bo.setCreateName(userSimpleDetailMap.get(bo.getCreateBy()).getName());
                bo.setUpdateName(userSimpleDetailMap.get(bo.getUpdateBy()).getName());
            }
        }

        //组装成中间对象树
        BIamOrganizationExpandTreeBo expandTreeBo = TreeUtil.buildTree(expandTreeBoList).getFirst();

        //tree 后序递归累计子节点的门店和用户数据并计算出门店和用户数量
        postorderTraversalAndCountChildren(expandTreeBo);

        { //添加【未分配】
            BIamOrganizationExpandTreeBo virtualExpandTreeBo = new BIamOrganizationExpandTreeBo();
            virtualExpandTreeBo.setId(typeName + SEPARATOR_COLON + DATA_ORGANIZATION_VIRTUAL_NODE);
            virtualExpandTreeBo.setParentId(expandTreeBo.getId());
            virtualExpandTreeBo.setName(DATA_ORGANIZATION_VIRTUAL_NODE_NAME);
            virtualExpandTreeBo.setSort(Long.MAX_VALUE);
            virtualExpandTreeBo.setCode(typeName + SEPARATOR_COLON + DATA_ORGANIZATION_VIRTUAL_NODE.toUpperCase());
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
            int userNumber = userClient.countNotBindOrganization(new BIamDataUserNotBindOrganizationInputDto(request.getType()));
            virtualExpandTreeBo.setUserNumber(userNumber);


            if (CollectionUtil.isEmpty(expandTreeBoList)) {
                expandTreeBo.setChildren(List.of(virtualExpandTreeBo));
            } else {
                expandTreeBo.getChildren().addFirst(virtualExpandTreeBo);
            }
        }

        return JSONUtil.toBean(JSONUtil.toJsonStr(expandTreeBo), BIamOrganizationExpandTreeResponseVo.class);
    }

    @Override
    public BIamOrganizationWithShopTreeResponseVo treeExpandWithShop(BIamOrganizationQueryTreeRequestVo request) {
        String typeName = request.getType().getName();
        List<BIamOrganizationListOutputDto> outputDtoList = organizationClient.listAllByType(request.getType());
        Set<String> organizationIdSet = outputDtoList.stream().map(BIamOrganizationListOutputDto::getId).collect(Collectors.toSet());

        //查询组织关联的门店信息
        List<DataShopOrganizationRelationListOutputDto> shopOrganizationRelationListOutputDtoList = shopOrganizationRelationClient
                .listByOrganizationIdSet(new IdSetRequest(organizationIdSet));

        //查询所有门店信息
        List<DataShopListSimpleOutputDto> shopListOutputDtoList = shopClient.listAll(new DataShopQueryListAllInputDto());
        Map<String, DataShopListSimpleOutputDto> shopOutputDtoMap = shopListOutputDtoList.stream()
                .collect(Collectors.toMap(DataShopListSimpleOutputDto::getId, dto -> dto));

        //组装中间对象信息
        List<BIamOrganizationExpandTreeBo> expandTreeBoList = JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), BIamOrganizationExpandTreeBo.class);

        Map<String, BIamOrganizationExpandTreeBo> organizationMap = expandTreeBoList.stream()
                .collect(Collectors.toMap(BIamOrganizationExpandTreeBo::getId, p -> p));

        {//门店数量
            for (DataShopOrganizationRelationListOutputDto dto : shopOrganizationRelationListOutputDtoList) {
                BIamOrganizationExpandTreeBo bo = organizationMap.get(dto.getOrganizationId());
                bo.getShopIdSet().add(dto.getShopId());
                bo.setShopNumber(bo.getShopIdSet().size());
            }

            //门店信息
            for (BIamOrganizationExpandTreeBo expandTreeBo : expandTreeBoList) {
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
            for (BIamOrganizationExpandTreeBo bo : expandTreeBoList) {
                bo.getOrganizationIdSet().add(bo.getId());
                bo.setOrganizationNumber(bo.getOrganizationIdSet().size());
            }
        }

        { //填充修改人创建人信息
            Set<String> userIdSet = expandTreeBoList.stream().map(BIamOrganizationExpandTreeBo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(expandTreeBoList.stream().map(BIamOrganizationExpandTreeBo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (BIamOrganizationExpandTreeBo bo : expandTreeBoList) {
                bo.setCreateName(userSimpleDetailMap.get(bo.getCreateBy()).getName());
                bo.setUpdateName(userSimpleDetailMap.get(bo.getUpdateBy()).getName());
            }
        }

        //组装成中间对象树
        BIamOrganizationExpandTreeBo expandTreeBo = TreeUtil.buildTree(expandTreeBoList).getFirst();

        //tree 后序递归累计子节点的门店和用户数据并计算出门店和用户数量
        postorderTraversalAndCountChildren(expandTreeBo);

        { //添加【未分配】
            BIamOrganizationExpandTreeBo virtualExpandTreeBo = new BIamOrganizationExpandTreeBo();
            virtualExpandTreeBo.setId(typeName + SEPARATOR_COLON + DATA_ORGANIZATION_VIRTUAL_NODE);
            virtualExpandTreeBo.setParentId(expandTreeBo.getId());
            virtualExpandTreeBo.setName(DATA_ORGANIZATION_VIRTUAL_NODE_NAME);
            virtualExpandTreeBo.setSort(Long.MAX_VALUE);
            virtualExpandTreeBo.setCode(typeName + SEPARATOR_COLON + DATA_ORGANIZATION_VIRTUAL_NODE.toUpperCase());
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

        return JSONUtil.toBean(JSONUtil.toJsonStr(expandTreeBo), BIamOrganizationWithShopTreeResponseVo.class);
    }


    private void postorderTraversalAndCountChildren(BIamOrganizationExpandTreeBo node) {
        if (node == null) {
            return;
        }

        for (BIamOrganizationExpandTreeBo child : node.getChildren()) {
            postorderTraversalAndCountChildren(child);
        }

        for (BIamOrganizationExpandTreeBo child : node.getChildren()) {
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


    private BIamOrganizationDetailResponseVo getDetailResponse(BIamOrganizationDetailOutputDto outputDto) {
        String organizationId = outputDto.getId();

        //递归查询id
        Set<String> organizationIdSet = new HashSet<>();
        organizationIdSet.add(organizationId);
        organizationIdSet.addAll(organizationCache.recursionListSubId(outputDto.getType(), organizationId));

        //查询组织关联的门店信息
        List<DataShopOrganizationRelationListOutputDto> shopOrganizationRelationListOutputDtoList = shopOrganizationRelationClient
                .listByOrganizationIdSet(new IdSetRequest(organizationIdSet));

        //查询组织关联的用户信息;
        List<BIamUserOrganizationRelationOutputDto> userOrganizationRelationOutputDtoList = userOrganizationRelationClient
                .listByOrganizationIdSet(new IdSetRequest(organizationIdSet));

        //填充修改人创建人信息
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(outputDto.getCreateBy());
        userIdSet.add(outputDto.getUpdateBy());
        Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

        BIamOrganizationDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), BIamOrganizationDetailResponseVo.class);
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
            Set<String> userIdSetTemp = userOrganizationRelationOutputDtoList.stream().map(BIamUserOrganizationRelationOutputDto::getUserId).collect(Collectors.toSet());
            responseVo.setUserNumber(userIdSetTemp.size());
        }

        //组织下的组织数量
        responseVo.setOrganizationNumber(organizationIdSet.size());
        return responseVo;
    }
}
