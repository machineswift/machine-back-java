package com.machine.sdk.common.envm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataCertificateTypeEnum implements BaseEnum<DataCertificateTypeEnum, String> {
    ID_CARD("ID_CARD", "身份证"),
    UNIFIED_SOCIAL_CREDIT_CODE("UNIFIED_SOCIAL_CREDIT_CODE", "社会统一信用代码"),
    CHINESE_PASSPORT("CHINESE_PASSPORT", "中国护照"),
    HK_MACAO_RESIDENTS_TRAVEL_PERMIT_TO_MAINLAND("HK_MACAO_RESIDENTS_TRAVEL_PERMIT_TO_MAINLAND", "港澳居民来往内地通行证"),
    TAIWAN_RESIDENTS_TRAVEL_PERMIT_TO_MAINLAND("TAIWAN_RESIDENTS_TRAVEL_PERMIT_TO_MAINLAND", "台湾居民来往大陆通行证"),
    MILITARY_ID("MILITARY_ID", "军人证"),
    HK_MACAO_TAIWAN_RESIDENTS_RESIDENCE_PERMIT("HK_MACAO_TAIWAN_RESIDENTS_RESIDENCE_PERMIT", "港澳台居民居住证"),
    ARMED_POLICE_OFFICER_ID("ARMED_POLICE_OFFICER_ID", "武警警官证"),
    HK_MACAO_ID_CARD("HK_MACAO_ID_CARD", "港澳身份证"),
    FOREIGN_PASSPORT("FOREIGN_PASSPORT", "外国护照"),
    HK_PERMANENT_RESIDENT_ID_CARD("HK_PERMANENT_RESIDENT_ID_CARD", "香港永久性居民身份证"),
    TAIWAN_ID_CARD("TAIWAN_ID_CARD", "台湾身份证"),
    MACAO_PERMANENT_RESIDENT_ID_CARD("MACAO_PERMANENT_RESIDENT_ID_CARD", "澳门特别行政区永久性居民身份证"),
    FOREIGNER_PERMANENT_RESIDENCE_PERMIT("FOREIGNER_PERMANENT_RESIDENCE_PERMIT", "外国人永久居留证"),
    MACAO_NON_PERMANENT_RESIDENT_ID_CARD("MACAO_NON_PERMANENT_RESIDENT_ID_CARD", "澳门特别行政区非永久性居民身份证"),
    HK_RESIDENT_ID_CARD("HK_RESIDENT_ID_CARD", "香港居民身份证"),
    OTHER("OTHER", "其他");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
