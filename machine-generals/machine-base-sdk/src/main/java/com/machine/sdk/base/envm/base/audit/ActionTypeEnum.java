package com.machine.sdk.base.envm.base.audit;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActionTypeEnum implements BaseEnum<ActionTypeEnum, String> {

    CREATE("CREATE", "新增"),
    DELETE("DELETE", "删除"),
    UPDATE("UPDATE", "修改"),
    QUERY("QUERY", "查询"),

    IMPORT("IMPORT", "导入"),
    EXPORT("EXPORT", "导出"),
    UPLOAD("UPLOAD", "上传"),
    DOWNLOAD("DOWNLOAD", "下载"),
    PRINT("PRINT", "打印"),

    ENABLE("ENABLE", "启用"),
    DISABLE("DISABLE", "停用"),
    LOCK("LOCK", "锁定"),
    UNLOCK("UNLOCK", "解锁"),

    SUBMIT("SUBMIT", "提交"),
    APPROVE("APPROVE", "通过"),
    REJECT("REJECT", "驳回"),
    REVOKE("REVOKE", "撤回"),
    CANCEL("CANCEL", "取消"),

    UNKNOWN("UNKNOWN", "未知");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }

}
