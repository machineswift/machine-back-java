package com.machine.client.hrm.department.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HrmDepartmentBatchInsertInputDto {

    @NotNull(message = "部门不能为空")
    private List<HrmDepartmentCreateInputDto> inputDtoList;

    public HrmDepartmentBatchInsertInputDto(List<HrmDepartmentCreateInputDto> inputDtoList) {
        this.inputDtoList = inputDtoList;
    }
}
