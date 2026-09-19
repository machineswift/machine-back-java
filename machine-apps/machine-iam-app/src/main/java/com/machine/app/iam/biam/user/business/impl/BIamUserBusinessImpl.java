package com.machine.app.iam.biam.user.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.machine.app.iam.biam.user.controller.vo.request.*;
import com.machine.app.iam.biam.user.business.IBIamUserBusiness;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserDetailResponseVo;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserExpandListResponseVo;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserRoleInfoResponse;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserSimpleListResponseVo;
import com.machine.client.data.filecenter.download.IDataDownloadClient;
import com.machine.client.data.filecenter.download.dto.input.DataDownloadContentDto;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.dto.output.DataShopDetailOutputDto;
import com.machine.client.hrm.employee.IHrmEmployeeDefaultClient;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeListOutputDto;
import com.machine.client.iam.biam.log.IBIamUserLoginLogClient;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogDetailOutputDto;
import com.machine.client.iam.biam.role.IBIamRoleClient;
import com.machine.client.iam.biam.role.dto.output.BIamRoleDetailOutputDto;
import com.machine.client.iam.biam.user.*;
import com.machine.client.iam.biam.user.dto.input.*;
import com.machine.client.iam.biam.user.dto.output.*;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.biam.role.BIamUserRoleBusinessTypeEnum;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthActionEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthMethodEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthResultEnum;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.dto.base.ClientEnvironmentInfo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.tool.ClientEnvironmentUtil;
import com.machine.sdk.base.tool.UUIDv7;
import com.machine.starter.redis.cache.hrm.RedisHrmDepartmentCache;
import com.machine.starter.redis.cache.biam.RedisBIamOrganizationCache;
import com.machine.starter.redis.command.CustomerRedisCommands;
import com.machine.starter.security.util.MachineLoginLogUtil;
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

import static com.machine.sdk.base.constant.CommonConstant.SEPARATOR_COLON;
import static com.machine.sdk.base.constant.CommonBIamConstant.Organization.DATA_ORGANIZATION_ROOT_PARENT_ID;
import static com.machine.sdk.base.constant.CommonBIamConstant.Organization.DATA_ORGANIZATION_VIRTUAL_NODE;
import static com.machine.sdk.base.constant.CommonBIamConstant.User.ROOT_USER_ID;
import static com.machine.starter.security.util.MachineLoginLogUtil.blackAllAvailableToken;

@Slf4j
@Component
public class BIamUserBusinessImpl implements IBIamUserBusiness {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisHrmDepartmentCache departmentCache;

    @Autowired
    private RedisBIamOrganizationCache organizationCache;

    @Autowired
    private IDataShopClient shopClient;

    @Autowired
    private IBIamRoleClient roleClient;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IDataDownloadClient downloadClient;

    @Autowired
    private IBIamUserLoginLogClient loginLogClient;

    @Autowired
    private IHrmEmployeeDefaultClient employeeDefaultClient;

    @Autowired
    private IBIamUserRoleRelationClient userRoleRelationClient;

    @Autowired
    private IBIamUserOrganizationRelationClient iamUserOrganizationRelationClient;

    @Autowired
    private IBIamUserRoleBusinessRelationClient iamUserRoleBusinessRelationClient;


    @Override
    public String create(BIamUserCreateRequestVo request) {
        request.setUsername(request.getUsername().trim());
        request.setName(request.getName().trim());

        BIamUserCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserCreateInputDto.class);
        return userClient.create(inputDto);
    }

    @Override
    public void update(BIamUserUpdateRequestVo request) {
        request.setUsername(request.getUsername().trim());
        request.setName(request.getName().trim());

        if (ROOT_USER_ID.equals(request.getId())) {
            throw new BIamBusinessException("biam.user.business.update.rootUser", "不能修改超级管理员数据");
        }
        BIamUserUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserUpdateInputDto.class);
        userClient.update(inputDto);
    }

    @Override
    public void updateStatus(BIamUserUpdateStatusRequestVo request) {
        if (ROOT_USER_ID.equals(request.getId())) {
            throw new BIamBusinessException("biam.user.business.updateStatus.rootUser", "不能修改超级管理员状态");
        }
        BIamUserUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserUpdateStatusInputDto.class);
        userClient.updateStatus(inputDto);
    }

    @Override
    public void updatePhone(BIamUserUpdatePhoneRequestVo request) {
        if (ROOT_USER_ID.equals(request.getId())) {
            throw new BIamBusinessException("biam.user.business.updatePhone.rootUser", "不能修超级管理员改手机号");
        }
        BIamUserUpdatePhoneInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserUpdatePhoneInputDto.class);
        userClient.updatePhone(inputDto);
    }

    @Override
    public void updatePassword(BIamUserUpdatePasswordRequestVo request) {
        if (ROOT_USER_ID.equals(request.getId())) {
            throw new BIamBusinessException("biam.user.business.updatePassword.rootUser", "不能修改超级管理员密码");
        }
        userClient.updatePassword(new BIamUserUpdatePasswordInputDto(request.getId(),
                passwordEncoder.encode(request.getNewPassword())));
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        blackAuthToken4AdminUpdatePassword(request.getId(), servletRequest);
    }

    @Override
    public void updatePermission(BIamUserUpdatePermissionRequestVo request) {
        if (ROOT_USER_ID.equals(request.getId())) {
            throw new BIamBusinessException("biam.user.business.updatePermission.rootUser", "不能修改超级管理员权限");
        }
        BIamUserUpdatePermissionInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserUpdatePermissionInputDto.class);
        userClient.updatePermission(inputDto);
    }

    @Override
    public void extractedUserIdByOrganizationIdSet(BIamOrganizationTypeEnum organizationType,
                                                   Set<String> organizationIdSet,
                                                   Set<String> finallyqueryShopIdSet) {

        Set<String> organizationUserIdSet = new HashSet<>();
        for (BIamOrganizationTypeEnum type : BIamOrganizationTypeEnum.values()) {
            if (organizationIdSet.contains(type.getName() + SEPARATOR_COLON + DATA_ORGANIZATION_VIRTUAL_NODE)) {
                //未分配节点
                List<String> userIdList = userClient.listNotBindOrganization(new BIamDataUserNotBindOrganizationInputDto(type));
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
                                                BIamOrganizationTypeEnum organizationType,
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
            for (BIamOrganizationTypeEnum type : BIamOrganizationTypeEnum.values()) {
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
    public BIamUserDetailResponseVo detail(IdRequest request) {
        BIamUserDetailOutputDto outputDto = userClient.detail(request);
        if (outputDto == null) {
            return null;
        }

        BIamUserDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), BIamUserDetailResponseVo.class);

        {//用户关联的组织数据
            List<BIamUserOrganizationRelationOutputDto> outputDtoList = iamUserOrganizationRelationClient.listByUserId(request);
            if (CollectionUtil.isNotEmpty(outputDtoList)) {
                Map<BIamOrganizationTypeEnum, Set<String>> organizationIdMap = outputDtoList.stream()
                        .collect(Collectors.groupingBy(
                                BIamUserOrganizationRelationOutputDto::getOrganizationType,
                                Collectors.mapping(
                                        BIamUserOrganizationRelationOutputDto::getOrganizationId,
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
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
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
    public List<BIamUserRoleInfoResponse> getUserRoleList(String userId) {
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


    @Override
    public Map<String, List<BIamUserRoleInfoResponse>> getUserRoleListMap(Set<String> userIdSet) {
        List<BIamUserRoleRelationListOutputDto> userRoleRelationListOutputDtoList =
                userRoleRelationClient.listByUserIdSet(new IdSetRequest(userIdSet));
        if (CollectionUtil.isEmpty(userRoleRelationListOutputDtoList)) {
            return Map.of();
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

        //根据UserId拆分Map集合
        Map<String, List<BIamUserRoleBusinessRelationListOutputDto>> userRoleBusinessRelationMap = new HashMap<>();
        for (BIamUserRoleBusinessRelationListOutputDto outputDto : userRoleBusinessRelationListOutputDtoList) {
            String userId = userRoleRelationMap.get(outputDto.getUserRoleRelationId()).getUserId();
            List<BIamUserRoleBusinessRelationListOutputDto> outputDtoList = userRoleBusinessRelationMap
                    .computeIfAbsent(userId, k -> new ArrayList<>());
            outputDtoList.add(outputDto);
        }

        //组装返回信息
        Map<String, List<BIamUserRoleInfoResponse>> userRoleResponseMap = new HashMap<>();
        for (Map.Entry<String, List<BIamUserRoleBusinessRelationListOutputDto>> entity : userRoleBusinessRelationMap.entrySet()) {
            String userId = entity.getKey();
            userRoleResponseMap.put(userId, assembleUserRoleInfo(entity.getValue(), userRoleRelationMap, roleIdInfoMap, shopIdInfoMap));
        }
        return userRoleResponseMap;
    }

    @Override
    public PageResponse<BIamUserSimpleListResponseVo> pageSimple(BIamUserQueryPageRequestVo request) {
        //组装UserId集合
        Set<String> finallyqueryUserIdSet = new HashSet<>();
        boolean compute = computeFinallyQueryUserIdSet(request.getShopIdSet(), request.getDepartmentIdSet(),
                request.getOrganizationType(), request.getOrganizationIdSet(), request.getRoleIdSet(), finallyqueryUserIdSet);

        if (compute && CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
            return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
        }

        BIamUserQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserQueryPageInputDto.class);
        inputDto.setUserIdSet(finallyqueryUserIdSet);

        //分页查询用户信息
        PageResponse<BIamUserListOutputDto> pageOutputDto = userClient.selectPage(inputDto);
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
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), BIamUserSimpleListResponseVo.class));

    }

    @Override
    public PageResponse<BIamUserExpandListResponseVo> pageExpand(BIamUserQueryPageRequestVo request) {
        //组装UserId集合
        Set<String> finallyqueryUserIdSet = new HashSet<>();
        boolean compute = computeFinallyQueryUserIdSet(request.getShopIdSet(), request.getDepartmentIdSet(),
                request.getOrganizationType(), request.getOrganizationIdSet(), request.getRoleIdSet(), finallyqueryUserIdSet);

        if (compute && CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
            return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
        }

        BIamUserQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserQueryPageInputDto.class);
        inputDto.setUserIdSet(finallyqueryUserIdSet);

        //分页查询用户信息
        PageResponse<BIamUserListOutputDto> pageOutputDto = userClient.selectPage(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(
                    pageOutputDto.getCurrent(),
                    pageOutputDto.getSize(),
                    pageOutputDto.getTotal());
        }

        PageResponse<BIamUserExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), BIamUserExpandListResponseVo.class));

        {  //创建人、修改文姓名
            Set<String> userIdSet = pageResponse.getRecords().stream().map(BIamUserExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(pageResponse.getRecords().stream().map(BIamUserExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (BIamUserExpandListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }

        return pageResponse;
    }

    @Override
    public void export(BIamUserExportRequestVo request) {
        //组装UserId集合
        Set<String> finallyqueryUserIdSet = new HashSet<>();
        boolean compute = computeFinallyQueryUserIdSet(request.getShopIdSet(), request.getDepartmentIdSet(),
                request.getOrganizationType(), request.getOrganizationIdSet(), request.getRoleIdSet(),
                finallyqueryUserIdSet);

        if (CollectionUtil.isNotEmpty(request.getUserIdSet())) {
            if (compute) {
                if (CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
                    finallyqueryUserIdSet.addAll(request.getUserIdSet());
                } else {
                    finallyqueryUserIdSet.retainAll(request.getUserIdSet());
                }
            } else {
                finallyqueryUserIdSet = request.getUserIdSet();
            }
        }

        if (compute && CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
            throw new BIamBusinessException("biam.user.business.export.emptyResult", "结果为空");
        }

        BIamUserExportInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserExportInputDto.class);
        inputDto.setUserIdSet(finallyqueryUserIdSet);

        //创建下载任务
        String downloadId = UUIDv7.generateWithoutDashes();
        DataDownloadContentDto downloadTask = new DataDownloadContentDto();
        downloadTask.setId(downloadId);
        downloadTask.setModule(ModuleEnum.BIAM);
        downloadTask.setEntity(ModuleEntityEnum.BIAM_USER);
        downloadTask.setClassName(IBIamUserClient.class.getName());
        downloadTask.setMethodName("exportUser");
        downloadTask.setParamsClassName(BIamUserExportInputDto.class.getName());

        inputDto.setDownloadId(downloadId);
        downloadTask.setJsonParams(JSONUtil.toJsonStr(inputDto));

        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ClientEnvironmentInfo environmentInfo = ClientEnvironmentUtil.buildInfo(servletRequest);
        downloadTask.setFeatures(JSONUtil.toJsonStr(environmentInfo));

        downloadClient.createTask(downloadTask);
        //MaterialDto material = userClient.exportUser(inputDto);
    }


    private List<BIamUserRoleInfoResponse> assembleUserRoleInfo(List<BIamUserRoleBusinessRelationListOutputDto> outputDtoList,
                                                                Map<String, BIamUserRoleRelationListOutputDto> userRoleRelationMap,
                                                                Map<String, BIamRoleDetailOutputDto> roleIdInfoMap,
                                                                Map<String, DataShopDetailOutputDto> shopIdInfoMap) {
        //组装角色信息
        List<BIamUserRoleInfoResponse> userRoleList = new ArrayList<>();
        Map<String, BIamUserRoleInfoResponse> userRoleMap = new HashMap<>();

        for (BIamUserRoleRelationListOutputDto relation : userRoleRelationMap.values()) {
            BIamRoleDetailOutputDto outputDto = roleIdInfoMap.get(relation.getRoleId());
            BIamUserRoleInfoResponse userRoleInfoResponse = new BIamUserRoleInfoResponse();
            userRoleInfoResponse.setId(outputDto.getId());
            userRoleInfoResponse.setType(outputDto.getType());
            userRoleInfoResponse.setName(outputDto.getName());
            userRoleInfoResponse.setCode(outputDto.getCode());
            userRoleInfoResponse.setStatus(outputDto.getStatus());
            userRoleInfoResponse.setSort(relation.getSort());
            userRoleMap.put(outputDto.getId(), userRoleInfoResponse);
            userRoleList.add(userRoleInfoResponse);
        }

        for (BIamUserRoleBusinessRelationListOutputDto outputDto : outputDtoList) {
            //用户角色关系信息
            BIamUserRoleRelationListOutputDto userRoleRelationListOutputDto = userRoleRelationMap
                    .get(outputDto.getUserRoleRelationId());

            String roleId = userRoleRelationListOutputDto.getRoleId();
            BIamUserRoleInfoResponse userRoleInfoResponse = userRoleMap.get(roleId);

            if (BIamUserRoleBusinessTypeEnum.SHOP == outputDto.getBusinessType()) {
                DataShopDetailOutputDto shopDetailOutputDto = shopIdInfoMap.get(outputDto.getBusinessId());
                List<BIamUserRoleInfoResponse.BusinessInfo> shopList = userRoleInfoResponse.getShopList();
                if (null == shopList) {
                    shopList = new ArrayList<>();
                    userRoleInfoResponse.setShopList(shopList);
                }

                BIamUserRoleInfoResponse.BusinessInfo businessInfo = new BIamUserRoleInfoResponse.BusinessInfo();
                businessInfo.setId(outputDto.getBusinessId());
                businessInfo.setCode(shopDetailOutputDto.getCode());
                businessInfo.setName(shopDetailOutputDto.getName());
                businessInfo.setSort(outputDto.getSort());
                shopList.add(businessInfo);
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

    /**
     * 管理员修改密码记录日志，并失效所有token
     */
    private void blackAuthToken4AdminUpdatePassword(String userId,
                                                    HttpServletRequest request) {
        BIamUserLoginLogDetailOutputDto detailOutputDto = loginLogClient.getLoginSuccessByUserId(userId);
        List<String> hasProcessLoginLogList = blackAllAvailableToken(userId, loginLogClient, customerRedisCommands);

        //新增修改密码日志
        BIamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(userId));
        BIamUserLoginLogCreateInputDto inputDto = MachineLoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(BIamAuthActionEnum.ADMIN_CHANGE_PASSWORD);
        inputDto.setAuthMethod(BIamAuthMethodEnum.NULL);
        inputDto.setAuthResult(BIamAuthResultEnum.SUCCESS);
        if (null != detailOutputDto) {
            inputDto.setAccessTokenId(detailOutputDto.getAccessTokenId());
            inputDto.setAccessTokenExpire(detailOutputDto.getAccessTokenExpire());
        }

        //记录被联动处理的日志ID
        inputDto.setDescription(JSON.toJSONString(hasProcessLoginLogList));
        MachineLoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);
    }
}
