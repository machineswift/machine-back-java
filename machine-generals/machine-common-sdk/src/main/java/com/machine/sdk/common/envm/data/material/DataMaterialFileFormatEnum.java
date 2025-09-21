package com.machine.sdk.common.envm.data.material;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataMaterialFileFormatEnum implements BaseEnum<DataMaterialFileFormatEnum, String> {
    // 压缩文件格式
    ZIP("ZIP", "ZIP压缩文件"),
    RAR("RAR", "RAR压缩文件"),
    TAR("TAR", "TAR归档文件"),
    GZ("GZ", "GZIP压缩文件"),
    BZ2("BZ2", "BZIP2压缩文件"),
    Z7("7Z", "7-Zip压缩文件"),

    // 可执行文件与脚本
    EXE("EXE", "Windows可执行文件"),
    MSI("MSI", "Windows安装包"),
    BAT("BAT", "批处理文件"),
    SH("SH", "Shell脚本"),
    JAR("JAR", "Java归档文件"),
    APK("APK", "Android应用包"),
    IPA("IPA", "iOS应用包"),

    // 数据库文件
    SQL("SQL", "SQL脚本文件"),
    DB("DB", "数据库文件"),
    MDB("MDB", "Microsoft Access数据库"),
    SQLITE("SQLITE", "SQLite数据库文件"),

    // 配置文件与其他
    INI("INI", "初始化配置文件"),
    YAML("YAML", "YAML配置文件"),
    PROPERTIES("PROPERTIES", "Java属性文件"),
    LOG("LOG", "日志文件");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}