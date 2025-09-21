package com.machine.sdk.beisen.envm;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BeiSenCertificateTypeEnum implements BaseEnum<StatusEnum, String> {
    ID_CARD("1", "身份证"),
    CHINESE_PASSPORT("2", "中国护照"),
    HK_MACAO_RESIDENTS_TRAVEL_PERMIT_TO_MAINLAND("3", "港澳居民来往内地通行证"),
    TAIWAN_RESIDENTS_TRAVEL_PERMIT_TO_MAINLAND("4", "台湾居民来往大陆通行证"),
    MILITARY_ID("5", "军人证"),
    OTHER("6", "其他"),
    HK_MACAO_TAIWAN_RESIDENTS_RESIDENCE_PERMIT("7", "港澳台居民居住证"),
    ARMED_POLICE_OFFICER_ID("8", "武警警官证"),
    HK_MACAO_ID_CARD("9", "港澳身份证"),
    FOREIGN_PASSPORT("10", "外国护照"),
    HK_PERMANENT_RESIDENT_ID_CARD("11", "香港永久性居民身份证"),
    TAIWAN_ID_CARD("12", "台湾身份证"),
    MACAO_PERMANENT_RESIDENT_ID_CARD("13", "澳门特别行政区永久性居民身份证"),
    FOREIGNER_PERMANENT_RESIDENCE_PERMIT("14", "外国人永久居留证"),
    MACAO_NON_PERMANENT_RESIDENT_ID_CARD("15", "澳门特别行政区非永久性居民身份证"),
    HK_RESIDENT_ID_CARD("16", "香港居民身份证");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
