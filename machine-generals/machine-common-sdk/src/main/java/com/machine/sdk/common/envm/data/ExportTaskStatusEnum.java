package com.machine.sdk.common.envm.data;


import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExportTaskStatusEnum implements BaseEnum<ExportTaskStatusEnum, String> {
    READY("READY", "未开始"),
    RUNNING("RUNNING", "进行中"),
    FINISH("FINISH", "已完成"),
    FAIL("FAIL", "失败");


    private final String code;
    private final String msg;


    @Override
    public String getName() {
        return this.name();
    }

}