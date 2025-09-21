package com.machine.client.hrm.department;

import com.machine.client.hrm.department.dto.output.HrmDepartmentDetailOutputDto;
import com.machine.client.hrm.department.dto.output.HrmDepartmentExpansionListOutputDto;
import com.machine.client.hrm.department.dto.output.HrmDepartmentListOutputDto;
import com.machine.client.hrm.department.dto.output.HrmDepartmentTreeOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-hrm-service/machine-hrm-service/server/hrm/department",
        configuration = OpenFeignDefaultConfig.class)
public interface IHrmDepartmentClient {

    @PostMapping("detail_by_id")
    HrmDepartmentDetailOutputDto detailById(@RequestBody @Validated IdRequest request);

    /**
     * 查询所有可用部门
     */
    @GetMapping("list_all")
    List<HrmDepartmentListOutputDto> listAll();

    @GetMapping("tree_all_simple")
    HrmDepartmentTreeOutputDto treeAllSimple();

    @PostMapping("map_department_expansion_by_departmentIdSet")
    Map<String, HrmDepartmentExpansionListOutputDto> mapDepartmentExpansionByDepartmentIdSet(@RequestBody @Validated IdSetRequest idSetRequest);
}
