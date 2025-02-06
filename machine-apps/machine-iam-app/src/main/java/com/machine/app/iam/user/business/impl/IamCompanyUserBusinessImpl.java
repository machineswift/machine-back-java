package com.machine.app.iam.user.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.user.business.IIamCompanyUserBusiness;
import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.IamCompanyUserDetailResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamCompanyUserExpandListResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamUserRoleInfoResponse;
import com.machine.client.data.employee.ICompanyEmployeeClient;
import com.machine.client.data.employee.dto.output.CompanyEmployeeDetailOutputDto;
import com.machine.client.hrm.department.IHrmDepartmentClient;
import com.machine.client.hrm.department.dto.output.DepartmentDetailOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentSimpleOutputDto;
import com.machine.client.hrm.employee.IHrmEmployeeDefaultClient;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeDetailOutputDto;
import com.machine.client.hrm.jobpost.IHrmJobPostClient;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostDetailOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserTypeClient;
import com.machine.client.iam.user.dto.input.IamCompanyUserQueryPageInputDto;
import com.machine.client.iam.user.dto.input.IamCompanyUserUpdateInputDto;
import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserListOutputDto;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.starter.redis.cache.RedisCacheHrmDepartment;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class IamCompanyUserBusinessImpl implements IIamCompanyUserBusiness {

    @Autowired
    private RedisCacheHrmDepartment departmentCache;

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IamUserBusinessImpl userBusiness;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserTypeClient userTypeClient;

    @Autowired
    private IHrmJobPostClient jobPostClient;

    @Autowired
    private IHrmDepartmentClient departmentClient;

    @Autowired
    private ICompanyEmployeeClient companyEmployeeClient;

    @Autowired
    private IHrmEmployeeDefaultClient employeeDefaultClient;

    @Override
    public void update(IamCompanyUserUpdateRequestVo request) {
        IamCompanyUserUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamCompanyUserUpdateInputDto.class);
        userClient.updateCompanyUser(inputDto);
    }

    @Override
    public IamCompanyUserDetailResponseVo detail(IdRequest request) {
        UserDetailOutputDto userDetailOutputDto = userClient.detail(request);
        if (null == userDetailOutputDto) {
            return null;
        }

        //不存对应类型，直接返回
        if (!userTypeClient.existsType(new IamUserTypeExistsTypeInputDto(request.getId(), UserTypeEnum.COMPANY))) {
            return null;
        }

        IamCompanyUserDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(userDetailOutputDto), IamCompanyUserDetailResponseVo.class);

        //类型信息
        responseVo.setUserTypeList(userTypeClient.listTypeByUserId(new IdRequest(request.getId())));

        {
            //公司员工
            CompanyEmployeeDetailOutputDto outputDto = companyEmployeeClient.getByUserId(new IdRequest(request.getId()));
            if (null != outputDto) {
                responseVo.setCompanyEmployeeId(outputDto.getId());
                responseVo.setEmployeeStatus(outputDto.getEmployeeStatus());
            }
        }
        {
            //员工信息
            HrmEmployeeDetailOutputDto outputDto = employeeDefaultClient.getByUserId(request);
            if (null != outputDto) {
                //职务信息
                String jobPostId = outputDto.getJobPostId();
                if (StrUtil.isNotBlank(jobPostId)) {
                    HrmJobPostDetailOutputDto dto = jobPostClient.getByUserId(new IdRequest(jobPostId));
                    if (null != dto) {
                        responseVo.setJobPostNameList(Collections.singletonList(dto.getName()));
                    }
                }

                //部门信息
                String departmentId = outputDto.getDepartmentId();
                if (StrUtil.isNotBlank(departmentId)) {
                    DepartmentDetailOutputDto dto = departmentClient.detailById(new IdRequest(departmentId));
                    if (null != dto) {
                        responseVo.setDepartmentNameList(Collections.singletonList(dto.getName()));
                    }
                }

                //上级信息
                String leaderId = outputDto.getLeaderId();
                if (StrUtil.isNotBlank(leaderId)) {
                    HrmEmployeeDetailOutputDto dto = employeeDefaultClient.detailById(new IdRequest(leaderId));
                    if (null != dto) {
                        responseVo.setLeaderNameList(Collections.singletonList(dto.getName()));
                    }
                }
            }
        }

        //角色信息
        responseVo.setUserRoleList(userBusiness.getUserRoleList(request.getId()));

        //填充修改人创建人信息
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(responseVo.getCreateBy());
        userIdSet.add(responseVo.getUpdateBy());
        Map<String, UserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());

        return responseVo;
    }

    @Override
    public PageResponse<IamCompanyUserExpandListResponseVo> pageExpand(IamCompanyUserQueryPageExpandRequestVo request) {
        IamCompanyUserQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamCompanyUserQueryPageInputDto.class);

        //组装UserId集合
        Set<String> queryUserIdSet = new HashSet<>(userBusiness.getIdByDepartmentIdSet(request.getDepartmentIdSet()));
        if (CollectionUtil.isNotEmpty(request.getOrganizationIdSet())) {
            queryUserIdSet.addAll(userBusiness.getIdByOrganizationIdSet(
                    //递归查询所有子节点
                    organizationCache.recursionListSubIds(request.getOrganizationIdSet())));
        }
        queryUserIdSet.addAll(userBusiness.getIdByRoleIdSet(request.getRoleIdSet()));
        inputDto.setUserIdSet(queryUserIdSet);

        //分页查询用户信息
        PageResponse<UserListOutputDto> pageOutputDto = userClient.pageCompanyUser(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(
                    pageOutputDto.getCurrent(),
                    pageOutputDto.getSize(),
                    pageOutputDto.getTotal());
        }

        //转化结果集
        PageResponse<IamCompanyUserExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), IamCompanyUserExpandListResponseVo.class));
        Set<String> responseUserIdSet = pageResponse.getRecords().stream().map(IamCompanyUserExpandListResponseVo::getId).collect(Collectors.toSet());

        //类型信息
        Map<String, List<UserTypeEnum>> userTypeMap = userTypeClient.mapTypeByUserIdSet(new IdSetRequest(responseUserIdSet));
        for (IamCompanyUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
            responseVo.setUserTypeList(userTypeMap.get(responseVo.getId()));
        }

        //角色信息
        Map<String, List<IamUserRoleInfoResponse>> userRoleResponseMap = userBusiness.getUserRoleListMap(responseUserIdSet);
        for (IamCompanyUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
            responseVo.setUserRoleList(userRoleResponseMap.get(responseVo.getId()));
        }

        {
            //公司员工
            Map<String, CompanyEmployeeDetailOutputDto> employeeUserIdMap = companyEmployeeClient.mapByUserIdSet(new IdSetRequest(responseUserIdSet));
            if (CollectionUtil.isNotEmpty(employeeUserIdMap)) {
                for (IamCompanyUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
                    CompanyEmployeeDetailOutputDto outputDto = employeeUserIdMap.get(responseVo.getId());
                    responseVo.setCompanyEmployeeId(outputDto.getId());
                    responseVo.setEmployeeStatus(outputDto.getEmployeeStatus());
                }
            }
        }

        //员工信息
        {
            Map<String, HrmEmployeeDetailOutputDto> employeeUserIdMap = employeeDefaultClient.mapByUserIdSet(new IdSetRequest(responseUserIdSet));
            if (CollectionUtil.isNotEmpty(employeeUserIdMap)) {
                Collection<HrmEmployeeDetailOutputDto> outputDtoCollection = employeeUserIdMap.values();
                Set<String> jobPostIdSet = outputDtoCollection.stream().map(HrmEmployeeDetailOutputDto::getJobPostId).collect(Collectors.toSet());
                Set<String> departmentIdSet = outputDtoCollection.stream().map(HrmEmployeeDetailOutputDto::getDepartmentId).collect(Collectors.toSet());
                Set<String> leaderIdSet = outputDtoCollection.stream().map(HrmEmployeeDetailOutputDto::getLeaderId).collect(Collectors.toSet());

                Map<String, HrmJobPostDetailOutputDto> jobPostMap = new HashMap<>();
                if (CollectionUtil.isNotEmpty(jobPostIdSet)) {
                    jobPostMap = jobPostClient.mapByIdSet(new IdSetRequest(jobPostIdSet));
                }

                Map<String, DepartmentSimpleOutputDto> departmentMap = new HashMap<>();
                if (CollectionUtil.isNotEmpty(departmentIdSet)) {
                    departmentMap = departmentCache.mapByIdSet(new IdSetRequest(departmentIdSet));
                }

                Map<String, HrmEmployeeDetailOutputDto> leaderMap = new HashMap<>();
                if (CollectionUtil.isNotEmpty(leaderIdSet)) {
                    leaderMap = employeeDefaultClient.mapByIdSet(new IdSetRequest(leaderIdSet));
                }


                for (IamCompanyUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
                    HrmEmployeeDetailOutputDto employeeDetailOutputDto = employeeUserIdMap.get(responseVo.getId());
                    if (null != employeeDetailOutputDto) {
                        HrmJobPostDetailOutputDto jobPostDetailOutputDto = jobPostMap.get(employeeDetailOutputDto.getJobPostId());
                        DepartmentSimpleOutputDto departmentSimpleOutputDto = departmentMap.get(employeeDetailOutputDto.getDepartmentId());
                        HrmEmployeeDetailOutputDto leaderDetailOutputDto = leaderMap.get(employeeDetailOutputDto.getLeaderId());

                        //职务信息
                        if (null != jobPostDetailOutputDto) {
                            responseVo.setJobPostNameList(Collections.singletonList(jobPostDetailOutputDto.getName()));
                        }

                        //部门信息
                        if (null != departmentSimpleOutputDto) {
                            responseVo.setDepartmentNameList(Collections.singletonList(departmentSimpleOutputDto.getName()));
                        }

                        //上级信息
                        if (null != leaderDetailOutputDto) {
                            responseVo.setLeaderNameList(Collections.singletonList(leaderDetailOutputDto.getName()));
                        }
                    }

                }
            }
        }

        //创建人、修改文姓名
        Set<String> userIdSet = pageResponse.getRecords().stream().map(IamCompanyUserExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
        userIdSet.addAll(pageResponse.getRecords().stream().map(IamCompanyUserExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
        Map<String, UserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (IamCompanyUserExpandListResponseVo vo : pageResponse.getRecords()) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }
        return pageResponse;
    }
}
