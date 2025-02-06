package com.machine.client.hrm.employee.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HrmEmployeeBatchInsertInputDto {

    @NotNull(message = "员工不能为空")
    private List<HrmEmployeeCreateInputDto> inputDtoList;

    public HrmEmployeeBatchInsertInputDto(List<HrmEmployeeCreateInputDto> inputDtoList) {
        this.inputDtoList = inputDtoList;
    }
}
