package com.machine.sdk.base.envm.base;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模块实体
 */
@Getter
@AllArgsConstructor
public enum ModuleEntityEnum implements BaseEnum<ModuleEntityEnum, String> {
    /**
     * BIAM
     */
    BIAM_USER("BIAM_USER", "用户"),
    BIAM_ROLE("BIAM_ROLE", "角色"),
    BIAM_PERMISSION("BIAM_PERMISSION", "菜单"),
    BIAM_ORGANIZATION("BIAM_ORGANIZATION", "组织"),
    BIAM_AUTH2_CLIENT("BIAM_AUTH2_CLIENT", "认证客户端"),
    BIAM_USER_LOGIN_LOG("BIAM_USER_LOGIN_LOG", "登录日志"),
    BIAM_USER_ACCESS_LOG("BIAM_USER_ACCESS_LOG", "访问日志"),
    BIAM_OPERATION_LOG("BIAM_OPERATION_LOG", "操作日志"),

    /**
     * DATA
     */
    DATA_MATERIAL("DATA_MATERIAL", "素材管理"),
    DATA_DOWNLOAD("DATA_DOWNLOAD", "下载中心"),
    DATA_BRAND("DATA_BRAND", "品牌"),
    DATA_SHOP("DATA_SHOP", "门店"),
    DATA_AREA("DATA_AREA", "区域"),
    DATA_TAG("DATA_TAG", "标签"),
    DATA_ATTACHMENT("DATA_ATTACHMENT", "附件"),
    DATA_FRANCHISEE("DATA_FRANCHISEE", "加盟商"),
    DATA_MESSAGE("DATA_MESSAGE", "站内消息"),
    DATA_MESSAGE_TEMPLATE("DATA_MESSAGE_TEMPLATE", "消息模版"),
    DATA_SUPPLIER("DATA_SUPPLIER", "供应商"),

    /**
     * HRM
     */
    HRM_DEPARTMENT("HRM_DEPARTMENT", "部门"),
    HRM_EMPLOYEE("HRM_EMPLOYEE", "员工"),
    HRM_JOB_POST("HRM_JOB_POST", "职务"),

    /**
     * SCM
     */
    SCM_CATEGORY("SCM_CATEGORY", "商品分类"),
    SCM_PROPERTY("SCM_PROPERTY", "属性"),
    SCM_PROPERTY_GROUP("SCM_PROPERTY_GROUP", "属性分组"),
    SCM_PROPERTY_VALUE("SCM_PROPERTY_VALUE", "属性值"),

    /**
     * CRM
     */
    CRM_CUSTOMER("CRM_CUSTOMER", "客户"),
    CRM_MEMBER("CRM_MEMBER", "会员"),

    /**
     * AI
     */
    AI_MODEL("AI_MODEL", "模型"),
    AI_PROVIDER("AI_PROVIDER", "厂商");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
