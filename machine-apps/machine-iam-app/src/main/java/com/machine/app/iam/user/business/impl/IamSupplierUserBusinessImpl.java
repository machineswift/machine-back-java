package com.machine.app.iam.user.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.user.business.IIamSupplierUserBusiness;
import com.machine.app.iam.user.controller.vo.request.IamSupplierUserCreateRequestVo;
import com.machine.app.iam.user.controller.vo.request.IamSupplierUserQueryPageExpandRequestVo;
import com.machine.app.iam.user.controller.vo.request.IamSupplierUserUpdateRequestVo;
import com.machine.app.iam.user.controller.vo.response.*;
import com.machine.client.data.supplier.ISupplierCompanyClient;
import com.machine.client.data.supplier.ISupplierUserClient;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanySimpleListOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierDetailOutputDto;
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
public class IamSupplierUserBusinessImpl implements IIamSupplierUserBusiness {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IamUserBusinessImpl userBusiness;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserTypeClient userTypeClient;

    @Autowired
    private ISupplierUserClient supplierUserClient;

    @Autowired
    private ISupplierCompanyClient supplierCompanyClient;

    @Override
    public String create(IamSupplierUserCreateRequestVo request) {
        IamSupplierUserCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamSupplierUserCreateInputDto.class);
        return userClient.createSupplierUser(inputDto);
    }

    @Override
    public void update(IamSupplierUserUpdateRequestVo request) {
        IamSupplierUserUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamSupplierUserUpdateInputDto.class);
        userClient.updateSupplierUser(inputDto);
    }

    @Override
    public IamSupplierUserDetailResponseVo detail(IdRequest request) {
        UserDetailOutputDto userDetailOutputDto = userClient.detail(request);
        if (null == userDetailOutputDto) {
            return null;
        }

        //不存对应类型，直接返回
        if (!userTypeClient.existsType(new IamUserTypeExistsTypeInputDto(request.getId(), UserTypeEnum.SUPPLIER))) {
            return null;
        }

        IamSupplierUserDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(userDetailOutputDto), IamSupplierUserDetailResponseVo.class);

        {//类型信息
            responseVo.setUserTypeList(userTypeClient.listTypeByUserId(new IdRequest(request.getId())));
        }

        {//供应商信息
            DataSupplierDetailOutputDto outputDto = supplierUserClient.getByUserId(new IdRequest(request.getId()));
            responseVo.setSupplierId(outputDto.getId());
        }

        {//供应商公司信息
            List<DataSupplierCompanySimpleListOutputDto> outputDtoList = supplierCompanyClient
                    .listBySupplierId(new IdRequest(responseVo.getSupplierId()));
            if (CollectionUtil.isNotEmpty(outputDtoList)) {
                List<IamSupplierUserDetailResponseVo.Company> companyList = new ArrayList<>();
                for (DataSupplierCompanySimpleListOutputDto outputDto : outputDtoList) {
                    IamSupplierUserDetailResponseVo.Company company = new IamSupplierUserDetailResponseVo.Company();
                    company.setId(outputDto.getId());
                    company.setName(outputDto.getName());
                    companyList.add(company);
                }
                responseVo.setCompanyList(companyList);
            }
        }

        {//角色信息
            responseVo.setUserRoleList(userBusiness.getUserRoleList(request.getId()));
        }

        {//填充修改人创建人信息
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
    public PageResponse<IamSupplierUserExpandListResponseVo> pageExpand(IamSupplierUserQueryPageExpandRequestVo
                                                                                request) {
        IamSupplierUserQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamSupplierUserQueryPageInputDto.class);
        //组装UserId集合
        Set<String> queryUserIdSet = new HashSet<>();
        if (CollectionUtil.isNotEmpty(request.getOrganizationIdSet())) {
            queryUserIdSet.addAll(userBusiness.getIdByOrganizationIdSet(
                    //递归查询所有子节点
                    organizationCache.recursionListSubIds(request.getOrganizationIdSet())));
        }
        queryUserIdSet.addAll(userBusiness.getIdByRoleIdSet(request.getRoleIdSet()));
        inputDto.setUserIdSet(queryUserIdSet);

        //分页查询用户信息
        PageResponse<UserListOutputDto> pageOutputDto = userClient.pageSupplierUser(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(
                    pageOutputDto.getCurrent(),
                    pageOutputDto.getSize(),
                    pageOutputDto.getTotal());
        }

        //序列化
        PageResponse<IamSupplierUserExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), IamSupplierUserExpandListResponseVo.class));
        Set<String> responseUserIdSet = pageResponse.getRecords().stream().map(IamSupplierUserExpandListResponseVo::getId).collect(Collectors.toSet());

        //类型信息
        Map<String, List<UserTypeEnum>> userTypeMap = userTypeClient.mapTypeByUserIdSet(new IdSetRequest(responseUserIdSet));
        for (IamSupplierUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
            responseVo.setUserTypeList(userTypeMap.get(responseVo.getId()));
        }

        //角色信息
        Map<String, List<IamUserRoleInfoResponse>> userRoleResponseMap = userBusiness.getUserRoleListMap(responseUserIdSet);
        for (IamSupplierUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
            responseVo.setUserRoleList(userRoleResponseMap.get(responseVo.getId()));
        }

        //创建人、修改文姓名
        Set<String> userIdSet = pageResponse.getRecords().stream().map(IamSupplierUserExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
        userIdSet.addAll(pageResponse.getRecords().stream().map(IamSupplierUserExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
        Map<String, UserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (IamSupplierUserExpandListResponseVo vo : pageResponse.getRecords()) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }
        return pageResponse;
    }
}
