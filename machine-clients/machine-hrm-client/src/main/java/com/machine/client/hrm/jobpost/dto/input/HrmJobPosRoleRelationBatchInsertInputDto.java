package com.machine.client.hrm.jobpost.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HrmJobPosRoleRelationBatchInsertInputDto {

    @NotNull(message = "职务角色关系不能为空")
    private List<HrmJobPostRoleRelationCreateInputDto> inputDtoList;

    public HrmJobPosRoleRelationBatchInsertInputDto(List<HrmJobPostRoleRelationCreateInputDto> inputDtoList) {
        this.inputDtoList = inputDtoList;
    }
}
