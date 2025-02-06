package com.machine.client.hrm.department.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HrmDepartmentBatchInsertInputDto {

    @NotNull(message = "部门不能为空")
    private List<DepartmentCreateInputDto> inputDtoList;

    public HrmDepartmentBatchInsertInputDto(List<DepartmentCreateInputDto> inputDtoList) {
        this.inputDtoList = inputDtoList;
    }
}
