package com.machine.sdk.base.envm.data.filecenter.attachment;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataAttachmentOperationResultEnum implements BaseEnum<DataAttachmentOperationResultEnum, String> {
    SUCCESS("SUCCESS", "成功"),
    FAILURE("FAILURE", "失败"),
    PARTIAL("PARTIAL", "部分成功");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }

    public boolean isFailure() {
        return this == FAILURE;
    }
}
