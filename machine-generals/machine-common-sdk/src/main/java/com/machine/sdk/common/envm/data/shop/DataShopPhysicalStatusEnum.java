package com.machine.sdk.common.envm.data.shop;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 物理状态：门店物理空间的可用性
 */
@Getter
@AllArgsConstructor
public enum DataShopPhysicalStatusEnum implements BaseEnum<DataShopPhysicalStatusEnum, String> {
    /**
     * 施工前的物理状态（场地未启用）
     */
    IDLE("IDLE", "空闲中"),
    /**
     * 已签约但施工未开始
     */
    PENDING_CONSTRUCTION("PENDING_CONSTRUCTION", "待施工"),
    /**
     * 装修、改建进行时
     */
    UNDER_CONSTRUCTION("UNDER_CONSTRUCTION", "施工中"),
    /**
     * 硬件升级改造
     */
    RENOVATION("RENOVATION", "翻新中"),
    /**
     * 因灾害/事故导致物理损坏
     */
    DAMAGED("DAMAGED", "已损坏"),
    /**
     * 部分区域禁止进入（如商场中的围挡区域）
     */
    ACCESS_RESTRICTED("ACCESS_RESTRICTED", "限行进入"),
    /**
     * 物理空间正在拆除
     */
    DEMOLITION("DEMOLITION", "拆除中"),

    /**
     * 拆除完成
     */
    DEMOLITION_COMPLETED("DEMOLITION_COMPLETED", "拆除完成");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
