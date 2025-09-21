package com.machine.sdk.common.envm.data.material;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataMaterIalTextFormatEnum implements BaseEnum<DataMaterIalTextFormatEnum, String> {
    TXT("TXT", "纯文本文件"),
    RTF("RTF", "富文本格式"),
    HTML("HTML", "超文本标记语言"),
    XML("XML", "可扩展标记语言"),
    JSON("JSON", "JSON对象表示法"),
    MARKDOWN("MD", "Markdown文档"),
    CSV("CSV", "逗号分隔值文件"),
    CONFIG("CONFIG", "配置文件"),
    YAML("YAML", "YAML配置文件"),
    PROPERTIES("PROPERTIES", "属性配置文件"),
    SQL("SQL", "SQL脚本文件"),
    JS("JS", "JavaScript文件"),
    JAVA("JAVA", "Java源代码"),
    PY("PYTHON", "Python源代码");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}