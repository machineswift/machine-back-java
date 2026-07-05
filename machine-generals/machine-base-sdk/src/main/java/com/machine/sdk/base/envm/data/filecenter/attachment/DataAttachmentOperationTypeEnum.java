package com.machine.sdk.base.envm.data.filecenter.attachment;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataAttachmentOperationTypeEnum implements BaseEnum<DataAttachmentOperationTypeEnum, String> {

    // 上传阶段
    UPLOAD("UPLOAD", "上传", "上传附件文件"),

    // 使用阶段
    QUERY("QUERY", "查询", "查询附件信息"),
    PREVIEW("PREVIEW", "预览", "在线预览附件"),
    DOWNLOAD("DOWNLOAD", "下载", "下载附件文件"),
    SHARE("SHARE", "分享", "分享附件链接"),
    EXPORT("EXPORT", "导出", "导出附件数据"),

    // 维护阶段
    UPDATE("UPDATE", "更新", "更新附件版本"),
    ROLLBACK("ROLLBACK", "回滚", "回滚到历史版本"),
    LOCK("LOCK", "锁定", "锁定附件"),
    UNLOCK("UNLOCK", "解锁", "解锁附件"),

    // 生命周期管理
    EXPIRE("EXPIRE", "过期", "附件过期"),
    ARCHIVE("ARCHIVE", "归档", "附件归档"),
    DELETE("DELETE", "删除", "删除附件");

    private final String code;
    private final String message;
    private final String description;

    @Override
    public String getName() {
        return this.name();
    }
}
