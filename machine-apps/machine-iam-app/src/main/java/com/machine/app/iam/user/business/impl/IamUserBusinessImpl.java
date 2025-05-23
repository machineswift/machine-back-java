package com.machine.app.iam.user.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.machine.app.iam.user.controller.vo.request.IamAdminChangePasswordRequestVo;
import com.machine.app.iam.user.business.IIamUserBusiness;
import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.IamUserRoleInfoResponse;
import com.machine.app.iam.user.controller.vo.response.IamUserSimpleListResponseVo;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.dto.output.DataShopDetailOutputDto;
import com.machine.client.hrm.employee.IHrmEmployeeDefaultClient;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeListOutputDto;
import com.machine.client.iam.role.IIamRoleClient;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.user.*;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.*;
import com.machine.sdk.common.envm.iam.UserRoleBusinessTypeEnum;
import com.machine.sdk.common.envm.iam.organization.OrganizationTypeEnum;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
import com.machine.sdk.common.envm.iam.auth.AuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.AuthMethodEnum;
import com.machine.sdk.common.envm.iam.auth.AuthResultEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.starter.redis.cache.RedisCacheHrmDepartment;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import com.machine.starter.redis.function.CustomerRedisCommands;
import com.machine.starter.security.util.LoginLogUtil;
import com.machine.starter.security.util.MachineJwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.Data.ORGANIZATION_ROOT_PARENT_ID;
import static com.machine.sdk.common.constant.CommonConstant.Data.ORGANIZATION_VIRTUAL_NODE;
import static com.machine.sdk.common.constant.CommonConstant.SEPARATOR_COLON;
import static com.machine.starter.security.util.LoginLogUtil.blackAllAvailableToken;

@Slf4j
@Component
public class IamUserBusinessImpl implements IIamUserBusiness {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisCacheHrmDepartment departmentCache;

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private MachineJwtUtil machineJwtUtil;

    @Autowired
    private IDataShopClient shopClient;

    @Autowired
    private IIamRoleClient roleClient;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserTypeClient userTypeClient;

    @Autowired
    private IIamUserLoginLogClient loginLogClient;

    @Autowired
    private IHrmEmployeeDefaultClient employeeDefaultClient;

    @Autowired
    private IIamUserRoleRelationClient userRoleRelationClient;

    @Autowired
    private IIamUserOrganizationRelationClient userOrganizationRelationClient;

    @Autowired
    private IIamUserRoleBusinessRelationClient iamUserRoleBusinessRelationClient;


    @Override
    public void updateStatus(IamUserUpdateStatusRequestVo requestVo) {
        userClient.updateStatus(JSONUtil.toBean(JSONUtil.toJsonStr(requestVo), IamUserUpdateStatusInputDto.class));
    }

    @Override
    public void adminChangePassword(IamAdminChangePasswordRequestVo requestVo) {
        userClient.updatePassword(new IamUserUpdatePasswordInputDto(requestVo.getId(),
                passwordEncoder.encode(requestVo.getNewPassword())));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        blackAuthToken4AdminUpdatePassword(requestVo.getId(), request);
    }

    @Override
    public void extractedUserIdByOrganizationIdSet(Set<String> organizationIdSet,
                                                   Set<String> finallyqueryShopIdSet) {

        Set<String> organizationShopIdSet = new HashSet<>();
        for (OrganizationTypeEnum type : OrganizationTypeEnum.values()) {
            if (organizationIdSet.contains(type.getName() + SEPARATOR_COLON + ORGANIZATION_VIRTUAL_NODE)) {
                //未分配节点
                List<String> userIdList = userClient.listNotBindOrganization(new DataUserNotBindOrganizationInputDto(type));
                if (CollectionUtil.isNotEmpty(userIdList)) {
                    organizationShopIdSet.addAll(userIdList);
                }
                break;
            }
        }

        //递归查询子节点
        Set<String> recursionOrganizationIdSet = organizationCache.recursionListSubIds(organizationIdSet);

        if (CollectionUtil.isNotEmpty(recursionOrganizationIdSet)) {
            Set<String> userIdSet = getIdByOrganizationIdSet(recursionOrganizationIdSet);
            organizationShopIdSet.addAll(userIdSet);
        }

        //取交集
        if (CollectionUtil.isEmpty(finallyqueryShopIdSet)) {
            finallyqueryShopIdSet.addAll(organizationShopIdSet);
        } else {
            finallyqueryShopIdSet.retainAll(organizationShopIdSet);
        }

    }

    @Override
    public Set<String> getIdByDepartmentIdSet(Set<String> departmentIdSet) {
        if (CollectionUtil.isEmpty(departmentIdSet)) {
            return Set.of();
        }

        HrmEmployeeQueryIListInputDto inputDto = new HrmEmployeeQueryIListInputDto(
                departmentIdSet, null, true);
        List<HrmEmployeeListOutputDto> outputDtoList = employeeDefaultClient.list(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return Set.of();
        }
        return outputDtoList.stream().map(HrmEmployeeListOutputDto::getUserId).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIdByOrganizationIdSet(Set<String> organizationIdSet) {
        if (CollectionUtil.isEmpty(organizationIdSet)) {
            return Set.of();
        }

        //查询组织关联的用户信息
        List<IamUserOrganizationRelationOutputDto> outputDtoList = userOrganizationRelationClient.listByOrganizationIdSet(
                new IdSetRequest(organizationIdSet));
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return Set.of();
        }
        return outputDtoList.stream().map(IamUserOrganizationRelationOutputDto::getUserId).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIdByRoleIdSet(Set<String> roleIdSet) {
        if (CollectionUtil.isEmpty(roleIdSet)) {
            return Set.of();
        }

        List<IamUserRoleRelationListOutputDto> outputDtoList = userRoleRelationClient.listByRoleIdSet(new IdSetRequest(roleIdSet));
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return Set.of();
        }

        return outputDtoList.stream().map(IamUserRoleRelationListOutputDto::getUserId).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIdByShopIdSet(Set<String> shopIdSet) {
        if (CollectionUtil.isEmpty(shopIdSet)) {
            return Set.of();
        }

        List<IamUserRoleBusinessRelationListOutputDto> userRoleBusinessRelationListOutputDtoList =
                iamUserRoleBusinessRelationClient.listByShopIdSet(new IdSetRequest(shopIdSet));
        if (CollectionUtil.isEmpty(userRoleBusinessRelationListOutputDtoList)) {
            return Set.of();
        }

        Set<String> userRoleRelationIdSet = userRoleBusinessRelationListOutputDtoList.stream()
                .map(IamUserRoleBusinessRelationListOutputDto::getId).collect(Collectors.toSet());

        List<IamUserRoleRelationListOutputDto> userRoleRelationListOutputDtoList =
                userRoleRelationClient.listByIdSet(new IdSetRequest(userRoleRelationIdSet));
        if (CollectionUtil.isEmpty(userRoleRelationListOutputDtoList)) {
            return Set.of();
        }

        return userRoleRelationListOutputDtoList.stream().map(IamUserRoleRelationListOutputDto::getUserId).collect(Collectors.toSet());
    }

    /**
     * 用户角色信息
     */
    @Override
    public List<IamUserRoleInfoResponse> getUserRoleList(String userId) {
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


    @Override
    public Map<String, List<IamUserRoleInfoResponse>> getUserRoleListMap(Set<String> userIdSet) {
        List<IamUserRoleRelationListOutputDto> userRoleRelationListOutputDtoList =
                userRoleRelationClient.listByUserIdSet(new IdSetRequest(userIdSet));
        if (CollectionUtil.isEmpty(userRoleRelationListOutputDtoList)) {
            return Map.of();
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

        //根据UserId拆分Map集合
        Map<String, List<IamUserRoleBusinessRelationListOutputDto>> userRoleBusinessRelationMap = new HashMap<>();
        for (IamUserRoleBusinessRelationListOutputDto outputDto : userRoleBusinessRelationListOutputDtoList) {
            String userId = userRoleRelationMap.get(outputDto.getUserRoleRelationId()).getUserId();
            List<IamUserRoleBusinessRelationListOutputDto> outputDtoList = userRoleBusinessRelationMap
                    .computeIfAbsent(userId, k -> new ArrayList<>());
            outputDtoList.add(outputDto);
        }

        //组装返回信息
        Map<String, List<IamUserRoleInfoResponse>> userRoleResponseMap = new HashMap<>();
        for (Map.Entry<String, List<IamUserRoleBusinessRelationListOutputDto>> entity : userRoleBusinessRelationMap.entrySet()) {
            String userId = entity.getKey();
            userRoleResponseMap.put(userId, assembleUserRoleInfo(entity.getValue(), userRoleRelationMap, roleIdInfoMap, shopIdInfoMap));
        }
        return userRoleResponseMap;
    }

    @Override
    public PageResponse<IamUserSimpleListResponseVo> pageSimpled(IamUserQueryPageSimpleRequestVo request) {
        //组装UserId集合
        boolean compute = false;
        Set<String> finallyqueryUserIdSet = new HashSet<>();

        //组装UserId集合(门店)
        Set<String> shopIdSet = request.getShopIdSet();
        if (CollectionUtil.isNotEmpty(shopIdSet)) {
            compute = true;
            Set<String> userIdSet = getIdByShopIdSet(shopIdSet);
            if (CollectionUtil.isNotEmpty(userIdSet)) {
                finallyqueryUserIdSet.addAll(userIdSet);
            }
        }

        //组装UserId集合(组织)
        Set<String> organizationIdSet = request.getOrganizationIdSet();
        if ((!compute || CollectionUtil.isNotEmpty(organizationIdSet))
                && CollectionUtil.isNotEmpty(request.getOrganizationIdSet())) {
            boolean containRootId = false;
            for (OrganizationTypeEnum type : OrganizationTypeEnum.values()) {
                if (organizationIdSet.contains(type.getName().toLowerCase())) {
                    //包含根组织直接返回
                    containRootId = true;
                    break;
                }
            }

            if (organizationIdSet.contains(ORGANIZATION_ROOT_PARENT_ID)) {
                containRootId = true;
            }

            if (!containRootId) {
                compute = true;
                extractedUserIdByOrganizationIdSet(organizationIdSet, finallyqueryUserIdSet);
            }
        }

        //组装UserId集合(部门)
        if ((!compute || CollectionUtil.isNotEmpty(finallyqueryUserIdSet))
                && CollectionUtil.isNotEmpty(request.getDepartmentIdSet())) {
            compute = true;
            Set<String> departmentIdSet = departmentCache.recursionListSubIdSet(request.getDepartmentIdSet());
            Set<String> userIdSet = new HashSet<>(getIdByDepartmentIdSet(departmentIdSet));
            if (CollectionUtil.isNotEmpty(userIdSet)) {
                //取交集
                if (CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
                    finallyqueryUserIdSet.addAll(userIdSet);
                } else {
                    finallyqueryUserIdSet.retainAll(userIdSet);
                }
            }
        }

        //组装UserId集合(角色)
        Set<String> roleIdSet = request.getRoleIdSet();
        if ((!compute || CollectionUtil.isNotEmpty(finallyqueryUserIdSet))
                && CollectionUtil.isNotEmpty(roleIdSet)) {
            compute = true;
            Set<String> userIdSet = getIdByRoleIdSet(roleIdSet);
            if (CollectionUtil.isNotEmpty(userIdSet)) {
                //取交集
                if (CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
                    finallyqueryUserIdSet.addAll(userIdSet);
                } else {
                    finallyqueryUserIdSet.retainAll(userIdSet);
                }
            }
        }

        if (compute && CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
            return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
        }

        IamUserQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserQueryPageInputDto.class);
        inputDto.setUserIdSet(finallyqueryUserIdSet);

        //分页查询用户信息
        PageResponse<UserListOutputDto> pageOutputDto = userClient.pageUser(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(
                    pageOutputDto.getCurrent(),
                    pageOutputDto.getSize(),
                    pageOutputDto.getTotal());
        }

        PageResponse<IamUserSimpleListResponseVo> pageResponse = new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), IamUserSimpleListResponseVo.class));
        Set<String> responseUserIdSet = pageResponse.getRecords().stream().map(IamUserSimpleListResponseVo::getId).collect(Collectors.toSet());

        //类型信息
        Map<String, List<UserTypeEnum>> userTypeMap = userTypeClient.mapTypeByUserIdSet(new IdSetRequest(responseUserIdSet));
        for (IamUserSimpleListResponseVo responseVo : pageResponse.getRecords()) {
            responseVo.setUserTypeList(userTypeMap.get(responseVo.getId()));
        }

        return pageResponse;
    }

    private List<IamUserRoleInfoResponse> assembleUserRoleInfo(List<IamUserRoleBusinessRelationListOutputDto> outputDtoList,
                                                               Map<String, IamUserRoleRelationListOutputDto> userRoleRelationMap,
                                                               Map<String, IamRoleDetailOutputDto> roleIdInfoMap,
                                                               Map<String, DataShopDetailOutputDto> shopIdInfoMap) {
        //组装角色信息
        List<IamUserRoleInfoResponse> userRoleList = new ArrayList<>();
        Map<String, IamUserRoleInfoResponse> userRoleMap = new HashMap<>();
        for (IamUserRoleBusinessRelationListOutputDto outputDto : outputDtoList) {
            //用户角色关系信息
            IamUserRoleRelationListOutputDto userRoleRelationListOutputDto = userRoleRelationMap
                    .get(outputDto.getUserRoleRelationId());

            String roleId = userRoleRelationListOutputDto.getRoleId();
            IamUserRoleInfoResponse userRoleInfoResponse = userRoleMap.get(roleId);

            if (null == userRoleInfoResponse) {
                IamRoleDetailOutputDto roleDetailOutputDto = roleIdInfoMap.get(roleId);
                userRoleInfoResponse = new IamUserRoleInfoResponse();
                userRoleInfoResponse.setId(roleDetailOutputDto.getId());
                userRoleInfoResponse.setType(roleDetailOutputDto.getType());
                userRoleInfoResponse.setName(roleDetailOutputDto.getName());
                userRoleInfoResponse.setCode(roleDetailOutputDto.getCode());
                userRoleMap.put(roleId, userRoleInfoResponse);
                userRoleList.add(userRoleInfoResponse);
            }

            if (UserRoleBusinessTypeEnum.SHOP == outputDto.getBusinessType()) {
                DataShopDetailOutputDto shopDetailOutputDto = shopIdInfoMap.get(outputDto.getBusinessId());
                List<IamUserRoleInfoResponse.BusinessInfo> shopList = userRoleInfoResponse.getShopList();
                if (null == shopList) {
                    shopList = new ArrayList<>();
                    userRoleInfoResponse.setShopList(shopList);
                }

                IamUserRoleInfoResponse.BusinessInfo businessInfo = new IamUserRoleInfoResponse.BusinessInfo();
                businessInfo.setId(outputDto.getId());
                businessInfo.setCode(shopDetailOutputDto.getCode());
                businessInfo.setName(shopDetailOutputDto.getName());
                businessInfo.setSort(outputDto.getSort());
                shopList.add(businessInfo);
            }
        }
        return userRoleList;
    }

    private Map<String, DataShopDetailOutputDto> getShopIdInfoMap(List<IamUserRoleBusinessRelationListOutputDto> outputDtoList) {
        Set<String> idSet = new HashSet<>();
        for (IamUserRoleBusinessRelationListOutputDto outputDto : outputDtoList) {
            if (UserRoleBusinessTypeEnum.SHOP == outputDto.getBusinessType()) {
                idSet.add(outputDto.getBusinessId());
            }
        }
        if (CollectionUtil.isEmpty(idSet)) {
            return Map.of();
        }
        return shopClient.mapByIdSet(new IdSetRequest(idSet));
    }


    /**
     * 管理员修改密码记录日志，并失效所有token
     */
    private void blackAuthToken4AdminUpdatePassword(String userId,
                                                    HttpServletRequest request) {
        UserLoginLogDetailOutputDto detailOutputDto = loginLogClient.getLoginSuccessByUserId(userId);
        List<String> hasProcessLoginLogList = blackAllAvailableToken(machineJwtUtil, userId, loginLogClient, customerRedisCommands);

        //新增修改密码日志
        UserDetailOutputDto userSimple = userClient.detail(new IdRequest(userId));
        IamUserLoginLogCreateInputDto inputDto = LoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(AuthActionEnum.ADMIN_CHANGE_PASSWORD);
        inputDto.setAuthResult(AuthResultEnum.SUCCESS);
        if (null != detailOutputDto) {
            inputDto.setAuthMethod(detailOutputDto.getAuthMethod());
            inputDto.setAccessTokenId(detailOutputDto.getAccessTokenId());
            inputDto.setAccessTokenExpire(detailOutputDto.getAccessTokenExpire());
            inputDto.setAccessToken(detailOutputDto.getAccessToken());
        } else {
            inputDto.setAuthMethod(AuthMethodEnum.USERNAME_PASSWORD);
        }

        //记录被联动处理的日志ID
        inputDto.setDescription(JSON.toJSONString(hasProcessLoginLogList));
        LoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);
    }
}
