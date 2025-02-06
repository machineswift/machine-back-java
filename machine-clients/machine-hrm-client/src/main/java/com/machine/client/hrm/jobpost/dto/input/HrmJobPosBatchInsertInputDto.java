package com.machine.client.hrm.jobpost.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HrmJobPosBatchInsertInputDto {

    @NotNull(message = "职务不能为空")
    private List<HrmJobPostCreateInputDto> inputDtoList;

    public HrmJobPosBatchInsertInputDto(List<HrmJobPostCreateInputDto> inputDtoList) {
        this.inputDtoList = inputDtoList;
    }
}
