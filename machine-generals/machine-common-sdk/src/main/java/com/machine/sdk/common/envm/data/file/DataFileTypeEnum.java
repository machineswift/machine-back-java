package com.machine.sdk.common.envm.data.file;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataFileTypeEnum implements BaseEnum<DataFileTypeEnum, String> {
    IMAGE("IMAGE", "图片"),
    VIDEO("VIDEO", "视频"),
    AUDIO("AUDIO", "音频"),
    DOCUMENT("DOCUMENT", "文档"),
    SPREADSHEET("SPREADSHEET", "电子表格"),
    PRESENTATION("PRESENTATION", "演示文稿"),
    ARCHIVE("ARCHIVE", "压缩文件"),
    EXECUTABLE("EXECUTABLE", "可执行文件"),
    CODE("CODE", "源代码"),
    CONFIG("CONFIG", "配置文件"),
    FONT("FONT", "字体文件"),
    MODEL_3D("MODEL_3D", "3D模型"),
    CAD("CAD", "CAD文件"),
    E_BOOK("E_BOOK", "电子书"),
    DATABASE("DATABASE", "数据库"),
    LOG("LOG", "日志文件"),
    SYSTEM("SYSTEM", "系统文件"),
    TEMPORARY("TEMPORARY", "临时文件"),
    UNKNOWN("UNKNOWN", "未知类型");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}