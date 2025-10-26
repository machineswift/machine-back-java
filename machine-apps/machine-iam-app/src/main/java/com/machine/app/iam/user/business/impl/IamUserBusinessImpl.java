package com.machine.app.iam.user.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.machine.app.iam.user.controller.vo.request.IamUserUpdatePasswordRequestVo;
import com.machine.app.iam.user.business.IIamUserBusiness;
import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.IamUserDetailResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamUserExpandListResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamUserRoleInfoResponse;
import com.machine.app.iam.user.controller.vo.response.IamUserSimpleListResponseVo;
import com.machine.client.data.file.IDataDownloadClient;
import com.machine.client.data.file.dto.input.DataDownloadContentDto;
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
import com.machine.sdk.common.envm.iam.IamUserRoleBusinessTypeEnum;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthMethodEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthResultEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.starter.redis.cache.hrm.RedisCacheHrmDepartment;
import com.machine.starter.redis.cache.iam.RedisCacheIamOrganization;
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

import static com.machine.sdk.common.constant.CommonConstant.SEPARATOR_COLON;
import static com.machine.sdk.common.constant.CommonIamConstant.Organization.DATA_ORGANIZATION_ROOT_PARENT_ID;
import static com.machine.sdk.common.constant.CommonIamConstant.Organization.DATA_ORGANIZATION_VIRTUAL_NODE;
import static com.machine.sdk.common.constant.CommonIamConstant.User.ROOT_USER_ID;
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
    private IDataDownloadClient downloadClient;

    @Autowired
    private IIamUserLoginLogClient loginLogClient;

    @Autowired
    private IHrmEmployeeDefaultClient employeeDefaultClient;

    @Autowired
    private IIamUserRoleRelationClient userRoleRelationClient;

    @Autowired
    private IIamUserOrganizationRelationClient iamUserOrganizationRelationClient;

    @Autowired
    private IIamUserRoleBusinessRelationClient iamUserRoleBusinessRelationClient;


    @Override
    public String create(IamUserCreateRequestVo request) {
        request.setUsername(request.getUsername().trim());
        request.setName(request.getName().trim());

        IamUserCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserCreateInputDto.class);
        return userClient.create(inputDto);
    }

    @Override
    public void update(IamUserUpdateRequestVo request) {
        request.setUsername(request.getUsername().trim());
        request.setName(request.getName().trim());

        if (ROOT_USER_ID.equals(request.getId())) {
            throw new IamBusinessException("iam.user.business.update.rootUser", "不能修改超级管理员数据");
        }
        IamUserUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserUpdateInputDto.class);
        userClient.update(inputDto);
    }

    @Override
    public void updateStatus(IamUserUpdateStatusRequestVo request) {
        if (ROOT_USER_ID.equals(request.getId())) {
            throw new IamBusinessException("iam.user.business.updateStatus.rootUser", "不能修改超级管理员状态");
        }
        IamUserUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserUpdateStatusInputDto.class);
        userClient.updateStatus(inputDto);
    }

    @Override
    public void updatePhone(IamUserUpdatePhoneRequestVo request) {
        if (ROOT_USER_ID.equals(request.getId())) {
            throw new IamBusinessException("iam.user.business.updatePhone.rootUser", "不能修超级管理员改手机号");
        }
        IamUserUpdatePhoneInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserUpdatePhoneInputDto.class);
        userClient.updatePhone(inputDto);
    }

    @Override
    public void updatePassword(IamUserUpdatePasswordRequestVo request) {
        if (ROOT_USER_ID.equals(request.getId())) {
            throw new IamBusinessException("iam.user.business.updatePassword.rootUser", "不能修改超级管理员密码");
        }
        userClient.updatePassword(new IamUserUpdatePasswordInputDto(request.getId(),
                passwordEncoder.encode(request.getNewPassword())));
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        blackAuthToken4AdminUpdatePassword(request.getId(), servletRequest);
    }

    @Override
    public void updatePermission(IamUserUpdatePermissionRequestVo request) {
        if (ROOT_USER_ID.equals(request.getId())) {
            throw new IamBusinessException("iam.user.business.updatePermission.rootUser", "不能修改超级管理员权限");
        }
        IamUserUpdatePermissionInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserUpdatePermissionInputDto.class);
        userClient.updatePermission(inputDto);
    }

    @Override
    public void extractedUserIdByOrganizationIdSet(IamOrganizationTypeEnum organizationType,
                                                   Set<String> organizationIdSet,
                                                   Set<String> finallyqueryShopIdSet) {

        Set<String> organizationUserIdSet = new HashSet<>();
        for (IamOrganizationTypeEnum type : IamOrganizationTypeEnum.values()) {
            if (organizationIdSet.contains(type.getName() + SEPARATOR_COLON + DATA_ORGANIZATION_VIRTUAL_NODE)) {
                //未分配节点
                List<String> userIdList = userClient.listNotBindOrganization(new IamDataUserNotBindOrganizationInputDto(type));
                if (CollectionUtil.isNotEmpty(userIdList)) {
                    organizationUserIdSet.addAll(userIdList);
                }
                break;
            }
        }

        //递归查询子节点
        Set<String> recursionOrganizationIdSet = organizationCache.recursionListSubIds(organizationType, organizationIdSet);

        if (CollectionUtil.isNotEmpty(recursionOrganizationIdSet)) {
            Set<String> userIdSet = userClient.getIdByOrganizationIdSet(new IdSetRequest(recursionOrganizationIdSet));
            organizationUserIdSet.addAll(userIdSet);
        }

        //取交集
        if (CollectionUtil.isEmpty(finallyqueryShopIdSet)) {
            finallyqueryShopIdSet.addAll(organizationUserIdSet);
        } else {
            finallyqueryShopIdSet.retainAll(organizationUserIdSet);
        }

    }

    @Override
    public boolean computeFinallyQueryUserIdSet(Set<String> shopIdSet,
                                                Set<String> departmentIdSet,
                                                IamOrganizationTypeEnum organizationType,
                                                Set<String> organizationIdSet,
                                                Set<String> roleIdSet,
                                                Set<String> finallyqueryUserIdSet) {
        boolean compute = false;

        //组装UserId集合(门店)
        if (CollectionUtil.isNotEmpty(shopIdSet)) {
            compute = true;
            Set<String> userIdSet = userClient.getIdByShopIdSet(new IdSetRequest(shopIdSet));
            if (CollectionUtil.isNotEmpty(userIdSet)) {
                finallyqueryUserIdSet.addAll(userIdSet);
            }
        }

        //组装UserId集合(组织)
        if ((!compute || CollectionUtil.isNotEmpty(organizationIdSet))
                && CollectionUtil.isNotEmpty(organizationIdSet)) {
            boolean containRootId = false;
            for (IamOrganizationTypeEnum type : IamOrganizationTypeEnum.values()) {
                if (organizationIdSet.contains(type.getName().toLowerCase())) {
                    //包含根组织直接返回
                    containRootId = true;
                    break;
                }
            }

            if (organizationIdSet.contains(DATA_ORGANIZATION_ROOT_PARENT_ID)) {
                containRootId = true;
            }

            if (!containRootId) {
                compute = true;
                extractedUserIdByOrganizationIdSet(organizationType, organizationIdSet, finallyqueryUserIdSet);
            }
        }

        //组装UserId集合(部门)
        if ((!compute || CollectionUtil.isNotEmpty(finallyqueryUserIdSet))
                && CollectionUtil.isNotEmpty(departmentIdSet)) {
            compute = true;
            Set<String> recursionDepartmentIdSet = departmentCache.recursionListSubIdSet(departmentIdSet);
            Set<String> userIdSet = new HashSet<>(getIdByDepartmentIdSet(recursionDepartmentIdSet));
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
        if ((!compute || CollectionUtil.isNotEmpty(finallyqueryUserIdSet))
                && CollectionUtil.isNotEmpty(roleIdSet)) {
            compute = true;
            Set<String> userIdSet = userClient.getIdByRoleIdSet(new IdSetRequest(roleIdSet));
            if (CollectionUtil.isNotEmpty(userIdSet)) {
                //取交集
                if (CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
                    finallyqueryUserIdSet.addAll(userIdSet);
                } else {
                    finallyqueryUserIdSet.retainAll(userIdSet);
                }
            }
        }
        return compute;
    }

    @Override
    public IamUserDetailResponseVo detail(IdRequest request) {
        IamUserDetailOutputDto outputDto = userClient.detail(request);
        if (outputDto == null) {
            return null;
        }

        IamUserDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), IamUserDetailResponseVo.class);

        {//用户关联的组织数据
            List<IamUserOrganizationRelationOutputDto> outputDtoList = iamUserOrganizationRelationClient.listByUserId(request);
            if (CollectionUtil.isNotEmpty(outputDtoList)) {
                Map<IamOrganizationTypeEnum, Set<String>> organizationIdMap = outputDtoList.stream()
                        .collect(Collectors.groupingBy(
                                IamUserOrganizationRelationOutputDto::getOrganizationType,
                                Collectors.mapping(
                                        IamUserOrganizationRelationOutputDto::getOrganizationId,
                                        Collectors.toSet()
                                )
                        ));
                responseVo.setOrganizationIdMap(organizationIdMap);
            }
        }

        {//用户关联的角色信息
            responseVo.setUserRoleInfoList(getUserRoleList(request.getId()));
        }

        {//填充修改人创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(outputDto.getCreateBy());
            userIdSet.add(outputDto.getUpdateBy());
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }

        return responseVo;
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
    public PageResponse<IamUserSimpleListResponseVo> pageSimple(IamUserQueryPageRequestVo request) {
        //组装UserId集合
        Set<String> finallyqueryUserIdSet = new HashSet<>();
        boolean compute = computeFinallyQueryUserIdSet(request.getShopIdSet(), request.getDepartmentIdSet(),
                request.getOrganizationType(), request.getOrganizationIdSet(), request.getRoleIdSet(), finallyqueryUserIdSet);

        if (compute && CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
            return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
        }

        IamUserQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserQueryPageInputDto.class);
        inputDto.setUserIdSet(finallyqueryUserIdSet);

        //分页查询用户信息
        PageResponse<IamUserListOutputDto> pageOutputDto = userClient.selectPage(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(
                    pageOutputDto.getCurrent(),
                    pageOutputDto.getSize(),
                    pageOutputDto.getTotal());
        }

        return new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), IamUserSimpleListResponseVo.class));

    }

    @Override
    public PageResponse<IamUserExpandListResponseVo> pageExpand(IamUserQueryPageRequestVo request) {
        //组装UserId集合
        Set<String> finallyqueryUserIdSet = new HashSet<>();
        boolean compute = computeFinallyQueryUserIdSet(request.getShopIdSet(), request.getDepartmentIdSet(),
                request.getOrganizationType(), request.getOrganizationIdSet(), request.getRoleIdSet(), finallyqueryUserIdSet);

        if (compute && CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
            return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
        }

        IamUserQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserQueryPageInputDto.class);
        inputDto.setUserIdSet(finallyqueryUserIdSet);

        //分页查询用户信息
        PageResponse<IamUserListOutputDto> pageOutputDto = userClient.selectPage(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(
                    pageOutputDto.getCurrent(),
                    pageOutputDto.getSize(),
                    pageOutputDto.getTotal());
        }

        PageResponse<IamUserExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), IamUserExpandListResponseVo.class));

        {  //创建人、修改文姓名
            Set<String> userIdSet = pageResponse.getRecords().stream().map(IamUserExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(pageResponse.getRecords().stream().map(IamUserExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (IamUserExpandListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }

        return pageResponse;
    }

    @Override
    public void export(IamUserQueryPageRequestVo request) {
        //组装UserId集合
        Set<String> finallyqueryUserIdSet = new HashSet<>();
        boolean compute = computeFinallyQueryUserIdSet(request.getShopIdSet(), request.getDepartmentIdSet(),
                request.getOrganizationType(), request.getOrganizationIdSet(), request.getRoleIdSet(), finallyqueryUserIdSet);

        if (compute && CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
            throw new IamBusinessException("iam.user.business.export.emptyResult", "结果为空");
        }

        IamUserQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserQueryPageInputDto.class);
        inputDto.setUserIdSet(finallyqueryUserIdSet);

        //创建下载任务
        DataDownloadContentDto downloadTask = new DataDownloadContentDto();
        downloadTask.setClassName(IIamUserClient.class.getName());
        downloadTask.setMethodName("exportUser");
        downloadTask.setParamsClassName(IamUserExportInputDto.class.getName());
        downloadTask.setJsonParams(JSONUtil.toJsonStr(inputDto));
        downloadClient.createTask(downloadTask);

        //MaterialDto material = userClient.exportUser(inputDto);
    }


    private List<IamUserRoleInfoResponse> assembleUserRoleInfo(List<IamUserRoleBusinessRelationListOutputDto> outputDtoList,
                                                               Map<String, IamUserRoleRelationListOutputDto> userRoleRelationMap,
                                                               Map<String, IamRoleDetailOutputDto> roleIdInfoMap,
                                                               Map<String, DataShopDetailOutputDto> shopIdInfoMap) {
        //组装角色信息
        List<IamUserRoleInfoResponse> userRoleList = new ArrayList<>();
        Map<String, IamUserRoleInfoResponse> userRoleMap = new HashMap<>();

        for (IamUserRoleRelationListOutputDto relation : userRoleRelationMap.values()) {
            IamRoleDetailOutputDto outputDto = roleIdInfoMap.get(relation.getRoleId());
            IamUserRoleInfoResponse userRoleInfoResponse = new IamUserRoleInfoResponse();
            userRoleInfoResponse.setId(outputDto.getId());
            userRoleInfoResponse.setType(outputDto.getType());
            userRoleInfoResponse.setName(outputDto.getName());
            userRoleInfoResponse.setCode(outputDto.getCode());
            userRoleInfoResponse.setStatus(outputDto.getStatus());
            userRoleInfoResponse.setSort(relation.getSort());
            userRoleMap.put(outputDto.getId(), userRoleInfoResponse);
            userRoleList.add(userRoleInfoResponse);
        }

        for (IamUserRoleBusinessRelationListOutputDto outputDto : outputDtoList) {
            //用户角色关系信息
            IamUserRoleRelationListOutputDto userRoleRelationListOutputDto = userRoleRelationMap
                    .get(outputDto.getUserRoleRelationId());

            String roleId = userRoleRelationListOutputDto.getRoleId();
            IamUserRoleInfoResponse userRoleInfoResponse = userRoleMap.get(roleId);

            if (IamUserRoleBusinessTypeEnum.SHOP == outputDto.getBusinessType()) {
                DataShopDetailOutputDto shopDetailOutputDto = shopIdInfoMap.get(outputDto.getBusinessId());
                List<IamUserRoleInfoResponse.BusinessInfo> shopList = userRoleInfoResponse.getShopList();
                if (null == shopList) {
                    shopList = new ArrayList<>();
                    userRoleInfoResponse.setShopList(shopList);
                }

                IamUserRoleInfoResponse.BusinessInfo businessInfo = new IamUserRoleInfoResponse.BusinessInfo();
                businessInfo.setId(outputDto.getBusinessId());
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
            if (IamUserRoleBusinessTypeEnum.SHOP == outputDto.getBusinessType()) {
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
        IamUserLoginLogDetailOutputDto detailOutputDto = loginLogClient.getLoginSuccessByUserId(userId);
        List<String> hasProcessLoginLogList = blackAllAvailableToken(machineJwtUtil, userId, loginLogClient, customerRedisCommands);

        //新增修改密码日志
        IamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(userId));
        IamUserLoginLogCreateInputDto inputDto = LoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(IamAuthActionEnum.ADMIN_CHANGE_PASSWORD);
        inputDto.setAuthMethod(IamAuthMethodEnum.NULL);
        inputDto.setAuthResult(IamAuthResultEnum.SUCCESS);
        if (null != detailOutputDto) {
            inputDto.setAccessTokenId(detailOutputDto.getAccessTokenId());
            inputDto.setAccessTokenExpire(detailOutputDto.getAccessTokenExpire());
            inputDto.setAccessToken(detailOutputDto.getAccessToken());
        }

        //记录被联动处理的日志ID
        inputDto.setDescription(JSON.toJSONString(hasProcessLoginLogList));
        LoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);
    }
}
