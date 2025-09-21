package com.machine.sdk.common.envm.data.material;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataMaterialDocumentFormatEnum implements BaseEnum<DataMaterialDocumentFormatEnum, String> {
    // 办公文档
    DOC("DOC", "Word 97-2003文档"),
    DOCX("DOCX", "Word文档"),
    PPT("PPT", "PowerPoint 97-2003演示文稿"),
    PPTX("PPTX", "PowerPoint演示文稿"),
    XLS("XLS", "Excel 97-2003电子表格"),
    XLSX("XLSX", "Excel电子表格"),
    PPS("PPS", "PowerPoint幻灯片放映"),
    PPSX("PPSX", "PowerPoint幻灯片放映"),
    PUB("PUB", "Publisher文档"),
    VSD("VSD", "Visio绘图文档"),

    // 开放文档
    ODT("ODT", "OpenDocument文本"),
    ODP("ODP", "OpenDocument演示文稿"),
    ODS("ODS", "OpenDocument电子表格"),
    ODG("ODG", "OpenDocument图形"),

    // 通用文档
    PDF("PDF", "便携式文档格式"),
    RTF("RTF", "富文本格式"),
    TXT("TXT", "纯文本文件"),

    // 电子书
    EPUB("EPUB", "电子出版物"),
    MOBI("MOBI", "Mobipocket电子书"),
    AZW("AZW", "Amazon Kindle电子书"),
    FB2("FB2", "FictionBook电子书"),

    // 标记语言
    HTML("HTML", "超文本标记语言"),
    XML("XML", "可扩展标记语言"),
    MARKDOWN("MD", "Markdown文档"),
    LATEX("TEX", "LaTeX文档"),

    // 数据文件
    CSV("CSV", "逗号分隔值文件"),
    TSV("TSV", "制表符分隔值文件"),
    JSON("JSON", "JavaScript对象表示法"),
    YAML("YAML", "YAML配置文件"),

    // 其他专业文档
    DJVU("DJVU", "Djvu扫描文档"),
    XPS("XPS", "XML纸张规范"),
    OXPS("OXPS", "开放XPS文档"),
    CHM("CHM", "编译的HTML帮助文件"),
    MHT("MHT", "MHTML网页存档");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}