package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTemplateTaskCategoryEnum implements BaseEnum<MessageTemplateTaskCategoryEnum, String> {

    SITE_SELECTION_AND_BUILD_TASK("SITE_SELECTION_AND_BUILD_TASK", "选址/筹建"),
    SHOP_MANAGE_TASK("SHOP_MANAGE_TASK", "店务管理"),
    CONTRACT_TASK("CONTRACT_TASK", "合同"),
    PURCHASE_SALE_STOCK_TASK("PURCHASE_SALE_STOCK_TASK", "进销存")
    ;

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
