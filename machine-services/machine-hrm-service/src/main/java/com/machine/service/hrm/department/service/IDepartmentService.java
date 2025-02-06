package com.machine.service.hrm.department.service;

import com.machine.client.hrm.department.dto.input.DepartmentCreateInputDto;
import com.machine.client.hrm.department.dto.output.DepartmentDetailOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentListOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentSimpleOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentTreeOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IDepartmentService {

    void sync(long lastSyncTime,
              long enSyncTime);

    void clearCache();

    String create(DepartmentCreateInputDto inputDto);

    DepartmentDetailOutputDto detailById(IdRequest request);

    List<DepartmentListOutputDto> listAll();

    List<DepartmentSimpleOutputDto> listSub4Recursion(IdRequest request);

    DepartmentTreeOutputDto treeAllSimple();

}
