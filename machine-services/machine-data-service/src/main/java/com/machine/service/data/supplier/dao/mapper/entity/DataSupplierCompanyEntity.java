package com.machine.service.data.supplier.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.data.DataSupplierBusinessCategoryEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_data_supplier_company")
public class DataSupplierCompanyEntity extends BaseEntity {

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 状态
     */
    @TableField("status")
    private StatusEnum status;

    /**
     * 供应商业务类别
     */
    @TableField("business_category")
    private DataSupplierBusinessCategoryEnum businessCategory;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 联系人
     */
    @TableField("contact_name")
    private String contactName;

    /**
     * 联系电话
     */
    @TableField("contact_phone")
    private String contactPhone;

    /**
     * 通讯地址
     */
    @TableField("correspondence_address")
    private String correspondenceAddress;

    /**
     * 财务信息
     */
    @TableField("financial_information")
    private String financialInformation;

    /**
     * 合同信息
     */
    @TableField("contract_information")
    private String contractInformation;

}