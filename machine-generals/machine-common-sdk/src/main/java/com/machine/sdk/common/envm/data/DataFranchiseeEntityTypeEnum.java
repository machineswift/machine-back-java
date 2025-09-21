package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 加盟商主体类型
 */
@Getter
@AllArgsConstructor
public enum DataFranchiseeEntityTypeEnum implements BaseEnum<DataFranchiseeEntityTypeEnum, String> {
    INDIVIDUAL("INDIVIDUAL", "个人加盟商"),
    CORPORATE("CORPORATE", "企业加盟商"),
    PARTNERSHIP("PARTNERSHIP", "合伙加盟商"),
    AREA_DEVELOPER("AREA_DEVELOPER", "区域加盟商"),
    MASTER_FRANCHISEE("MASTER_FRANCHISEE", "主加盟商"),
    SUB_FRANCHISEE("SUB_FRANCHISEE", "次级加盟商");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
