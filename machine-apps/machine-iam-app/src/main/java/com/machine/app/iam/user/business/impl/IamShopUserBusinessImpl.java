package com.machine.app.iam.user.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.user.business.IIamShopUserBusiness;
import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.*;
import com.machine.client.data.employee.IShopEmployeeClient;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeDetailOutputDto;
import com.machine.client.data.franchisee.IFranchiseeClient;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeDetailOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeHealthCertificateOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeIdentityCardOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserTypeClient;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserListOutputDto;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class IamShopUserBusinessImpl implements IIamShopUserBusiness {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IamUserBusinessImpl userBusiness;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IShopEmployeeClient shopEmployeeClient;

    @Autowired
    private IFranchiseeClient franchiseeClient;

    @Autowired
    private IIamUserTypeClient userTypeClient;

    @Override
    public String create(IamShopUserCreateRequestVo request) {
        IamShopUserCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamShopUserCreateInputDto.class);
        return userClient.createShopUser(inputDto);
    }

    @Override
    public void update(IamShopUserUpdateRequestVo request) {
        IamShopUserUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamShopUserUpdateInputDto.class);
        userClient.updateShopUser(inputDto);
    }

    @Override
    public IamShopUserDetailResponseVo detail(IdRequest request) {
        UserDetailOutputDto userDetailOutputDto = userClient.detail(request);
        if (null == userDetailOutputDto) {
            return null;
        }

        //不存对应类型，直接返回
        if (!userTypeClient.existsType(new IamUserTypeExistsTypeInputDto(request.getId(),
                List.of(UserTypeEnum.SHOP, UserTypeEnum.FRANCHISEE)))) {
            return null;
        }

        IamShopUserDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(userDetailOutputDto), IamShopUserDetailResponseVo.class);

        { //类型信息
            responseVo.setUserTypeList(userTypeClient.listTypeByUserId(new IdRequest(request.getId())));
        }

        { //身份证、健康证
            if (responseVo.getUserTypeList().contains(UserTypeEnum.SHOP)) {
                ShopEmployeeDetailOutputDto outputDto = shopEmployeeClient.getByUserId(request);

                OpenapiShopEmployeeIdentityCardOutputDto identityCardOutputDto = shopEmployeeClient.identityCard(new IdRequest(outputDto.getId()));
                if (null != identityCardOutputDto) {
                    responseVo.setIdentityCard(identityCardOutputDto.getPermanentIdentityCard());
                }
                OpenapiShopEmployeeHealthCertificateOutputDto healthCertificateOutputDto = shopEmployeeClient.healthCertificate(new IdRequest(outputDto.getId()));
                if (null != healthCertificateOutputDto) {
                    responseVo.setHealthCertificate(healthCertificateOutputDto.getPermanentHealthCertificate());
                }
            } else {
                DataFranchiseeDetailOutputDto outputDto = franchiseeClient.getByUserId(request);
                OpenapiFranchiseeIdentityCardOutputDto identityCardOutputDto = franchiseeClient.identityCard(new IdRequest(outputDto.getId()));
                if (null != identityCardOutputDto) {
                    responseVo.setIdentityCard(identityCardOutputDto.getPermanentIdentityCard());
                }
                OpenapiFranchiseeHealthCertificateOutputDto healthCertificateOutputDto = franchiseeClient.healthCertificate(new IdRequest(outputDto.getId()));
                if (null != healthCertificateOutputDto) {
                    responseVo.setHealthCertificate(healthCertificateOutputDto.getPermanentHealthCertificate());
                }
            }
        }

        {//角色信息
            responseVo.setUserRoleList(userBusiness.getUserRoleList(request.getId()));
        }

        { //填充修改人创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(responseVo.getCreateBy());
            userIdSet.add(responseVo.getUpdateBy());
            Map<String, UserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }

        return responseVo;
    }

    @Override
    public PageResponse<IamShopUserExpandListResponseVo> pageExpand(IamShopUserQueryPageExpandRequestVo request) {
        IamShopUserQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamShopUserQueryPageInputDto.class);
        //组x z装UserId集合
        Set<String> queryUserIdSet = new HashSet<>();
        if (CollectionUtil.isNotEmpty(request.getOrganizationIdSet())) {
            queryUserIdSet.addAll(userBusiness.getIdByOrganizationIdSet(
                    //递归查询所有子节点
                    organizationCache.recursionListSubIds(request.getOrganizationIdSet())));
        }
        queryUserIdSet.addAll(userBusiness.getIdByRoleIdSet(request.getRoleIdSet()));
        inputDto.setUserIdSet(queryUserIdSet);

        //分页查询用户信息
        PageResponse<UserListOutputDto> pageOutputDto = userClient.pageShopUser(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(
                    pageOutputDto.getCurrent(),
                    pageOutputDto.getSize(),
                    pageOutputDto.getTotal());
        }

        //序列化
        PageResponse<IamShopUserExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), IamShopUserExpandListResponseVo.class));
        Set<String> responseUserIdSet = pageResponse.getRecords().stream().map(IamShopUserExpandListResponseVo::getId).collect(Collectors.toSet());

        //类型信息
        Map<String, List<UserTypeEnum>> userTypeMap = userTypeClient.mapTypeByUserIdSet(new IdSetRequest(responseUserIdSet));
        for (IamShopUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
            responseVo.setUserTypeList(userTypeMap.get(responseVo.getId()));
        }

        //角色信息
        Map<String, List<IamUserRoleInfoResponse>> userRoleResponseMap = userBusiness.getUserRoleListMap(responseUserIdSet);
        for (IamShopUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
            responseVo.setUserRoleList(userRoleResponseMap.get(responseVo.getId()));
        }

        //创建人、修改文姓名
        Set<String> userIdSet = pageResponse.getRecords().stream().map(IamShopUserExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
        userIdSet.addAll(pageResponse.getRecords().stream().map(IamShopUserExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
        Map<String, UserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (IamShopUserExpandListResponseVo vo : pageResponse.getRecords()) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }

        return pageResponse;
    }
}
