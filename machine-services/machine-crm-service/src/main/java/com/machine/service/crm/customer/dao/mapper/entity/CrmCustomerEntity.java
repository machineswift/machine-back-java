package com.machine.service.crm.customer.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.base.GenderEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;

@Data
@NoArgsConstructor
@TableName("t_crm_customer")
@EqualsAndHashCode(callSuper = true)
public class CrmCustomerEntity extends BaseEntity {

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 身份证号
     */
    @TableField("identity_card_number")
    private String identityCardNumber;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 性别
     */
    @TableField("gender")
    private GenderEnum gender;

}