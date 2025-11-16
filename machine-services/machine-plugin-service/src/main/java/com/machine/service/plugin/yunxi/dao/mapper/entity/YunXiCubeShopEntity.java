package com.machine.service.plugin.yunxi.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@TableName("sc_shop")
public class YunXiCubeShopEntity {

    /**
     * 主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /*
     * 商户ID
     */
    @TableField(value = "seller_id")
    private Long sellerId;

    /*
     * 编码
     */
    @TableField(value = "code")
    private String code;

    /*
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /*
     * 1:线下门店；2：线上门店 3:直营 4:加盟  5:其他 6:直播
     */
    @TableField(value = "type")
    private Long type;

    /*
     * 1:炸串+臭豆腐；2：炸串+臭豆腐+炸鸡；3：炸串+臭豆腐+卷饼；4：炸串+臭豆腐+卷饼+炸鸡
     */
    @TableField(value = "business_type")
    private String businessType;

    /*
     * 店铺等级
     */
    @TableField(value = "level")
    private Integer level;

    /*
     * 联系人
     */
    @TableField(value = "contact_person")
    private String contactPerson;

    /*
     * 联系电话
     */
    @TableField(value = "contact_phone")
    private String contactPhone;

    /*
     * URL地址
     */
    @TableField(value = "shop_url")
    private String shopUrl;

    /*
     * 状态。
     * PENDING：待审核
     * NORMAL正常
     * BANNED封禁
     * CORRECTION整改
     */
    @TableField(value = "status")
    private String status;

    /*
     * 地址
     */
    @TableField(value = "address")
    private String address;

    /*
     * 省份编码
     */
    @TableField(value = "province_code")
    private String provinceCode;

    /*
     * 地市编码
     */
    @TableField(value = "city_code")
    private String cityCode;

    /*
     * 区编码
     */
    @TableField(value = "county_code")
    private String countyCode;

    /*
     * 街道编码
     */
    @TableField(value = "street_code")
    private String streetCode;

    /*
     * 地理坐标。x,y
     */
    @TableField(value = "geo")
    private String geo;

    /*
     * 店铺简介
     */
    @TableField(value = "shop_introduction")
    private String shopIntroduction;

    /*
     * 店铺logo
     */
    @TableField(value = "logo")
    private String logo;

    /*
     * 配送类型
     */
    @TableField(value = "delivery_type")
    private String deliveryType;

    /*
     * 营业时间-json格式[{"s": "", "e": ""}]
     */
    @TableField(value = "work_time")
    private String workTime;

    /*
     * 营业市段[{"type":"对应cube_joindata.bd_dict表，group_code=shop_business_segment的code字段值",
     *           "typeName":"市段名称，用户前端回显",
     *           "start":"HH:mm",
     *           "end":"HH:mm"}]
     */
    @TableField(value = "business_segment")
    private String businessSegment;

    /*
     * 店铺ID
     */
    @TableField(value = "self_id")
    private Long selfId;

    /*
     * 扩展字段
     */
    @TableField(value = "extension")
    private String extension;

    /*
     * 所有者
     */
    @TableField(value = "owner")
    private Long owner;

    /*
     * pos门店编码
     */
    @TableField(value = "pos_shop_code")
    private String posShopCode;

    /*
     * 云徒门店编码
     */
    @TableField(value = "yunxi_shop_code")
    private String yunxiShopCode;

    /*
     * 所属城市(部门id)
     */
    @TableField(value = "org_id")
    private Long orgId;

    /*
     * 所属城市名称(部门名称)
     */
    @TableField(value = "org_name")
    private String orgName;

    /*
     * 微信二维码id
     */
    @TableField(value = "code_id")
    private Long codeId;

    /*
     * 微信二维码url
     */
    @TableField(value = "code_url")
    private String codeUrl;

    /*
     * 门店面积
     */
    @TableField(value = "shop_area")
    private BigDecimal shopArea;

    /*
     * 排序字段
     */
    @TableField(value = "sort_num")
    private Long sortNum;

    /*
     * 书亦烧仙草小程序我的开票二维码url
     */
    @TableField(value = "applets_my_invoice_qr_code_url")
    private String appletsMyInvoiceQrCodeUrl;

    /*
     * 双证 json
     */
    @TableField(value = "qualification")
    private String qualification;

    /*
     * 迁址审批状态
     */
    @TableField(value = "relocation_approval_status")
    private String relocationApprovalStatus;

    /*
     * 翻新审批状态
     */
    @TableField(value = "renovation_approval_status")
    private String renovationApprovalStatus;

    /*
     * 门店开业状态
     */
    @TableField(value = "store_opening_status")
    private String storeOpeningStatus;

    /*
     * 签约ID
     */
    @TableField(value = "sign_id")
    private Long signId;

    /*
     * 项目编码
     */
    @TableField(value = "project_num")
    private String projectNum;

    /*
     * 开业时间
     */
    @TableField(value = "open_date")
    private Date openDate;

    /*
     * 区域代理
     */
    @TableField(value = "regional_agency")
    private String regionalAgency;

    /*
     * 续约审批状态
     */
    @TableField(value = "renewal_approval_status")
    private String renewalApprovalStatus;

    /*
     * 开业中
     */
    @TableField(value = "opening")
    private String opening;

    /*
     * 数据类型
     */
    @TableField(value = "data_type")
    private String dataType;

    /*
     * 创建人
     */
    @TableField(value = "create_person")
    private String createPerson;

    /*
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /*
     * 更新人
     */
    @TableField(value = "update_person")
    private String updatePerson;

    /*
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /*
     * 删除标记
     */
    @TableField(value = "dr")
    private Integer dr;

    /*
     * 
     */
    @TableField(value = "instance_id")
    private Long instanceId;

    /*
     * 
     */
    @TableField(value = "tenant_id")
    private Long tenantId;
}

