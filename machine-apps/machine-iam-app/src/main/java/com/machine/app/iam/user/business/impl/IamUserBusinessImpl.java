package com.machine.app.iam.user.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.machine.app.iam.user.controller.vo.request.IamAdminChangePasswordRequestVo;
import com.machine.app.iam.user.business.IIamUserBusiness;
import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.IamUserRoleInfoResponse;
import com.machine.app.iam.user.controller.vo.response.IamUserSimpleListResponseVo;
import com.machine.app.iam.user.controller.vo.response.ShopUserSimpleListResponseVo;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.dto.output.DataShopDetailOutputDto;
import com.machine.client.data.supplier.ISupplierCompanyClient;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanySimpleListOutputDto;
import com.machine.client.hrm.employee.IHrmEmployeeDefaultClient;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeListOutputDto;
import com.machine.client.iam.organization.dto.input.IamUserRoleTargetQueryListInputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationSimpleOutputDto;
import com.machine.client.iam.organization.dto.output.IamUserRoleTargetListOutputDto;
import com.machine.client.iam.role.IIamRoleClient;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.IIamUserRoleTargetClient;
import com.machine.client.iam.user.IIamUserTypeClient;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserListOutputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogDetailOutputDto;
import com.machine.sdk.common.envm.iam.OrganizationTypeEnum;
import com.machine.sdk.common.envm.iam.UserRoleTargetTypeEnum;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
import com.machine.sdk.common.envm.iam.auth.AuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.AuthMethodEnum;
import com.machine.sdk.common.envm.iam.auth.AuthResultEnum;
import com.machine.sdk.common.envm.iam.role.CompanyDefaultRoleEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
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
    private MachineJwtUtil machineJwtUtil;

    @Autowired
    private RedisCacheHrmDepartment departmentCache;

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IIamUserRoleTargetClient userRoleTargetClient;

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
    private ISupplierCompanyClient supplierCompanyClient;


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
        IamUserRoleTargetQueryListInputDto input = new IamUserRoleTargetQueryListInputDto();
        input.setTargetType(UserRoleTargetTypeEnum.ORGANIZATION);
        input.setTargetIdSet(organizationIdSet);
        List<IamUserRoleTargetListOutputDto> outputDtoList = userRoleTargetClient.listByCondition(input);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return Set.of();
        }
        return outputDtoList.stream().map(IamUserRoleTargetListOutputDto::getUserId).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIdByRoleIdSet(Set<String> roleIdSet) {
        if (CollectionUtil.isEmpty(roleIdSet)) {
            return Set.of();
        }

        List<IamUserRoleTargetListOutputDto> outputDtoList = userRoleTargetClient.listByRoleIdSet(new IdSetRequest(roleIdSet));
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return Set.of();
        }

        return outputDtoList.stream().map(IamUserRoleTargetListOutputDto::getUserId).collect(Collectors.toSet());
    }

    /**
     * 用户角色信息
     */
    @Override
    public List<IamUserRoleInfoResponse> getUserRoleList(String userId) {
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


    @Override
    public Map<String, List<IamUserRoleInfoResponse>> getUserRoleListMap(Set<String> userIdSet) {
        List<IamUserRoleTargetListOutputDto> roleTargetListOutputDtoList = userRoleTargetClient.listByUserIdSet(new IdSetRequest(userIdSet));
        if (CollectionUtil.isEmpty(roleTargetListOutputDtoList)) {
            return Map.of();
        }

        //角色信息
        Map<String, IamRoleDetailOutputDto> roleMap = getRoleMap(roleTargetListOutputDtoList);
        //组织信息
        Map<String, IamOrganizationSimpleOutputDto> organizationMap = getOrganizationMap(roleTargetListOutputDtoList);
        //门店信息
        Map<String, DataShopDetailOutputDto> shopMap = getShopMap(roleTargetListOutputDtoList);
        //公司信息
        Map<String, DataSupplierCompanySimpleListOutputDto> companyMap = getCompanyMap(roleTargetListOutputDtoList);


        //根据UserId拆分Map集合
        Map<String, List<IamUserRoleTargetListOutputDto>> userRoleTargetMap = new HashMap<>();
        for (IamUserRoleTargetListOutputDto outputDto : roleTargetListOutputDtoList) {
            String userId = outputDto.getUserId();
            List<IamUserRoleTargetListOutputDto> outputDtoList = userRoleTargetMap.computeIfAbsent(userId, k -> new ArrayList<>());
            outputDtoList.add(outputDto);
        }

        //组装返回信息
        Map<String, List<IamUserRoleInfoResponse>> userRoleResponseMap = new HashMap<>();
        for (Map.Entry<String, List<IamUserRoleTargetListOutputDto>> entity : userRoleTargetMap.entrySet()) {
            String userId = entity.getKey();
            List<IamUserRoleTargetListOutputDto> outputDtoList = entity.getValue();
            userRoleResponseMap.put(userId, assembleUserRoleInfo(outputDtoList, roleMap, organizationMap, shopMap, companyMap));
        }
        return userRoleResponseMap;
    }

    @Override
    public ShopUserSimpleListResponseVo listByShopId(IamUserQueryListByShopIdRequestVo request) {
        //查询门店角色Id
        List<String> roleIdList = roleClient.listIdByType(RoleTypeEnum.SHOP);
        roleIdList.add(CompanyDefaultRoleEnum.AREA_MANAGER.name().toLowerCase());
        roleIdList.add(CompanyDefaultRoleEnum.INSPECTOR.name().toLowerCase());

        //查询用户信息
        //查询组织关联的用户信息
        IamUserRoleTargetQueryListInputDto input = new IamUserRoleTargetQueryListInputDto();
        input.setTargetType(UserRoleTargetTypeEnum.SHOP);
        input.setTargetIdSet(Set.of(request.getShopId()));
        input.setRoleIdSet(new HashSet<>(roleIdList));
        List<IamUserRoleTargetListOutputDto> outputDtoList = userRoleTargetClient.listByCondition(input);

        ShopUserSimpleListResponseVo responseVo = new ShopUserSimpleListResponseVo();
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return responseVo;
        }


        //查询用户信息
        Set<String> userIdSet = outputDtoList.stream().map(IamUserRoleTargetListOutputDto::getUserId).collect(Collectors.toSet());
        Map<String, UserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

        //类型信息
        Map<String, List<UserTypeEnum>> userTypeMap = userTypeClient.mapTypeByUserIdSet(new IdSetRequest(userIdSet));

        //角色信息
        Map<String, List<IamUserRoleInfoResponse>> userRoleResponseMap = getUserRoleListMap(userIdSet);


        //组装数据
        List<ShopUserSimpleListResponseVo.ShopUserInfo> shopUserInfoList = new ArrayList<>();
        for (UserDetailOutputDto dto : userMap.values()) {
            ShopUserSimpleListResponseVo.ShopUserInfo shopUserInfo = new ShopUserSimpleListResponseVo.ShopUserInfo();
            shopUserInfo.setId(dto.getId());
            shopUserInfo.setName(dto.getName());
            shopUserInfo.setCode(dto.getCode());
            shopUserInfo.setPhone(dto.getPhone());
            shopUserInfo.setUserTypeList(userTypeMap.get(dto.getId()));
            shopUserInfo.setUserRoleList(userRoleResponseMap.get(dto.getId()));
            shopUserInfoList.add(shopUserInfo);
        }


        //区域经理/督导
        List<ShopUserSimpleListResponseVo.ShopUserInfo> areaManagementList = new ArrayList<>();
        //门店员工
        List<ShopUserSimpleListResponseVo.ShopUserInfo> storeManagerList = new ArrayList<>();

        for (ShopUserSimpleListResponseVo.ShopUserInfo info : shopUserInfoList) {
            List<IamUserRoleInfoResponse> userRoleList = info.getUserRoleList();
            boolean addAreaManagement = false;
            boolean addStoreManager = false;
            for (IamUserRoleInfoResponse userRole : userRoleList) {
                if (userRole.getRoleType().equals(RoleTypeEnum.SHOP)) {
                    if (!addAreaManagement) {
                        addAreaManagement = true;
                        storeManagerList.add(info);
                    }
                } else {
                    if (!addStoreManager) {
                        addStoreManager = true;
                        areaManagementList.add(info);
                    }
                }

                if (addAreaManagement && addStoreManager) {
                    break;
                }
            }
        }

        responseVo.setAreaManagementList(areaManagementList);
        responseVo.setStoreManagerList(storeManagerList);
        return responseVo;
    }


    @Override
    public PageResponse<IamUserSimpleListResponseVo> pageSimpled(IamUserQueryPageSimpleRequestVo request) {
        //组装ShopId集合
        boolean compute = false;
        Set<String> finallyqueryUserIdSet = new HashSet<>();

        //组装ShopId集合(组织)
        Set<String> organizationIdSet = request.getOrganizationIdSet();
        if (CollectionUtil.isNotEmpty(organizationIdSet)) {
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
            if (CollectionUtil.isEmpty(userIdSet)) {
                return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
            } else {
                //取交集
                if (CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
                    finallyqueryUserIdSet.addAll(userIdSet);
                } else {
                    finallyqueryUserIdSet.retainAll(userIdSet);
                }
            }
        }

        //组装UserId集合(角色)
        if ((!compute || CollectionUtil.isNotEmpty(finallyqueryUserIdSet))
                && CollectionUtil.isNotEmpty(request.getRoleIdSet())) {
            compute = true;
            Set<String> userIdSet = getIdByRoleIdSet(request.getRoleIdSet());
            if (CollectionUtil.isEmpty(userIdSet)) {
                return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
            } else {
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


    private List<IamUserRoleInfoResponse> assembleUserRoleInfo(List<IamUserRoleTargetListOutputDto> singleUserTargetInfoList,
                                                               Map<String, IamRoleDetailOutputDto> roleMap,
                                                               Map<String, IamOrganizationSimpleOutputDto> organizationMap,
                                                               Map<String, DataShopDetailOutputDto> shopMap,
                                                               Map<String, DataSupplierCompanySimpleListOutputDto> companyMap) {
        //组装角色信息
        List<IamUserRoleInfoResponse> userRoleList = new ArrayList<>();
        Map<String, IamUserRoleInfoResponse> userRoleMap = new HashMap<>();
        for (IamUserRoleTargetListOutputDto dto : singleUserTargetInfoList) {
            IamUserRoleInfoResponse userRoleInfoResponse = userRoleMap.get(dto.getRoleId());
            if (null == userRoleInfoResponse) {
                IamRoleDetailOutputDto roleDetailOutputDto = roleMap.get(dto.getRoleId());
                userRoleInfoResponse = new IamUserRoleInfoResponse();
                userRoleInfoResponse.setRoleId(roleDetailOutputDto.getId());
                userRoleInfoResponse.setRoleType(roleDetailOutputDto.getType());
                userRoleInfoResponse.setRoleName(roleDetailOutputDto.getName());
                userRoleInfoResponse.setRoleCode(roleDetailOutputDto.getCode());
                userRoleMap.put(dto.getRoleId(), userRoleInfoResponse);
                userRoleList.add(userRoleInfoResponse);
            }

            if (UserRoleTargetTypeEnum.ORGANIZATION == dto.getTargetType()) {
                IamOrganizationSimpleOutputDto outputDto = organizationMap.get(dto.getTargetId());
                List<IamUserRoleInfoResponse.TargetInfo> organizationList = userRoleInfoResponse.getOrganizationList();
                if (null == organizationList) {
                    organizationList = new ArrayList<>();
                    userRoleInfoResponse.setOrganizationList(organizationList);
                }

                IamUserRoleInfoResponse.TargetInfo targetInfo = new IamUserRoleInfoResponse.TargetInfo();
                targetInfo.setId(outputDto.getId());
                targetInfo.setCode(outputDto.getCode());
                targetInfo.setName(outputDto.getName());
                targetInfo.setSort(dto.getSort());
                organizationList.add(targetInfo);
            } else if (UserRoleTargetTypeEnum.SHOP == dto.getTargetType()) {
                DataShopDetailOutputDto outputDto = shopMap.get(dto.getTargetId());
                List<IamUserRoleInfoResponse.TargetInfo> shopList = userRoleInfoResponse.getShopList();
                if (null == shopList) {
                    shopList = new ArrayList<>();
                    userRoleInfoResponse.setShopList(shopList);
                }

                IamUserRoleInfoResponse.TargetInfo targetInfo = new IamUserRoleInfoResponse.TargetInfo();
                targetInfo.setId(outputDto.getId());
                targetInfo.setCode(outputDto.getCode());
                targetInfo.setName(outputDto.getName());
                targetInfo.setSort(dto.getSort());
                shopList.add(targetInfo);
            } else if (UserRoleTargetTypeEnum.COMPANY == dto.getTargetType()) {
                DataSupplierCompanySimpleListOutputDto outputDto = companyMap.get(dto.getTargetId());
                List<IamUserRoleInfoResponse.TargetInfo> companyList = userRoleInfoResponse.getCompanytList();
                if (null == companyList) {
                    companyList = new ArrayList<>();
                    userRoleInfoResponse.setCompanytList(companyList);
                }

                IamUserRoleInfoResponse.TargetInfo targetInfo = new IamUserRoleInfoResponse.TargetInfo();
                targetInfo.setId(outputDto.getId());
                targetInfo.setCode(outputDto.getCode());
                targetInfo.setName(outputDto.getName());
                targetInfo.setSort(dto.getSort());
                companyList.add(targetInfo);
            }
        }
        return userRoleList;
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

    private void extractedUserIdByOrganizationIdSet(Set<String> organizationIdSet, Set<String> finallyqueryShopIdSet) {
        for (OrganizationTypeEnum type : OrganizationTypeEnum.values()) {
            if (organizationIdSet.contains(type.getName() + SEPARATOR_COLON + ORGANIZATION_VIRTUAL_NODE)) {
                //未分配节点
                List<String> userIdList = userClient.listNotBindOrganization(new DataUserNotBindOrganizationInputDto(type));
                if (CollectionUtil.isNotEmpty(userIdList)) {
                    finallyqueryShopIdSet.addAll(userIdList);
                }
                break;
            }
        }

        //递归查询子节点
        Set<String> recursionOrganizationIdSet = organizationCache.recursionListSubIds(organizationIdSet);

        if (CollectionUtil.isNotEmpty(recursionOrganizationIdSet)) {
            Set<String> userIdSet = getIdByOrganizationIdSet(recursionOrganizationIdSet);
            finallyqueryShopIdSet.addAll(userIdSet);
        }
    }
}
