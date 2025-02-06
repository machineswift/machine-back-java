package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataMaterIalTypeEnum implements BaseEnum<DataMaterIalTypeEnum, String> {
    TEXT("TEXT", "文本"),
    IMAGE("IMAGE", "图片"),
    VOICE("VOICE", "音频"),
    VIDEO("VIDEO", "视频"),
    DOCUMENT("DOCUMENT", "文档"),
    FILE("FILE", "文件");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
