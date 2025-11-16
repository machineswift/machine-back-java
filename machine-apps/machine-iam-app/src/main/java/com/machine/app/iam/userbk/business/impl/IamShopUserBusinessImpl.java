package com.machine.app.iam.userbk.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.user.business.impl.IamUserBusinessImpl;
import com.machine.app.iam.userbk.business.IIamShopUserBusiness;
import com.machine.app.iam.user.controller.vo.response.*;
import com.machine.app.iam.userbk.vo.request.IamShopUserCreateRequestVo;
import com.machine.app.iam.userbk.vo.request.IamShopUserQueryPageExpandRequestVo;
import com.machine.app.iam.userbk.vo.request.IamShopUserUpdateRequestVo;
import com.machine.app.iam.userbk.vo.response.IamShopUserDetailResponseVo;
import com.machine.app.iam.userbk.vo.response.IamShopUserExpandListResponseVo;
import com.machine.app.iam.userbk.vo.response.IamShopUserExportRequestVo;
import com.machine.client.data.employee.IDataShopEmployeeClient;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.DataShopEmployeeDetailOutputDto;
import com.machine.client.data.file.IDataDownloadClient;
import com.machine.client.data.file.dto.input.DataDownloadContentDto;
import com.machine.client.data.franchisee.IDataFranchiseeClient;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeDetailOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeHealthCertificateOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeIdentityCardOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserTypeClient;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.client.iam.userbk.IIamUserBkClient;
import com.machine.client.iam.userbk.dto.input.IamShopUserCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserExportInputDto;
import com.machine.client.iam.userbk.dto.input.IamShopUserQueryPageInputDto;
import com.machine.client.iam.userbk.dto.input.IamShopUserUpdateInputDto;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.envm.iam.user.IamUserTypeEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonIamConstant.Organization.DATA_ORGANIZATION_ROOT_PARENT_ID;

@Slf4j
@Component
public class IamShopUserBusinessImpl implements IIamShopUserBusiness {

    @Autowired
    private IamUserBusinessImpl userBusiness;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserBkClient userBkClient;

    @Autowired
    private IDataShopEmployeeClient shopEmployeeClient;

    @Autowired
    private IDataFranchiseeClient franchiseeClient;

    @Autowired
    private IIamUserTypeClient userTypeClient;

    @Autowired
    private IDataDownloadClient downloadClient;

    @Override
    public String create(IamShopUserCreateRequestVo request) {
        IamShopUserCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamShopUserCreateInputDto.class);
        return userBkClient.createShopUser(inputDto);
    }

    @Override
    public void update(IamShopUserUpdateRequestVo request) {
        IamShopUserUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamShopUserUpdateInputDto.class);
        userBkClient.updateShopUser(inputDto);
    }

    @Override
    public IamShopUserDetailResponseVo detail(IdRequest request) {
        IamUserDetailOutputDto iamUserDetailOutputDto = userClient.detail(request);
        if (null == iamUserDetailOutputDto) {
            return null;
        }

        //不存对应类型，直接返回
        if (!userTypeClient.existsType(new IamUserTypeExistsTypeInputDto(request.getId(),
                List.of(IamUserTypeEnum.SHOP, IamUserTypeEnum.FRANCHISEE)))) {
            return null;
        }

        IamShopUserDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(iamUserDetailOutputDto), IamShopUserDetailResponseVo.class);

        { //类型信息
            responseVo.setUserTypeList(userTypeClient.listTypeByUserId(new IdRequest(request.getId())));
        }

        { //身份证、健康证
            if (responseVo.getUserTypeList().contains(IamUserTypeEnum.SHOP)) {
                DataShopEmployeeDetailOutputDto outputDto = shopEmployeeClient.getByUserId(request);

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
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }

        return responseVo;
    }

    @Override
    public PageResponse<IamShopUserExpandListResponseVo> pageExpand(IamShopUserQueryPageExpandRequestVo request) {
        //组装UserId集合
        Set<String> finallyqueryUserIdSet = new HashSet<>();
        boolean compute = isCompute(request.getOrganizationType(),request.getOrganizationIdSet(), request.getRoleIdSet(), finallyqueryUserIdSet);

        if (compute && CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
            return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
        }

        IamShopUserQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamShopUserQueryPageInputDto.class);
        inputDto.setUserIdSet(finallyqueryUserIdSet);

        //分页查询用户信息
        PageResponse<IamUserListOutputDto> pageOutputDto = userBkClient.pageShopUser(inputDto);
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
        Map<String, List<IamUserTypeEnum>> userTypeMap = userTypeClient.mapTypeByUserIdSet(new IdSetRequest(responseUserIdSet));
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
        Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (IamShopUserExpandListResponseVo vo : pageResponse.getRecords()) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }

        return pageResponse;
    }

    @Override
    public void export(IamShopUserExportRequestVo request) {
        //组装UserId集合
        Set<String> finallyqueryUserIdSet = new HashSet<>();
        boolean compute = isCompute(request.getOrganizationType(),request.getOrganizationIdSet(), request.getRoleIdSet(), finallyqueryUserIdSet);

        if (compute && CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
            throw new IamBusinessException("iam.shopUser.business.export.emptyResult", "结果为空");
        }

        IamUserExportInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserExportInputDto.class);
        inputDto.setUserIdSet(finallyqueryUserIdSet);

        //创建下载任务
        DataDownloadContentDto downloadTask = new DataDownloadContentDto();
        downloadTask.setClassName(IIamUserClient.class.getName());
        downloadTask.setMethodName("exportShopUser");
        downloadTask.setParamsClassName(IamUserExportInputDto.class.getName());
        downloadTask.setJsonParams(JSONUtil.toJsonStr(inputDto));
        downloadClient.createTask(downloadTask);
    }


    private boolean isCompute(IamOrganizationTypeEnum organizationType,
                              Set<String> organizationIdSet,
                              Set<String> roleIdSet,
                              Set<String> finallyqueryUserIdSet) {
        boolean compute = false;
        //组装UserId集合(角色)
        if (CollectionUtil.isNotEmpty(roleIdSet)) {
            compute = true;
            Set<String> userIdSet = userClient.getIdByRoleIdSet(new IdSetRequest(roleIdSet));
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
                userBusiness.extractedUserIdByOrganizationIdSet(organizationType, organizationIdSet, finallyqueryUserIdSet);
            }
        }
        return compute;
    }
}
