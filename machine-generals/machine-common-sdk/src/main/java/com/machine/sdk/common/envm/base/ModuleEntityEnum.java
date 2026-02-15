package com.machine.sdk.common.envm.base;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模块实体
 */
@Getter
@AllArgsConstructor
public enum ModuleEntityEnum implements BaseEnum<ModuleEntityEnum, String> {
    /**
     * IAM
     */
    IAM_USER("IAM_USER", "用户"),
    IAM_ROLE("IAM_ROLE", "角色"),
    IAM_PERMISSION("IAM_PERMISSION", "菜单"),
    IAM_ORGANIZATION("IAM_ORGANIZATION", "组织"),

    /**
     * DATA
     */
    DATA_MATERIAL("DATA_MATERIAL", "素材管理"),
    DATA_DOWNLOAD("DATA_DOWNLOAD", "下载中心"),
    DATA_BRAND("DATA_BRAND", "用户"),
    DATA_SHOP("DATA_SHOP", "门店"),
    DATA_AREA("DATA_AREA", "区域"),
    DATA_TAG("DATA_TAG", "标签");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
