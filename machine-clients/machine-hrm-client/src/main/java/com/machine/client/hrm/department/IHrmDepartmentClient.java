package com.machine.client.hrm.department;

import com.machine.client.hrm.department.dto.output.DepartmentDetailOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentListOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentTreeOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-hrm-service/machine-hrm-service/server/hrm/department",
        configuration = OpenFeignDefaultConfig.class)
public interface IHrmDepartmentClient {

    @GetMapping("sync")
    void sync();

    /**
     * 清理缓存
     */
    @GetMapping("clear_cache")
    void clearCache();

    @PostMapping("detail_by_id")
    DepartmentDetailOutputDto detailById(@RequestBody @Validated IdRequest request);

    /**
     * 查询所有可用部门
     */
    @GetMapping("list_all")
    List<DepartmentListOutputDto> listAll();

    @GetMapping("tree_all_simple")
    DepartmentTreeOutputDto treeAllSimple();
}
