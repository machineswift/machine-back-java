package com.machine.sdk.common.envm.data.message;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataMessageTemplateRiskCategoryEnum implements BaseEnum<DataMessageTemplateRiskCategoryEnum, String> {

    GROUP_BAN("GROUP_BAN", "团购平台封禁"),
    INVENTORY_LOW_WARNING("INVENTORY_LOW_WARNING", "库存不足预警"),
    CONTRACT_EXPIRATION_REMINDER("CONTRACT_EXPIRATION_REMINDER", "合同到期提醒")
    ;

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
