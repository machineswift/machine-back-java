package com.machine.service.data.shop.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.shop.*;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@TableName("t_data_shop")
@EqualsAndHashCode(callSuper = true)
public class DataShopEntity extends BaseEntity {

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 经营状态：门店的商业生命周期阶段
     */
    @TableField("business_status")
    private DataShopBusinessStatusEnum businessStatus;

    /**
     * 运营状态：日常营业的可操作性状态
     */
    @TableField("operation_status")
    private DataShopOperationStatusEnum operationStatus;

    /**
     * 物理状态：门店物理空间的可用性
     */
    @TableField("physical_status")
    private DataShopPhysicalStatusEnum physicalStatus;

    /**
     * 国家编码
     */
    @TableField("country_code")
    private String countryCode;

    /**
     * 省编码
     */
    @TableField("province_code")
    private String provinceCode;

    /**
     * 市编码
     */
    @TableField("city_code")
    private String cityCode;

    /**
     * 区编码
     */
    @TableField("area_code")
    private String areaCode;

    /**
     * 详细地址
     */
    @TableField("address")
    private String address;

    /**
     * 经度
     */
    @TableField("latitude")
    private BigDecimal latitude;

    /**
     * 纬度
     */
    @TableField("longitude")
    private BigDecimal longitude;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

}
