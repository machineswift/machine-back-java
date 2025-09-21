package com.machine.client.crm.customer.dto.output;

import com.machine.sdk.common.envm.crm.customer.CrmGenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class CrmCustomerListOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "身份证号")
    private String identityCardNumber;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "性别（CrmGenderEnum）")
    private CrmGenderEnum gender;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
