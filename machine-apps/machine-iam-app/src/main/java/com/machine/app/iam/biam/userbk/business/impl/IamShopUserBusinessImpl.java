package com.machine.app.iam.biam.userbk.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.user.business.impl.BIamUserBusinessImpl;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserRoleInfoResponse;
import com.machine.app.iam.biam.userbk.business.IIamShopUserBusiness;
import com.machine.app.iam.biam.userbk.vo.request.IamShopUserCreateRequestVo;
import com.machine.app.iam.biam.userbk.vo.request.IamShopUserQueryPageExpandRequestVo;
import com.machine.app.iam.biam.userbk.vo.request.IamShopUserUpdateRequestVo;
import com.machine.app.iam.biam.userbk.vo.response.IamShopUserDetailResponseVo;
import com.machine.app.iam.biam.userbk.vo.response.IamShopUserExpandListResponseVo;
import com.machine.app.iam.biam.userbk.vo.response.IamShopUserExportRequestVo;
import com.machine.client.data.employee.IDataShopEmployeeClient;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.DataShopEmployeeDetailOutputDto;
import com.machine.client.data.filecenter.download.IDataDownloadClient;
import com.machine.client.data.filecenter.download.dto.input.DataDownloadContentDto;
import com.machine.client.data.franchisee.IDataFranchiseeClient;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeDetailOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeHealthCertificateOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeIdentityCardOutputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.IBIamUserTypeClient;
import com.machine.client.iam.biam.user.dto.input.BIamUserTypeExistsTypeInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserListOutputDto;
import com.machine.client.iam.biam.userbk.IBIamUserBkClient;
import com.machine.client.iam.biam.userbk.dto.input.IamShopUserCreateInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserExportInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamShopUserQueryPageInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamShopUserUpdateInputDto;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.envm.biam.user.BIamUserTypeEnum;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonBIamConstant.Organization.DATA_ORGANIZATION_ROOT_PARENT_ID;

@Slf4j
@Component
public class IamShopUserBusinessImpl implements IIamShopUserBusiness {

    @Autowired
    private BIamUserBusinessImpl userBusiness;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IBIamUserBkClient userBkClient;

    @Autowired
    private IDataShopEmployeeClient shopEmployeeClient;

    @Autowired
    private IDataFranchiseeClient franchiseeClient;

    @Autowired
    private IBIamUserTypeClient userTypeClient;

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
        BIamUserDetailOutputDto iamUserDetailOutputDto = userClient.detail(request);
        if (null == iamUserDetailOutputDto) {
            return null;
        }

        //不存对应类型，直接返回
        if (!userTypeClient.existsType(new BIamUserTypeExistsTypeInputDto(request.getId(),
                List.of(BIamUserTypeEnum.SHOP, BIamUserTypeEnum.FRANCHISEE)))) {
            return null;
        }

        IamShopUserDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(iamUserDetailOutputDto), IamShopUserDetailResponseVo.class);

        { //类型信息
            responseVo.setUserTypeList(userTypeClient.listTypeByUserId(new IdRequest(request.getId())));
        }

        { //身份证、健康证
            if (responseVo.getUserTypeList().contains(BIamUserTypeEnum.SHOP)) {
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
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
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
        PageResponse<BIamUserListOutputDto> pageOutputDto = userBkClient.pageShopUser(inputDto);
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
        Map<String, List<BIamUserTypeEnum>> userTypeMap = userTypeClient.mapTypeByUserIdSet(new IdSetRequest(responseUserIdSet));
        for (IamShopUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
            responseVo.setUserTypeList(userTypeMap.get(responseVo.getId()));
        }

        //角色信息
        Map<String, List<BIamUserRoleInfoResponse>> userRoleResponseMap = userBusiness.getUserRoleListMap(responseUserIdSet);
        for (IamShopUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
            responseVo.setUserRoleList(userRoleResponseMap.get(responseVo.getId()));
        }

        //创建人、修改文姓名
        Set<String> userIdSet = pageResponse.getRecords().stream().map(IamShopUserExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
        userIdSet.addAll(pageResponse.getRecords().stream().map(IamShopUserExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
        Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
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
            throw new BIamBusinessException("iam.shopUser.business.export.emptyResult", "结果为空");
        }

        BIamUserExportInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserExportInputDto.class);
        inputDto.setUserIdSet(finallyqueryUserIdSet);

        //创建下载任务
        DataDownloadContentDto downloadTask = new DataDownloadContentDto();
        downloadTask.setClassName(IBIamUserClient.class.getName());
        downloadTask.setMethodName("exportShopUser");
        downloadTask.setParamsClassName(BIamUserExportInputDto.class.getName());
        downloadTask.setJsonParams(JSONUtil.toJsonStr(inputDto));
        downloadClient.createTask(downloadTask);
    }


    private boolean isCompute(BIamOrganizationTypeEnum organizationType,
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
                userBusiness.extractedUserIdByOrganizationIdSet(organizationType, organizationIdSet, finallyqueryUserIdSet);
            }
        }
        return compute;
    }
}
