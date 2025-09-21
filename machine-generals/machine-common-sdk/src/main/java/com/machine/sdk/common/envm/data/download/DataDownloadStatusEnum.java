package com.machine.sdk.common.envm.data.download;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataDownloadStatusEnum implements BaseEnum<DataDownloadStatusEnum, String> {
    READY("READY", "未开始"),
    RUNNING("RUNNING", "进行中"),
    FINISH("FINISH", "已完成"),
    FAIL("FAIL", "失败"),
    DEAD("DEAD", "死亡");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }

}