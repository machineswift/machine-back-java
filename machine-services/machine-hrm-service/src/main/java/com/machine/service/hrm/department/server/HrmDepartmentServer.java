package com.machine.service.hrm.department.server;

import com.machine.client.hrm.department.IHrmDepartmentClient;
import com.machine.client.hrm.department.dto.output.DepartmentDetailOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentExpansionListOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentListOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentTreeOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.hrm.department.service.IDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/hrm/department")
public class HrmDepartmentServer implements IHrmDepartmentClient {
    @Autowired
    private IDepartmentService departmentService;

    @Override
    @PostMapping("detail_by_id")
    public DepartmentDetailOutputDto detailById(@RequestBody @Validated IdRequest request) {
        return departmentService.detailById(request);
    }

    @Override
    @GetMapping("list_all")
    public List<DepartmentListOutputDto> listAll() {
        return departmentService.listAll();
    }

    @Override
    @GetMapping("tree_all_simple")
    public DepartmentTreeOutputDto treeAllSimple() {
        return departmentService.treeAllSimple();
    }

    @Override
    public Map<String, DepartmentExpansionListOutputDto> mapDepartmentExpansionByDepartmentIdSet(IdSetRequest idSetRequest) {
        return departmentService.mapDepartmentExpansionByDepartmentIdSet(idSetRequest);
    }
}
