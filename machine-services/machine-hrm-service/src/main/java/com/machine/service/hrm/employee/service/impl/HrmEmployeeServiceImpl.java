package com.machine.service.hrm.employee.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeDetailOutputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.hrm.employee.dao.IEmployeeDao;
import com.machine.service.hrm.employee.dao.mapper.entity.EmployeeEntity;
import com.machine.service.hrm.employee.service.IHrmEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HrmEmployeeServiceImpl implements IHrmEmployeeService {

    @Autowired
    private IEmployeeDao employeeDao;

    @Override
    public Integer countByDepartmentIds(IdSetRequest request) {
        return employeeDao.countByDepartmentIds(request.getIdSet());
    }

    @Override
    public HrmEmployeeDetailOutputDto detailById(IdRequest request) {
        EmployeeEntity entity = employeeDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), HrmEmployeeDetailOutputDto.class);
    }

    @Override
    public HrmEmployeeDetailOutputDto getByUserId(IdRequest request) {
        EmployeeEntity entity = employeeDao.getByUserId(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), HrmEmployeeDetailOutputDto.class);
    }

    @Override
    public List<HrmEmployeeListOutputDto> list4Charge(IdRequest request) {
        String departmentId = request.getId();
        List<EmployeeEntity> entityList = employeeDao.list4Charge(departmentId);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), HrmEmployeeListOutputDto.class);
    }

    @Override
    public List<HrmEmployeeListOutputDto> list(HrmEmployeeQueryIListInputDto inputDto) {
        List<EmployeeEntity> entityList = employeeDao.list(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), HrmEmployeeListOutputDto.class);
    }

    @Override
    public Map<String, HrmEmployeeDetailOutputDto> mapByIdSet(IdSetRequest request) {
        List<EmployeeEntity> userEntityList = employeeDao.selectByIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(EmployeeEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), HrmEmployeeDetailOutputDto.class)));
    }

    @Override
    public Map<String, HrmEmployeeDetailOutputDto> mapByUserIdSet(IdSetRequest request) {
        List<EmployeeEntity> userEntityList = employeeDao.selectByUserIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(EmployeeEntity::getUserId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), HrmEmployeeDetailOutputDto.class)));
    }

    @Override
    public Map<String, List<HrmEmployeeDetailOutputDto>> map4ChargeByDepartmentIdSet(IdSetRequest request) {
        List<EmployeeEntity> userEntityList = employeeDao.listChargeByDepartmentIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(userEntityList)) {
            return Map.of();
        }
        //根据DepartmentId拆分Map集合
        Map<String, List<HrmEmployeeDetailOutputDto>> departmentIdEmployeeMap = new HashMap<>();
        for (EmployeeEntity entity : userEntityList) {
            String departmentId = entity.getDepartmentId();
            List<HrmEmployeeDetailOutputDto> outputDtoList = departmentIdEmployeeMap.computeIfAbsent(departmentId, k -> new ArrayList<>());
            outputDtoList.add(JSONUtil.toBean(JSONUtil.toJsonStr(entity), HrmEmployeeDetailOutputDto.class));
        }
        return departmentIdEmployeeMap;
    }

}
