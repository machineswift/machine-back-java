package com.machine.sdk.common.envm.data.download;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DownLoadFileChannelEnum implements BaseEnum<DownLoadFileChannelEnum, String> {
    HYY("HYY", "慧运营"),
    XGJ("XGJ", "喜管家"),
    POS("POS", "POS"),
    SYSTEM("SYSTEM", "系统");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
