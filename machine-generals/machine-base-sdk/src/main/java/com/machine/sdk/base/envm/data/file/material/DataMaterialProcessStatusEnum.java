package com.machine.sdk.base.envm.data.file.material;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统处理状态
 */
@Getter
@AllArgsConstructor
public enum DataMaterialProcessStatusEnum implements BaseEnum<DataMaterialProcessStatusEnum, String> {
    /** 未开始 */
    NOT_STARTED("NOT_STARTED", "未开始"),
    /** 处理中 */
    PROCESSING("PROCESSING", "处理中"),
    /** 重试中 */
    RETRYING("RETRYING", "重试中"),
    /** 成功（终态） */
    SUCCESS("SUCCESS", "成功"),
    /** 失败（终态） */
    FAILED("FAILED", "失败"),
    /** 已取消（终态，任务取消或超时未执行） */
    CANCELLED("CANCELLED", "已取消");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
