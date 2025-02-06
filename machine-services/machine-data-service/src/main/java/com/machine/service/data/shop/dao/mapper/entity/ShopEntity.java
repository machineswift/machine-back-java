package com.machine.service.data.shop.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.shop.*;
import com.machine.sdk.common.envm.iam.BusinessDistrictTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@TableName("t_shop")
@EqualsAndHashCode(callSuper = true)
public class ShopEntity extends BaseEntity {

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 类型
     */
    @TableField("type")
    private ShopTypeEnum type;

    /**
     * 状态
     */
    @TableField("status")
    private ShopStatusEnum status;

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
     * 面积
     */
    @TableField("square_meters")
    private BigDecimal squareMeters;

    /**
     * 初始投资额
     */
    @TableField("initial_investment")
    private BigDecimal initialInvestment;

    /**
     * 开店日期
     */
    @TableField("opening_date")
    private Long openingDate;

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
     * 商圈类型
     */
    @TableField("business_district_type")
    private BusinessDistrictTypeEnum businessDistrictType;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

}
