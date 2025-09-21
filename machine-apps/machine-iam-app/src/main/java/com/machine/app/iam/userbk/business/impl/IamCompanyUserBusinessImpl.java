package com.machine.app.iam.userbk.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.user.business.impl.IamUserBusinessImpl;
import com.machine.app.iam.userbk.business.IIamCompanyUserBusiness;
import com.machine.app.iam.userbk.vo.request.IamCompanyUserQueryPageExpandRequestVo;
import com.machine.app.iam.userbk.vo.response.IamCompanyUserDetailResponseVo;
import com.machine.app.iam.userbk.vo.response.IamCompanyUserExpandListResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamUserRoleInfoResponse;
import com.machine.client.data.employee.IDataCompanyEmployeeClient;
import com.machine.client.data.employee.IDataShopEmployeeClient;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.input.DataShopEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.output.DataCompanyEmployeeDetailOutputDto;
import com.machine.client.data.franchisee.IDataFranchiseeClient;
import com.machine.client.data.supplier.IDataSupplierUserClient;
import com.machine.client.data.supplier.dto.input.DataFranchiseeListUserIdInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierListUserIdInputDto;
import com.machine.client.hrm.department.IHrmDepartmentClient;
import com.machine.client.hrm.department.dto.output.HrmDepartmentDetailOutputDto;
import com.machine.client.hrm.department.dto.output.HrmDepartmentSimpleOutputDto;
import com.machine.client.hrm.department.dto.output.HrmDepartmentTreeOutputDto;
import com.machine.client.hrm.employee.IHrmEmployeeDefaultClient;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeDetailOutputDto;
import com.machine.client.hrm.jobpost.IHrmJobPostClient;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostDetailOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserTypeClient;
import com.machine.client.iam.userbk.IIamUserBkClient;
import com.machine.client.iam.userbk.dto.input.IamCompanyUserQueryPageInputDto;
import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.sdk.common.envm.hrm.HrmEmployeeStatusEnum;
import com.machine.sdk.common.envm.data.DataShopEmployeeStatusEnum;
import com.machine.sdk.common.envm.iam.IamUserTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.RedisCacheHrmDepartment;
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
    private IamUserBusinessImpl userBusiness;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserBkClient userBkClient;

    @Autowired
    private IIamUserTypeClient userTypeClient;

    @Autowired
    private IHrmJobPostClient jobPostClient;

    @Autowired
    private IHrmDepartmentClient departmentClient;

    @Autowired
    private IDataCompanyEmployeeClient companyEmployeeClient;

    @Autowired
    private IDataShopEmployeeClient shopEmployeeClient;

    @Autowired
    private IDataFranchiseeClient franchiseeClient;

    @Autowired
    private IDataSupplierUserClient supplierUserClient;

    @Autowired
    private IHrmEmployeeDefaultClient employeeDefaultClient;

    @Override
    public IamCompanyUserDetailResponseVo detail(IdRequest request) {
        IamUserDetailOutputDto iamUserDetailOutputDto = userClient.detail(request);
        if (null == iamUserDetailOutputDto) {
            return null;
        }

        //不存对应类型，直接返回
        if (!userTypeClient.existsType(new IamUserTypeExistsTypeInputDto(request.getId(), IamUserTypeEnum.COMPANY))) {
            return null;
        }

        IamCompanyUserDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(iamUserDetailOutputDto), IamCompanyUserDetailResponseVo.class);

        //类型信息
        responseVo.setUserTypeList(userTypeClient.listTypeByUserId(new IdRequest(request.getId())));

        {
            //公司员工
            DataCompanyEmployeeDetailOutputDto outputDto = companyEmployeeClient.getByUserId(new IdRequest(request.getId()));
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
                    HrmDepartmentDetailOutputDto dto = departmentClient.detailById(new IdRequest(departmentId));
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
        Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());

        return responseVo;
    }

    @Override
    public PageResponse<IamCompanyUserExpandListResponseVo> pageExpand(IamCompanyUserQueryPageExpandRequestVo request) {
        //组装UserId集合
        Set<String> finallyqueryUserIdSet = new HashSet<>();
        boolean compute = userBusiness.computeFinallyQueryUserIdSet(request.getShopIdSet(), request.getDepartmentIdSet(),
                request.getOrganizationType(), request.getOrganizationIdSet(), request.getRoleIdSet(), finallyqueryUserIdSet);

        if (compute && CollectionUtil.isEmpty(finallyqueryUserIdSet)) {
            return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
        }

        IamCompanyUserQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamCompanyUserQueryPageInputDto.class);
        inputDto.setUserIdSet(finallyqueryUserIdSet);


        {//组装NotUserId集合
            Set<String> leftCompanyUserIdSet = companyEmployeeClient.listUserIdByCondition(new DataCompanyEmployeeListUserIdInputDto(HrmEmployeeStatusEnum.LEFT));
            if (CollectionUtil.isNotEmpty(leftCompanyUserIdSet)) {
                Set<String> shopUserIdSet = shopEmployeeClient.listUserIdByCondition(new DataShopEmployeeListUserIdInputDto(leftCompanyUserIdSet, DataShopEmployeeStatusEnum.FULL_TIME));
                Set<String> franchiseeShopUserIdSet = franchiseeClient.listUserIdByCondition(new DataFranchiseeListUserIdInputDto(leftCompanyUserIdSet));
                Set<String> supplierShopUserIdSet = supplierUserClient.listUserIdByCondition(new DataSupplierListUserIdInputDto(leftCompanyUserIdSet));

                Set<String> allUserIdSet = new HashSet<>(shopUserIdSet);
                allUserIdSet.addAll(franchiseeShopUserIdSet);
                allUserIdSet.addAll(supplierShopUserIdSet);

                if (CollectionUtil.isNotEmpty(allUserIdSet)) {
                    //取交集
                    Set<String> notUserIdSet = leftCompanyUserIdSet.stream()
                            .filter(allUserIdSet::contains)
                            .collect(Collectors.toSet());
                    inputDto.setNotUserIdSet(notUserIdSet);
                }
            }
        }

        //分页查询用户信息
        PageResponse<IamUserListOutputDto> pageOutputDto = userBkClient.pageCompanyUser(inputDto);
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
        Map<String, List<IamUserTypeEnum>> userTypeMap = userTypeClient.mapTypeByUserIdSet(new IdSetRequest(responseUserIdSet));
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
            Map<String, DataCompanyEmployeeDetailOutputDto> employeeUserIdMap = companyEmployeeClient.mapByUserIdSet(new IdSetRequest(responseUserIdSet));
            if (CollectionUtil.isNotEmpty(employeeUserIdMap)) {
                for (IamCompanyUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
                    DataCompanyEmployeeDetailOutputDto outputDto = employeeUserIdMap.get(responseVo.getId());
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

                Map<String, HrmDepartmentSimpleOutputDto> departmentMap = new HashMap<>();
                if (CollectionUtil.isNotEmpty(departmentIdSet)) {
                    departmentMap = departmentCache.mapByIdSet(new IdSetRequest(departmentIdSet));
                }

                Map<String, HrmEmployeeDetailOutputDto> leaderMap = new HashMap<>();
                if (CollectionUtil.isNotEmpty(leaderIdSet)) {
                    leaderMap = employeeDefaultClient.mapByIdSet(new IdSetRequest(leaderIdSet));
                }


                HrmDepartmentTreeOutputDto allTreeOutputDto = departmentCache.treeAllSimple();
                for (IamCompanyUserExpandListResponseVo responseVo : pageResponse.getRecords()) {
                    HrmEmployeeDetailOutputDto employeeDetailOutputDto = employeeUserIdMap.get(responseVo.getId());
                    if (null != employeeDetailOutputDto) {
                        HrmJobPostDetailOutputDto jobPostDetailOutputDto = jobPostMap.get(employeeDetailOutputDto.getJobPostId());
                        HrmDepartmentSimpleOutputDto hrmDepartmentSimpleOutputDto = departmentMap.get(employeeDetailOutputDto.getDepartmentId());
                        HrmEmployeeDetailOutputDto leaderDetailOutputDto = leaderMap.get(employeeDetailOutputDto.getLeaderId());

                        //职务信息
                        if (null != jobPostDetailOutputDto) {
                            responseVo.setJobPostNameList(Collections.singletonList(jobPostDetailOutputDto.getName()));
                        }

                        //部门信息
                        if (null != hrmDepartmentSimpleOutputDto) {
                            //找到指定的节点
                            HrmDepartmentTreeOutputDto targetTreeNode = TreeUtil.findNode(allTreeOutputDto, hrmDepartmentSimpleOutputDto.getId());
                            if (null != targetTreeNode) {
                                //获取指定组织的所有父组织名称列表（list元素第一个是根组织ID，最后一个是当前组织ID，从左至右组织层级减）
                                List<String> parentNameList = new ArrayList<>();
                                do {
                                    parentNameList.add(targetTreeNode.getName());
                                    targetTreeNode = TreeUtil.findNode(allTreeOutputDto, targetTreeNode.getParentId());
                                } while (null != targetTreeNode);

                                parentNameList.removeLast();
                                parentNameList = parentNameList.reversed();
                                responseVo.setDepartmentNameList(parentNameList);
                            }
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
        Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (IamCompanyUserExpandListResponseVo vo : pageResponse.getRecords()) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }
        return pageResponse;
    }
}
