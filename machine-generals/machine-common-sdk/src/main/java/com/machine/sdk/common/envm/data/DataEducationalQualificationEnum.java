package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataEducationalQualificationEnum implements BaseEnum<DataEducationalQualificationEnum, String> {
    PRIMARY_SCHOOL("PRIMARY_SCHOOL", "小学"),
    JUNIOR_HIGH_SCHOOL("JUNIOR_HIGH_SCHOOL", "初中"),
    TECHNICAL_SECONDARY("JUNIOR_HIGH_SCHOOL", "中专"),
    SENIOR_HIGH_SCHOOL("SENIOR_HIGH_SCHOOL", "高中"),
    ASSOCIATE_DEGREE("ASSOCIATE_DEGREE", "大专"),
    BACHELOR_DEGREE("BACHELOR_DEGREE", "本科"),
    MASTER_DEGREE("MASTER_DEGREE", "硕士/研究生"),
    DOCTORAL_DEGREE("DOCTORAL_DEGREE", "博士"),
    POSTDOCTORAL_RESEARCH("POSTDOCTORAL_RESEARCH", "博士后"),
    OTHER("OTHER", "其他");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
