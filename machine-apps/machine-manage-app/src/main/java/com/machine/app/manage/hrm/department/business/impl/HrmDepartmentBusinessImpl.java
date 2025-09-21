package com.machine.app.manage.hrm.department.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.hrm.department.business.IHrmDepartmentBusiness;
import com.machine.app.manage.hrm.department.business.bo.HrmDepartmentExpandTreeBo;
import com.machine.app.manage.hrm.department.controller.vo.response.*;
import com.machine.client.hrm.department.IHrmDepartmentClient;
import com.machine.client.hrm.department.dto.output.HrmDepartmentDetailOutputDto;
import com.machine.client.hrm.department.dto.output.HrmDepartmentExpansionListOutputDto;
import com.machine.client.hrm.department.dto.output.HrmDepartmentListOutputDto;
import com.machine.client.hrm.department.dto.output.HrmDepartmentTreeOutputDto;
import com.machine.client.hrm.employee.IHrmEmployeeDefaultClient;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeDetailOutputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.RedisCacheHrmDepartment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HrmDepartmentBusinessImpl implements IHrmDepartmentBusiness {

    @Autowired
    private RedisCacheHrmDepartment departmentCache;

    @Autowired
    private IHrmDepartmentClient departmentClient;

    @Autowired
    private IHrmEmployeeDefaultClient employeeDefaultClient;

    @Override
    public HrmDepartmentDetailResponseVo detail(IdRequest request) {
        HrmDepartmentDetailOutputDto outputDto = departmentClient.detailById(new IdRequest(request.getId()));
        if (null == outputDto) {
            return null;
        }

        HrmDepartmentDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), HrmDepartmentDetailResponseVo.class);

        //部门负责人信息
        // 查询部门扩展信息
        Set<String> departmentIdSet =  new HashSet<>();
        departmentIdSet.add(outputDto.getId());
        Map<String, HrmDepartmentExpansionListOutputDto> departmentExpansionMap = departmentClient.mapDepartmentExpansionByDepartmentIdSet(new IdSetRequest(departmentIdSet));

        HrmDepartmentExpansionListOutputDto hrmDepartmentExpansionListOutputDto = departmentExpansionMap.getOrDefault(responseVo.getId(), new HrmDepartmentExpansionListOutputDto());
        // 设立日期，生效日期
        responseVo.setEstablishDate(hrmDepartmentExpansionListOutputDto.getEstablishDate());
        responseVo.setStartDate(hrmDepartmentExpansionListOutputDto.getStarDate());
        //获取负责人ID集合
        Set<String> employeeIdSet = new HashSet<>();
        departmentExpansionMap.forEach((key, value) -> {
            if (value != null && StringUtils.isNotEmpty(value.getPersonInCharge())) {
                employeeIdSet.add(value.getPersonInCharge());
            }
        });
        if(!CollectionUtil.isEmpty(employeeIdSet)){
            //获取负责人用户信息
            Map<String, HrmEmployeeDetailOutputDto> hrmEmployeeDetailOutputDtoMap = employeeDefaultClient.mapByIdSet(new IdSetRequest(employeeIdSet));

            if (CollectionUtil.isNotEmpty(hrmEmployeeDetailOutputDtoMap)) {

                HrmEmployeeDetailOutputDto hrmEmployeeDetailOutputDto = hrmEmployeeDetailOutputDtoMap.get(hrmDepartmentExpansionListOutputDto.getPersonInCharge());
                if (hrmEmployeeDetailOutputDto != null) {
                    List<DepartmentChargeResponseVo> departmentChargeList = new ArrayList<>();
                    DepartmentChargeResponseVo departmentCharge = new DepartmentChargeResponseVo();
                    departmentCharge.setUserId(hrmEmployeeDetailOutputDto.getUserId());
                    departmentCharge.setEmployeeId(hrmEmployeeDetailOutputDto.getId());
                    departmentCharge.setName(hrmEmployeeDetailOutputDto.getName());
                    departmentChargeList.add(departmentCharge);
                    responseVo.setDepartmentChargeList(departmentChargeList);
                }

            }
        }

        List<HrmEmployeeListOutputDto> outputDtoList4Charge = employeeDefaultClient.list4ChargeByDepartmentId(new IdRequest(responseVo.getId()));
        if (CollectionUtil.isNotEmpty(outputDtoList4Charge)) {
            List<DepartmentChargeResponseVo> departmentChargeList = new ArrayList<>();
            for (HrmEmployeeListOutputDto dto : outputDtoList4Charge) {
                DepartmentChargeResponseVo departmentCharge = new DepartmentChargeResponseVo();
                departmentCharge.setUserId(dto.getUserId());
                departmentCharge.setEmployeeId(dto.getId());
                departmentCharge.setName(dto.getName());
                departmentChargeList.add(departmentCharge);
            }
            responseVo.setDepartmentChargeList(departmentChargeList);
        }

        //行政维度上级
        HrmDepartmentDetailOutputDto parentOutputDto = departmentClient.detailById(new IdRequest(responseVo.getParentId()));
        if (null != parentOutputDto) {
            HrmDepartmentDetailResponseVo.ParentDepartment parentDepartment = new HrmDepartmentDetailResponseVo.ParentDepartment();
            parentDepartment.setId(parentOutputDto.getId());
            parentDepartment.setCode(parentOutputDto.getCode());
            parentDepartment.setName(parentOutputDto.getName());
            responseVo.setParentDepartment(parentDepartment);
        }

        //获取部门以及子部门的Id
        departmentIdSet = new HashSet<>();
        departmentIdSet.add(request.getId());
        departmentIdSet.addAll(departmentCache.recursionListSubId(responseVo.getId()));

        //部门机构中员工的人数
        Integer employeeNumber = employeeDefaultClient.countByDepartmentIds(new IdSetRequest(departmentIdSet));
        responseVo.setEmployeeNumber(employeeNumber);

        {//层级
            HrmDepartmentTreeOutputDto treeOutputDto = departmentCache.treeAllSimple();
            //找到指定的节点
            HrmDepartmentTreeOutputDto treeNode = TreeUtil.findNode(treeOutputDto, outputDto.getId());
            //获取指定部门的所有父组织ID列表（list元素第一个是当前部门ID，最后一个是根部门ID，从左至右部门层级递增）
            List<String> parentIdList = new ArrayList<>();
            do {
                parentIdList.add(treeNode.getId());
                treeNode = TreeUtil.findNode(treeOutputDto, treeNode.getParentId());
            } while (null != treeNode);

            responseVo.setLevel(parentIdList.size());
        }

        return responseVo;
    }

    @Override
    public HrmDepartmentExpandTreeResponseVo treeAllExpand() {
        List<HrmDepartmentListOutputDto> outputDtoList = departmentClient.listAll();
        Set<String> departmentIdSet = outputDtoList.stream().map(HrmDepartmentListOutputDto::getId).collect(Collectors.toSet());

        //查询组织关联的用户信息
        List<HrmEmployeeListOutputDto> employeeDepartmentOutputDtoList = employeeDefaultClient
                .list(new HrmEmployeeQueryIListInputDto(departmentIdSet, null, false));

        //组装中间对象信息
        List<HrmDepartmentExpandTreeBo> expandTreeBoList = JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), HrmDepartmentExpandTreeBo.class);

        Map<String, HrmDepartmentExpandTreeBo> personMap = expandTreeBoList.stream()
                .collect(Collectors.toMap(HrmDepartmentExpandTreeBo::getId, p -> p));

        for (HrmEmployeeListOutputDto dto : employeeDepartmentOutputDtoList) {
            HrmDepartmentExpandTreeBo bo = personMap.get(dto.getDepartmentId());
            if (null == bo) {
                continue;
            }
            bo.getEmployeeIdSet().add(dto.getId());
            bo.setEmployeeNumber(bo.getEmployeeIdSet().size());
        }
        for (HrmDepartmentExpandTreeBo bo : expandTreeBoList) {
            bo.getDepartmentIdSet().add(bo.getId());
            bo.setDepartmentNumber(bo.getDepartmentIdSet().size());
        }

        {//部门负责人信息
            // 查询部门扩展信息
            Map<String, HrmDepartmentExpansionListOutputDto> departmentExpansionMap = departmentClient.mapDepartmentExpansionByDepartmentIdSet(new IdSetRequest(departmentIdSet));

            //获取负责人ID集合
            Set<String> employeeIdSet = new HashSet<>();
            departmentExpansionMap.forEach((key, value) -> {
                if(value != null&& StringUtils.isNotEmpty(value.getPersonInCharge())) {
                    employeeIdSet.add(value.getPersonInCharge());
                }
            });
            //获取负责人用户信息
            Map<String, HrmEmployeeDetailOutputDto> hrmEmployeeDetailOutputDtoMap  =employeeDefaultClient.mapByIdSet(new IdSetRequest(employeeIdSet));

            if (CollectionUtil.isNotEmpty(hrmEmployeeDetailOutputDtoMap)) {
                for (HrmDepartmentExpandTreeBo bo : expandTreeBoList) {
                    HrmDepartmentExpansionListOutputDto hrmDepartmentExpansionListOutputDto = departmentExpansionMap.getOrDefault(bo.getId(), new HrmDepartmentExpansionListOutputDto());
                    HrmEmployeeDetailOutputDto  hrmEmployeeDetailOutputDto = hrmEmployeeDetailOutputDtoMap.get(hrmDepartmentExpansionListOutputDto.getPersonInCharge());
                    if(hrmEmployeeDetailOutputDto !=null){
                        List<DepartmentChargeResponseVo> departmentChargeList = new ArrayList<>();
                        DepartmentChargeResponseVo departmentCharge = new DepartmentChargeResponseVo();
                        departmentCharge.setUserId(hrmEmployeeDetailOutputDto.getUserId());
                        departmentCharge.setEmployeeId(hrmEmployeeDetailOutputDto.getId());
                        departmentCharge.setName(hrmEmployeeDetailOutputDto.getName());
                        departmentChargeList.add(departmentCharge);
                        bo.setDepartmentChargeList(departmentChargeList);
                    }

                }
            }
        }

        //组装成中间对象树
        HrmDepartmentExpandTreeBo expandTreeBo = TreeUtil.buildTree(expandTreeBoList).getFirst();

        //tree 后序递归累计子节点的员工数据并计算出部门和员工数量
        postorderTraversalAndCountChildren(expandTreeBo);
        return JSONUtil.toBean(JSONUtil.toJsonStr(expandTreeBo), HrmDepartmentExpandTreeResponseVo.class);
    }

    @Override
    public HrmDepartmentTreeOutputDto treeAllSimple() {
        return departmentCache.treeAllSimple();
    }

    private void postorderTraversalAndCountChildren(HrmDepartmentExpandTreeBo node) {
        if (node == null) {
            return;
        }

        // Traverse left subtree
        for (HrmDepartmentExpandTreeBo child : node.getChildren()) {
            postorderTraversalAndCountChildren(child);
        }

        for (HrmDepartmentExpandTreeBo child : node.getChildren()) {
            node.getDepartmentIdSet().addAll(child.getDepartmentIdSet());
            node.setDepartmentNumber(node.getDepartmentIdSet().size());

            node.getEmployeeIdSet().addAll(child.getEmployeeIdSet());
            node.setEmployeeNumber(node.getEmployeeIdSet().size());
        }
    }
}
