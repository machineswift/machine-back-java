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
import com.machine.client.data.supplier.ISupplierCompanyClient;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanySimpleListOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationSimpleOutputDto;
import com.machine.client.iam.organization.dto.output.IamUserRoleTargetListOutputDto;
import com.machine.client.iam.role.IIamRoleClient;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserRoleTargetClient;
import com.machine.client.iam.user.IIamUserTypeClient;
import com.machine.client.iam.user.dto.UserDto;
import com.machine.client.iam.user.dto.input.IamUserQueryListOffsetInputDto;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserListOutputDto;
import com.machine.sdk.common.envm.iam.UserRoleTargetTypeEnum;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OpenApiUserBusinessImpl implements IOpenApiUserBusiness {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IDataShopClient shopClient;

    @Autowired
    private IIamRoleClient roleClient;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserTypeClient userTypeClient;

    @Autowired
    private IIamUserRoleTargetClient userRoleTargetClient;

    @Autowired
    private ISupplierCompanyClient supplierCompanyClient;


    @Override
    public String userIdByPhone(OpenApiUserPhoneRequestVo request) {
        UserDto userDto = userClient.getByPhone(request.getPhone());
        if (userDto == null) {
            return null;
        }
        return userDto.getUserId();
    }

    @Override
    public OpenapiUserDetailResponseVo detail(OpenApiUserIdRequestVo request) {
        UserDetailOutputDto userDetailOutputDto = userClient.detail(new IdRequest(request.getId()));
        if (null == userDetailOutputDto) {
            return null;
        }

        OpenapiUserDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(userDetailOutputDto), OpenapiUserDetailResponseVo.class);

        //类型信息
        responseVo.setUserTypeList(userTypeClient.listTypeByUserId(new IdRequest(request.getId())));

        //角色信息
        responseVo.setUserRoleList(getUserRoleList(request.getId()));

        return responseVo;
    }

    @Override
    public List<OpenApiUserListSimpleResponseVo> listSimple(OpenApiUserListSimpleRequestVo request) {
        IamUserQueryListOffsetInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserQueryListOffsetInputDto.class);
        List<UserListOutputDto> outputDtoList = userClient.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }

        //序列化
        List<OpenApiUserListSimpleResponseVo> responseVoList =
                JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), OpenApiUserListSimpleResponseVo.class);
        Set<String> responseUserIdSet = responseVoList.stream().map(OpenApiUserListSimpleResponseVo::getId).collect(Collectors.toSet());

        //类型信息
        Map<String, List<UserTypeEnum>> userTypeMap = userTypeClient.mapTypeByUserIdSet(new IdSetRequest(responseUserIdSet));
        for (OpenApiUserListSimpleResponseVo responseVo : responseVoList) {
            responseVo.setUserTypeList(userTypeMap.get(responseVo.getId()));
        }
        return responseVoList;
    }

    /**
     * 用户角色信息
     */
    private List<OpenapiUserRoleInfoResponse> getUserRoleList(String userId) {
        List<IamUserRoleTargetListOutputDto> singleUserTargetInfoList = userRoleTargetClient.listByUserId(new IdRequest(userId));
        if (CollectionUtil.isEmpty(singleUserTargetInfoList)) {
            return List.of();
        }

        //角色信息
        Map<String, IamRoleDetailOutputDto> roleMap = getRoleMap(singleUserTargetInfoList);
        //组织信息
        Map<String, IamOrganizationSimpleOutputDto> organizationMap = getOrganizationMap(singleUserTargetInfoList);
        //门店信息
        Map<String, DataShopDetailOutputDto> shopMap = getShopMap(singleUserTargetInfoList);
        //公司信息
        Map<String, DataSupplierCompanySimpleListOutputDto> companyMap = getCompanyMap(singleUserTargetInfoList);

        return assembleUserRoleInfo(singleUserTargetInfoList, roleMap, organizationMap, shopMap, companyMap);
    }


    /**
     * 角色信息
     */
    private Map<String, IamRoleDetailOutputDto> getRoleMap(List<IamUserRoleTargetListOutputDto> singleUserTargetInfoList) {
        Set<String> idSet = new HashSet<>();
        for (IamUserRoleTargetListOutputDto outputDto : singleUserTargetInfoList) {
            idSet.add(outputDto.getRoleId());
        }
        if (CollectionUtil.isEmpty(idSet)) {
            return Map.of();
        }
        return roleClient.mapByIdSet(new IdSetRequest(idSet));
    }

    /**
     * 组织信息
     */
    private Map<String, IamOrganizationSimpleOutputDto> getOrganizationMap(List<IamUserRoleTargetListOutputDto> singleUserTargetInfoList) {
        Set<String> idSet = new HashSet<>();
        for (IamUserRoleTargetListOutputDto outputDto : singleUserTargetInfoList) {
            if (UserRoleTargetTypeEnum.ORGANIZATION == outputDto.getTargetType()) {
                idSet.add(outputDto.getTargetId());
            }
        }
        if (CollectionUtil.isEmpty(idSet)) {
            return Map.of();
        }
        return organizationCache.mapByIdSet(idSet);
    }

    /**
     * 门店信息
     */
    private Map<String, DataShopDetailOutputDto> getShopMap(List<IamUserRoleTargetListOutputDto> singleUserTargetInfoList) {
        Set<String> idSet = new HashSet<>();
        for (IamUserRoleTargetListOutputDto outputDto : singleUserTargetInfoList) {
            if (UserRoleTargetTypeEnum.SHOP == outputDto.getTargetType()) {
                idSet.add(outputDto.getTargetId());
            }
        }
        if (CollectionUtil.isEmpty(idSet)) {
            return Map.of();
        }
        return shopClient.mapByIdSet(new IdSetRequest(idSet));
    }

    /**
     * 公司信息
     */
    private Map<String, DataSupplierCompanySimpleListOutputDto> getCompanyMap(List<IamUserRoleTargetListOutputDto> singleUserTargetInfoList) {
        Set<String> idSet = new HashSet<>();
        for (IamUserRoleTargetListOutputDto outputDto : singleUserTargetInfoList) {
            if (UserRoleTargetTypeEnum.COMPANY == outputDto.getTargetType()) {
                idSet.add(outputDto.getTargetId());
            }
        }
        if (CollectionUtil.isEmpty(idSet)) {
            return Map.of();
        }
        return supplierCompanyClient.mapByIdSet(new IdSetRequest(idSet));
    }

    private List<OpenapiUserRoleInfoResponse> assembleUserRoleInfo(List<IamUserRoleTargetListOutputDto> singleUserTargetInfoList,
                                                                   Map<String, IamRoleDetailOutputDto> roleMap,
                                                                   Map<String, IamOrganizationSimpleOutputDto> organizationMap,
                                                                   Map<String, DataShopDetailOutputDto> shopMap,
                                                                   Map<String, DataSupplierCompanySimpleListOutputDto> companyMap) {
        //组装角色信息
        List<OpenapiUserRoleInfoResponse> userRoleList = new ArrayList<>();
        Map<String, OpenapiUserRoleInfoResponse> userRoleMap = new HashMap<>();
        for (IamUserRoleTargetListOutputDto dto : singleUserTargetInfoList) {
            OpenapiUserRoleInfoResponse userRoleInfoResponse = userRoleMap.get(dto.getRoleId());
            if (null == userRoleInfoResponse) {
                IamRoleDetailOutputDto roleDetailOutputDto = roleMap.get(dto.getRoleId());
                userRoleInfoResponse = new OpenapiUserRoleInfoResponse();
                userRoleInfoResponse.setRoleId(roleDetailOutputDto.getId());
                userRoleInfoResponse.setRoleType(roleDetailOutputDto.getType());
                userRoleInfoResponse.setRoleName(roleDetailOutputDto.getName());
                userRoleInfoResponse.setRoleCode(roleDetailOutputDto.getCode());
                userRoleMap.put(dto.getRoleId(), userRoleInfoResponse);
                userRoleList.add(userRoleInfoResponse);
            }

            if (UserRoleTargetTypeEnum.ORGANIZATION == dto.getTargetType()) {
                IamOrganizationSimpleOutputDto iamOrganizationSimpleOutputDto = organizationMap.get(dto.getTargetId());
                List<String> organizationIdList = userRoleInfoResponse.getOrganizationIdList();
                if (null == organizationIdList) {
                    organizationIdList = new ArrayList<>();
                    userRoleInfoResponse.setOrganizationIdList(organizationIdList);
                }
                organizationIdList.add(iamOrganizationSimpleOutputDto.getId());
            } else if (UserRoleTargetTypeEnum.SHOP == dto.getTargetType()) {
                DataShopDetailOutputDto dataShopDetailOutputDto = shopMap.get(dto.getTargetId());
                List<String> shopIdList = userRoleInfoResponse.getShopIdList();
                if (null == shopIdList) {
                    shopIdList = new ArrayList<>();
                    userRoleInfoResponse.setShopIdList(shopIdList);
                }
                shopIdList.add(dataShopDetailOutputDto.getId());
            } else if (UserRoleTargetTypeEnum.COMPANY == dto.getTargetType()) {
                DataSupplierCompanySimpleListOutputDto outputDto = companyMap.get(dto.getTargetId());
                List<String> companyIdList = userRoleInfoResponse.getCompanyIdList();
                if (null == companyIdList) {
                    companyIdList = new ArrayList<>();
                    userRoleInfoResponse.setCompanyIdList(companyIdList);
                }
                companyIdList.add(outputDto.getId());
            }
        }
        return userRoleList;
    }
}
