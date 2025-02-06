package com.machine.client.data.employee.dto.output;

import com.machine.sdk.common.envm.data.CompanyEmployeeStatusEnum;
import lombok.Data;

@Data
public class CompanyEmployeeListOutputDto {

    private String id;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 员工状态
     */
    private CompanyEmployeeStatusEnum employeeStatus;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private long updateTime;
}
