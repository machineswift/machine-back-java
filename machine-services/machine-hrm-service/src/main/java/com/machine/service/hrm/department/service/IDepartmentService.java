package com.machine.service.hrm.department.service;

import com.machine.client.hrm.department.dto.input.DepartmentCreateInputDto;
import com.machine.client.hrm.department.dto.output.*;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IDepartmentService {

    void sync(long lastSyncTime,
              long enSyncTime);

    void syncDepartmentPersonInCharge();

    void clearCache();

    String create(DepartmentCreateInputDto inputDto);

    DepartmentDetailOutputDto detailById(IdRequest request);

    List<DepartmentListOutputDto> listAll();

    DepartmentTreeOutputDto treeAllSimple();

    Map<String, DepartmentExpansionListOutputDto> mapDepartmentExpansionByDepartmentIdSet(IdSetRequest idSetRequest);
}
