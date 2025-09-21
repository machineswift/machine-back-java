package com.machine.service.hrm.department.service;

import com.machine.client.hrm.department.dto.input.HrmDepartmentCreateInputDto;
import com.machine.client.hrm.department.dto.output.*;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IDepartmentService {

    String create(HrmDepartmentCreateInputDto inputDto);

    HrmDepartmentDetailOutputDto detailById(IdRequest request);

    List<HrmDepartmentListOutputDto> listAll();

    HrmDepartmentTreeOutputDto treeAllSimple();

    Map<String, HrmDepartmentExpansionListOutputDto> mapDepartmentExpansionByDepartmentIdSet(IdSetRequest idSetRequest);
}
