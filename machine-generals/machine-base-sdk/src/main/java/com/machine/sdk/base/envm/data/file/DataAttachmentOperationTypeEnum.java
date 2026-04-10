package com.machine.sdk.base.envm.data.file;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataAttachmentOperationTypeEnum implements BaseEnum<DataAttachmentOperationTypeEnum, String> {
    QUERY("QUERY", "查询"),
    DOWNLOAD("DOWNLOAD", "下载"),
    EXPORT("EXPORT", "导出"),
    PREVIEW("PREVIEW", "预览"),
    SHARE("SHARE", "分享"),
    ARCHIVED("ARCHIVED", "归档");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
