package com.machine.client.iam.user.dto.output;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.crm.customer.GenderEnum;
import lombok.Data;

@Data
public class UserDetailOutputDto {

    private String id;

    /**
     * 用户名（系统账号）
     */
    private String userName;

    private StatusEnum status;

    private String code;

    private String name;

    private String phone;

    private GenderEnum gender;

    /**
     * 描述
     */
    private String description;

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
