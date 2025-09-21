package com.machine.sdk.common.envm.data.shop;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 运营状态：日常营业的可操作性状态
 */
@Getter
@AllArgsConstructor
public enum DataShopOperationStatusEnum implements BaseEnum<DataShopOperationStatusEnum, String> {
    /**
     * 正常接待顾客
     */
    OPEN("OPEN", "营业中"),
    /**
     * 每日非营业时段（可自动切换）
     */
    CLOSED("CLOSED", "已打烊"),
    /**
     * 短期关闭（需关联原因：装修/疫情等）
     */
    TEMPORARY_CLOSED("TEMPORARY_CLOSED", "临时闭店"),
    /**
     * 只开放特定区域/服务（如酒店部分楼层维修）
     */
    PARTIAL_OPEN("PARTIAL_OPEN", "部分开放"),
    /**
     * 线下门店关闭但保留线上服务
     */
    ONLINE_ONLY("ONLINE_ONLY", "仅线上运营"),

    /**
     * 仅提供基础服务（如医疗机构的急诊模式）
     */
    EMERGENCY_MODE("EMERGENCY_MODE", "应急模式");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
