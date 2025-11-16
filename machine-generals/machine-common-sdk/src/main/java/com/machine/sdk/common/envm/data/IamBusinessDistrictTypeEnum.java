package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamBusinessDistrictTypeEnum implements BaseEnum<IamBusinessDistrictTypeEnum, String> {
    COMMERCIAL_STREET("COMMERCIAL_STREET", "商业街"),
    OFFICE_AREA("OFFICE_AREA", "办公区"),
    COMMUNITY("COMMUNITY", "社区(住宅)"),
    RESIDENTIAL_OFFICE_MIXED("RESIDENTIAL_OFFICE_MIXED", "住办混合"),
    RESIDENTIAL_COMMERCIAL_MIXED("RESIDENTIAL_COMMERCIAL_MIXED", "住商混合"),
    COMMERCIAL_OFFICE_MIXED("COMMERCIAL_OFFICE_MIXED", "商办混合"),
    TOURIST_ATTRACTION("TOURIST_ATTRACTION", "旅游景区"),
    SCHOOL("SCHOOL", "学校"),
    INDUSTRIAL_AREA("INDUSTRIAL_AREA", "工业区"),
    TRANSPORTATION_HUB("TRANSPORTATION_HUB", "交通枢纽"),
    SHOPPING_MALL("SHOPPING_MALL", "商场"),
    OTHER("OTHER", "其他");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
