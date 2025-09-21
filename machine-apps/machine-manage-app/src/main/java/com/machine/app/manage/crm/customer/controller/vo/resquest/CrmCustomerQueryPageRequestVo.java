package com.machine.app.manage.crm.customer.controller.vo.resquest;

import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CrmCustomerQueryPageRequestVo extends PageRequest {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "身份证号")
    private String identityCardNumber;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;

}
