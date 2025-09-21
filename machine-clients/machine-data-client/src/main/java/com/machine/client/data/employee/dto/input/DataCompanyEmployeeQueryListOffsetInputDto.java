package com.machine.client.data.employee.dto.input;

import com.machine.sdk.common.envm.hrm.HrmEmployeeStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataCompanyEmployeeQueryListOffsetInputDto {

    @Schema(description = "公司员工状态（公司员工状态）")
    private HrmEmployeeStatusEnum employeeStatus;

    @Schema(description = "偏移量，不传的话取默认值。下一次请求取当前结果集最后一条数据的ID。")
    private String offset;

    @Min(10)
    @Max(100)
    @NotNull(message = "分页大小不能为空")
    @Schema(description = "支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，最大100。")
    private Integer size;
}
