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
import com.machine.client.iam.biam.role.IBIamRoleClient;
import com.machine.client.iam.biam.role.dto.output.BIamRoleDetailOutputDto;
import com.machine.client.iam.biam.user.*;
import com.machine.client.iam.biam.user.dto.BIamUserDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserQueryListOffsetInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserRoleBusinessRelationListOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserRoleRelationListOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserListOutputDto;
import com.machine.sdk.base.envm.biam.role.BIamUserRoleBusinessTypeEnum;
import com.machine.sdk.base.envm.biam.user.BIamUserTypeEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
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
    private IBIamRoleClient roleClient;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IBIamUserTypeClient userTypeClient;

    @Autowired
    private IBIamUserRoleRelationClient userRoleRelationClient;

    @Autowired
    private IBIamUserRoleBusinessRelationClient iamUserRoleBusinessRelationClient;


    @Override
    public String userIdByPhone(OpenApiUserPhoneRequestVo request) {
        BIamUserDto iamUserDto = userClient.getByPhone(request.getPhone());
        if (iamUserDto == null) {
            return null;
        }
        return iamUserDto.getUserId();
    }

    @Override
    public OpenapiUserDetailResponseVo detail(OpenApiUserIdRequestVo request) {
        BIamUserDetailOutputDto iamUserDetailOutputDto = userClient.detail(new IdRequest(request.getId()));
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
        BIamUserQueryListOffsetInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserQueryListOffsetInputDto.class);
        List<BIamUserListOutputDto> outputDtoList = userClient.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }

        //序列化
        List<OpenApiUserListSimpleResponseVo> responseVoList =
                JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), OpenApiUserListSimpleResponseVo.class);
        Set<String> responseUserIdSet = responseVoList.stream().map(OpenApiUserListSimpleResponseVo::getId).collect(Collectors.toSet());

        //类型信息
        Map<String, List<BIamUserTypeEnum>> userTypeMap = userTypeClient.mapTypeByUserIdSet(new IdSetRequest(responseUserIdSet));
        for (OpenApiUserListSimpleResponseVo responseVo : responseVoList) {
            responseVo.setUserTypeList(userTypeMap.get(responseVo.getId()));
        }
        return responseVoList;
    }

    /**
     * 用户角色信息
     */
    private List<OpenapiUserRoleInfoResponse> getUserRoleList(String userId) {
        List<BIamUserRoleRelationListOutputDto> userRoleRelationListOutputDtoList =
                userRoleRelationClient.listByUserId(new IdRequest(userId));

        if (CollectionUtil.isEmpty(userRoleRelationListOutputDtoList)) {
            return List.of();
        }

        //用户角色关系Map
        Map<String, BIamUserRoleRelationListOutputDto> userRoleRelationMap = userRoleRelationListOutputDtoList.stream()
                .collect(Collectors.toMap(BIamUserRoleRelationListOutputDto::getId, Function.identity()));

        //角色信息
        Set<String> roleIdSet = userRoleRelationListOutputDtoList.stream()
                .map(BIamUserRoleRelationListOutputDto::getRoleId).collect(Collectors.toSet());
        Map<String, BIamRoleDetailOutputDto> roleIdInfoMap = roleClient.mapByIdSet(new IdSetRequest(roleIdSet));

        //角色业务关系
        Set<String> userRoleRelationIdSet = userRoleRelationListOutputDtoList.stream()
                .map(BIamUserRoleRelationListOutputDto::getId).collect(Collectors.toSet());
        List<BIamUserRoleBusinessRelationListOutputDto> userRoleBusinessRelationListOutputDtoList =
                iamUserRoleBusinessRelationClient.listByUserRoleRelationIdSet(new IdSetRequest(userRoleRelationIdSet));

        //门店信息
        Map<String, DataShopDetailOutputDto> shopIdInfoMap = getShopIdInfoMap(userRoleBusinessRelationListOutputDtoList);

        return assembleUserRoleInfo(userRoleBusinessRelationListOutputDtoList, userRoleRelationMap, roleIdInfoMap, shopIdInfoMap);
    }


    private List<OpenapiUserRoleInfoResponse> assembleUserRoleInfo(List<BIamUserRoleBusinessRelationListOutputDto> outputDtoList,
                                                               Map<String, BIamUserRoleRelationListOutputDto> userRoleRelationMap,
                                                               Map<String, BIamRoleDetailOutputDto> roleIdInfoMap,
                                                               Map<String, DataShopDetailOutputDto> shopIdInfoMap) {
        //组装角色信息
        List<OpenapiUserRoleInfoResponse> userRoleList = new ArrayList<>();
        Map<String, OpenapiUserRoleInfoResponse> userRoleMap = new HashMap<>();
        for (BIamUserRoleBusinessRelationListOutputDto outputDto : outputDtoList) {
            //用户角色关系信息
            BIamUserRoleRelationListOutputDto userRoleRelationListOutputDto = userRoleRelationMap
                    .get(outputDto.getUserRoleRelationId());

            String roleId = userRoleRelationListOutputDto.getRoleId();
            OpenapiUserRoleInfoResponse userRoleInfoResponse = userRoleMap.get(roleId);

            if (null == userRoleInfoResponse) {
                BIamRoleDetailOutputDto roleDetailOutputDto = roleIdInfoMap.get(roleId);
                userRoleInfoResponse = new OpenapiUserRoleInfoResponse();
                userRoleInfoResponse.setId(roleDetailOutputDto.getId());
                userRoleInfoResponse.setType(roleDetailOutputDto.getType());
                userRoleInfoResponse.setName(roleDetailOutputDto.getName());
                userRoleInfoResponse.setCode(roleDetailOutputDto.getCode());
                userRoleMap.put(roleId, userRoleInfoResponse);
                userRoleList.add(userRoleInfoResponse);
            }

            if (BIamUserRoleBusinessTypeEnum.SHOP == outputDto.getBusinessType()) {
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

    private Map<String, DataShopDetailOutputDto> getShopIdInfoMap(List<BIamUserRoleBusinessRelationListOutputDto> outputDtoList) {
        Set<String> idSet = new HashSet<>();
        for (BIamUserRoleBusinessRelationListOutputDto outputDto : outputDtoList) {
            if (BIamUserRoleBusinessTypeEnum.SHOP == outputDto.getBusinessType()) {
                idSet.add(outputDto.getBusinessId());
            }
        }
        if (CollectionUtil.isEmpty(idSet)) {
            return Map.of();
        }
        return shopClient.mapByIdSet(new IdSetRequest(idSet));
    }

}
