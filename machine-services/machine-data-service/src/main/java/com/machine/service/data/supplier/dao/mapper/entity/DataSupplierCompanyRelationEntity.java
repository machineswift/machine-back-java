package com.machine.service.data.supplier.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_data_supplier_company_relation")
public class DataSupplierCompanyRelationEntity extends BaseEntity {
    /**
     * 供应商Id
     */
    @TableField("supplier_id")
    private String supplierId;

    /**
     * 归属公司ID
     */
    @TableField("company_id")
    private String companyId;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}