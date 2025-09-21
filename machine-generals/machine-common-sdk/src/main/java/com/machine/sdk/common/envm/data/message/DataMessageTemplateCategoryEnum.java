package com.machine.sdk.common.envm.data.message;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataMessageTemplateCategoryEnum implements BaseEnum<DataMessageTemplateCategoryEnum, String> {

    SITE_SELECTION_TASK("SITE_SELECTION_TASK","选址任务"),
    BUILD_TASK("BUILD_TASK","筹建任务"),
    SHOP_MANAGE("SHOP_MANAGE","店务管理"),
    OA_APPROVAL("OA_APPROVAL","OA审批"),
    PURCHASE_SALE_STOCK("PURCHASE_SALE_STOCK","进销存"),
    CONTRACT("CONTRACT","合同"),
    DESIGN_WORK_ORDER("DESIGN_WORK_ORDER","设计任务"),
    THIRD_PARTY_APPROVAL("THIRD_PARTY_APPROVAL","平台开通"),
    INVENTORY_SHORTAGE_WARNING("INVENTORY_SHORTAGE_WARNING","库存不足预警"),
    INVENTORY_SURPLUS_WARNING("INVENTORY_SURPLUS_WARNING","库存过剩预警"),
    INSPECTION("INSPECTION", "检核"),
    STORE_DATA_CHANGE("STORE_DATA_CHANGE", "门店数据变更"),
    TRAINING("TRAINING", "训练"),
    EMPLOYEE_DATA_CHANGE("EMPLOYEE_DATA_CHANGE", "员工数据变更"),
    LICENSE("LICENSE", "证照"),
    ;

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
