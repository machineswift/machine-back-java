package com.machine.app.manage.hrm.department.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.hrm.department.business.IHrmDepartmentBusiness;
import com.machine.app.manage.hrm.department.business.bo.HrmDepartmentExpandTreeBo;
import com.machine.app.manage.hrm.department.controller.vo.response.*;
import com.machine.client.hrm.department.IHrmDepartmentClient;
import com.machine.client.hrm.department.dto.output.DepartmentDetailOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentListOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentTreeOutputDto;
import com.machine.client.hrm.employee.IHrmEmployeeDefaultClient;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeDetailOutputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.RedisCacheHrmDepartment;
import lombok.extern.slf4j.Slf4j;
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
    public void clearCache() {
        departmentClient.clearCache();
    }

    @Override
    public void sync() {
        departmentClient.sync();
    }

    @Override
    public HrmDepartmentDetailResponseVo detail(IdRequest request) {
        DepartmentDetailOutputDto outputDto = departmentClient.detailById(new IdRequest(request.getId()));
        if (null == outputDto) {
            return null;
        }

        HrmDepartmentDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), HrmDepartmentDetailResponseVo.class);

        //部门负责人信息
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
        DepartmentDetailOutputDto parentOutputDto = departmentClient.detailById(new IdRequest(responseVo.getParentId()));
        if (null != parentOutputDto) {
            HrmDepartmentDetailResponseVo.ParentDepartment parentDepartment = new HrmDepartmentDetailResponseVo.ParentDepartment();
            parentDepartment.setId(parentOutputDto.getId());
            parentDepartment.setCode(parentOutputDto.getCode());
            parentDepartment.setName(parentOutputDto.getName());
            responseVo.setParentDepartment(parentDepartment);
        }

        Set<String> departmentIdSet = new HashSet<>();

        {  //获取部门以及子部门的Id
            departmentIdSet.add(request.getId());
            departmentIdSet.addAll(departmentCache.recursionListSubId(responseVo.getId()));
        }

        {//部门机构中员工的人数
            Integer employeeNumber = employeeDefaultClient.countByDepartmentIds(new IdSetRequest(departmentIdSet));
            responseVo.setEmployeeNumber(employeeNumber);
        }

        {//层级
            DepartmentTreeOutputDto treeOutputDto = departmentCache.treeAllSimple();
            //找到指定的节点
            DepartmentTreeOutputDto treeNode = TreeUtil.findNode(treeOutputDto, outputDto.getId());
            //获取指定部门的所有父组织ID列表（list元素第一个是当前部门ID，最后一个是根部门ID，从左至右部门层级递增）
            List<String> parentIdList = new ArrayList<>();
            do {
                parentIdList.add(treeNode.getId());
                treeNode = TreeUtil.findNode(treeOutputDto, treeNode.getParentId());
            } while (null != treeNode);

            responseVo.setLevel(parentIdList.size());
        }

        {//层级
            DepartmentTreeOutputDto treeOutputDto = departmentCache.treeAllSimple();
            //找到指定的节点
            DepartmentTreeOutputDto treeNode = TreeUtil.findNode(treeOutputDto, outputDto.getId());
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
        List<DepartmentListOutputDto> outputDtoList = departmentClient.listAll();
        Set<String> departmentIdSet = outputDtoList.stream().map(DepartmentListOutputDto::getId).collect(Collectors.toSet());

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
            Map<String, List<HrmEmployeeDetailOutputDto>> map4Charge = employeeDefaultClient.map4ChargeByDepartmentIdSet(new IdSetRequest(departmentIdSet));
            if (CollectionUtil.isNotEmpty(map4Charge)) {
                for (HrmDepartmentExpandTreeBo bo : expandTreeBoList) {
                    List<HrmEmployeeDetailOutputDto> outputDtoList4Charge = map4Charge.getOrDefault(bo.getId(), new ArrayList<>());
                    List<DepartmentChargeResponseVo> departmentChargeList = new ArrayList<>();
                    for (HrmEmployeeDetailOutputDto dto : outputDtoList4Charge) {
                        DepartmentChargeResponseVo departmentCharge = new DepartmentChargeResponseVo();
                        departmentCharge.setUserId(dto.getUserId());
                        departmentCharge.setEmployeeId(dto.getId());
                        departmentCharge.setName(dto.getName());
                        departmentChargeList.add(departmentCharge);
                    }
                    bo.setDepartmentChargeList(departmentChargeList);
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
    public DepartmentTreeOutputDto treeAllSimple() {
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
