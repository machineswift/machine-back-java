package com.machine.sdk.common.envm.data.shop;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 经营状态：门店的商业生命周期阶段
 */
@Getter
@AllArgsConstructor
public enum DataShopBusinessStatusEnum implements BaseEnum<DataShopBusinessStatusEnum, String> {

    /**
     * 品牌方正在评估选址、客流量、竞争环境等
     */
    MARKET_RESEARCH("MARKET_RESEARCH", "市场调研中"),
    /**
     * 已确定目标区域，正在筛选具体物业（如商场、街铺）
     */
    LOCATION_SCOUTING("LOCATION_SCOUTING", "选址考察中"),
    /**
     * 与业主/物业方进行租金、合同条款等谈判（尚未签约）
     */
    NEGOTIATION("NEGOTIATION", "商业谈判中"),
    /**
     * 合同条款已初步达成，等待品牌总部或法务部门批准
     */
    APPROVAL_PENDING("APPROVAL_PENDING", "内部审批中"),
    /**
     * 租赁/购买合同已生效，法律手续完成
     */
    CONTRACT_SIGNED("CONTRACT_SIGNED", "签约完成"),
    /**
     * 装修、招聘、证照办理阶段
     */
    PRE_OPENING("PRE_OPENING", "开业筹备中"),
    /**
     * 限流开放测试（餐饮业常见）
     */
    TRIAL_OPERATION("TRIAL_OPERATION", "试营业"),
    /**
     * 正式经营状态
     */
    NORMAL_OPERATION("NORMAL_OPERATION", "正常营业"),

    /**
     * 加盟权转让
     */
    FRANCHISE_TRANSFER("FRANCHISE_TRANSFER", "加盟权转让"),

    /**
     * 品牌升级改造
     */
    REBRANDING("REBRANDING", "品牌升级改造"),

    /**
     * 因违规被监管部门要求停业
     */
    SUSPENDED("SUSPENDED", "停业整顿"),
    /**
     * 终止经营后的资产清算阶段
     */
    LIQUIDATION("LIQUIDATION", "清算中"),
    /**
     * 彻底退出运营（物理门店可能仍存在）
     */
    ABANDONED("ABANDONED", "已废弃");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
