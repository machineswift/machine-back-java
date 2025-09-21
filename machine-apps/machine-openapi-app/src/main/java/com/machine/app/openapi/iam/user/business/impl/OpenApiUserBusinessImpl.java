package com.machine.app.openapi.iam.user.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.iam.user.business.IOpenApiUserBusiness;
import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserIdRequestVo;
import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserListSimpleRequestVo;
import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserPhoneRequestVo;
import com.machine.app.openapi.iam.user.controller.vo.response.OpenApiUserListSimpleResponseVo;
import com.machine.app.openapi.iam.user.controller.vo.response.OpenapiUserDetailResponseVo;
import com.machine.app.openapi.iam.user.controller.vo.response.OpenapiUserRoleInfoResponse;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.dto.output.DataShopDetailOutputDto;
import com.machine.client.iam.role.IIamRoleClient;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserRoleBusinessRelationClient;
import com.machine.client.iam.user.IIamUserRoleRelationClient;
import com.machine.client.iam.user.IIamUserTypeClient;
import com.machine.client.iam.user.dto.IamUserDto;
import com.machine.client.iam.user.dto.input.IamUserQueryListOffsetInputDto;
import com.machine.client.iam.user.dto.output.IamUserRoleBusinessRelationListOutputDto;
import com.machine.client.iam.user.dto.output.IamUserRoleRelationListOutputDto;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.sdk.common.envm.iam.IamUserRoleBusinessTypeEnum;
import com.machine.sdk.common.envm.iam.IamUserTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OpenApiUserBusinessImpl implements IOpenApiUserBusiness {

    @Autowired
    private IDataShopClient shopClient;

    @Autowired
    private IIamRoleClient roleClient;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserTypeClient userTypeClient;

    @Autowired
    private IIamUserRoleRelationClient userRoleRelationClient;

    @Autowired
    private IIamUserRoleBusinessRelationClient iamUserRoleBusinessRelationClient;


    @Override
    public String userIdByPhone(OpenApiUserPhoneRequestVo request) {
        IamUserDto iamUserDto = userClient.getByPhone(request.getPhone());
        if (iamUserDto == null) {
            return null;
        }
        return iamUserDto.getUserId();
    }

    @Override
    public OpenapiUserDetailResponseVo detail(OpenApiUserIdRequestVo request) {
        IamUserDetailOutputDto iamUserDetailOutputDto = userClient.detail(new IdRequest(request.getId()));
        if (null == iamUserDetailOutputDto) {
            return null;
        }

        OpenapiUserDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(iamUserDetailOutputDto), OpenapiUserDetailResponseVo.class);

        //类型信息
        responseVo.setUserTypeList(userTypeClient.listTypeByUserId(new IdRequest(request.getId())));

        //角色信息
        responseVo.setUserRoleList(getUserRoleList(request.getId()));

        return responseVo;
    }

    @Override
    public List<OpenApiUserListSimpleResponseVo> listSimple(OpenApiUserListSimpleRequestVo request) {
        IamUserQueryListOffsetInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserQueryListOffsetInputDto.class);
        List<IamUserListOutputDto> outputDtoList = userClient.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }

        //序列化
        List<OpenApiUserListSimpleResponseVo> responseVoList =
                JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), OpenApiUserListSimpleResponseVo.class);
        Set<String> responseUserIdSet = responseVoList.stream().map(OpenApiUserListSimpleResponseVo::getId).collect(Collectors.toSet());

        //类型信息
        Map<String, List<IamUserTypeEnum>> userTypeMap = userTypeClient.mapTypeByUserIdSet(new IdSetRequest(responseUserIdSet));
        for (OpenApiUserListSimpleResponseVo responseVo : responseVoList) {
            responseVo.setUserTypeList(userTypeMap.get(responseVo.getId()));
        }
        return responseVoList;
    }

    /**
     * 用户角色信息
     */
    private List<OpenapiUserRoleInfoResponse> getUserRoleList(String userId) {
        List<IamUserRoleRelationListOutputDto> userRoleRelationListOutputDtoList =
                userRoleRelationClient.listByUserId(new IdRequest(userId));

        if (CollectionUtil.isEmpty(userRoleRelationListOutputDtoList)) {
            return List.of();
        }

        //用户角色关系Map
        Map<String, IamUserRoleRelationListOutputDto> userRoleRelationMap = userRoleRelationListOutputDtoList.stream()
                .collect(Collectors.toMap(IamUserRoleRelationListOutputDto::getId, Function.identity()));

        //角色信息
        Set<String> roleIdSet = userRoleRelationListOutputDtoList.stream()
                .map(IamUserRoleRelationListOutputDto::getRoleId).collect(Collectors.toSet());
        Map<String, IamRoleDetailOutputDto> roleIdInfoMap = roleClient.mapByIdSet(new IdSetRequest(roleIdSet));

        //角色业务关系
        Set<String> userRoleRelationIdSet = userRoleRelationListOutputDtoList.stream()
                .map(IamUserRoleRelationListOutputDto::getId).collect(Collectors.toSet());
        List<IamUserRoleBusinessRelationListOutputDto> userRoleBusinessRelationListOutputDtoList =
                iamUserRoleBusinessRelationClient.listByUserRoleRelationIdSet(new IdSetRequest(userRoleRelationIdSet));

        //门店信息
        Map<String, DataShopDetailOutputDto> shopIdInfoMap = getShopIdInfoMap(userRoleBusinessRelationListOutputDtoList);

        return assembleUserRoleInfo(userRoleBusinessRelationListOutputDtoList, userRoleRelationMap, roleIdInfoMap, shopIdInfoMap);
    }


    private List<OpenapiUserRoleInfoResponse> assembleUserRoleInfo(List<IamUserRoleBusinessRelationListOutputDto> outputDtoList,
                                                               Map<String, IamUserRoleRelationListOutputDto> userRoleRelationMap,
                                                               Map<String, IamRoleDetailOutputDto> roleIdInfoMap,
                                                               Map<String, DataShopDetailOutputDto> shopIdInfoMap) {
        //组装角色信息
        List<OpenapiUserRoleInfoResponse> userRoleList = new ArrayList<>();
        Map<String, OpenapiUserRoleInfoResponse> userRoleMap = new HashMap<>();
        for (IamUserRoleBusinessRelationListOutputDto outputDto : outputDtoList) {
            //用户角色关系信息
            IamUserRoleRelationListOutputDto userRoleRelationListOutputDto = userRoleRelationMap
                    .get(outputDto.getUserRoleRelationId());

            String roleId = userRoleRelationListOutputDto.getRoleId();
            OpenapiUserRoleInfoResponse userRoleInfoResponse = userRoleMap.get(roleId);

            if (null == userRoleInfoResponse) {
                IamRoleDetailOutputDto roleDetailOutputDto = roleIdInfoMap.get(roleId);
                userRoleInfoResponse = new OpenapiUserRoleInfoResponse();
                userRoleInfoResponse.setId(roleDetailOutputDto.getId());
                userRoleInfoResponse.setType(roleDetailOutputDto.getType());
                userRoleInfoResponse.setName(roleDetailOutputDto.getName());
                userRoleInfoResponse.setCode(roleDetailOutputDto.getCode());
                userRoleMap.put(roleId, userRoleInfoResponse);
                userRoleList.add(userRoleInfoResponse);
            }

            if (IamUserRoleBusinessTypeEnum.SHOP == outputDto.getBusinessType()) {
                DataShopDetailOutputDto shopDetailOutputDto = shopIdInfoMap.get(outputDto.getBusinessId());
                List<OpenapiUserRoleInfoResponse.BusinessInfo> shopList = userRoleInfoResponse.getShopList();
                if (null == shopList) {
                    shopList = new ArrayList<>();
                    userRoleInfoResponse.setShopList(shopList);
                }

                OpenapiUserRoleInfoResponse.BusinessInfo targetInfo = new OpenapiUserRoleInfoResponse.BusinessInfo();
                targetInfo.setId(outputDto.getId());
                targetInfo.setCode(shopDetailOutputDto.getCode());
                targetInfo.setName(shopDetailOutputDto.getName());
                targetInfo.setSort(outputDto.getSort());
                shopList.add(targetInfo);
            }
        }
        return userRoleList;
    }

    private Map<String, DataShopDetailOutputDto> getShopIdInfoMap(List<IamUserRoleBusinessRelationListOutputDto> outputDtoList) {
        Set<String> idSet = new HashSet<>();
        for (IamUserRoleBusinessRelationListOutputDto outputDto : outputDtoList) {
            if (IamUserRoleBusinessTypeEnum.SHOP == outputDto.getBusinessType()) {
                idSet.add(outputDto.getBusinessId());
            }
        }
        if (CollectionUtil.isEmpty(idSet)) {
            return Map.of();
        }
        return shopClient.mapByIdSet(new IdSetRequest(idSet));
    }

}
