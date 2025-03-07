package com.machine.sdk.huawei.exception;

import com.machine.sdk.common.exception.sdk.SdkBusinessException;
import com.machine.sdk.huawei.envm.HuaWeiSmsCodeEnum;
import com.machine.sdk.huawei.envm.HuaWeiSmsStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HuaWeiSmsSdkException extends SdkBusinessException {

    public HuaWeiSmsSdkException(String code,
                                 String message) {
        super(code, message);
    }

    public HuaWeiSmsSdkException(String code,
                                 String message,
                                 Throwable cause) {
        super(code, message,cause);
    }



    public HuaWeiSmsSdkException(String code,
                                 HuaWeiSmsCodeEnum codeEnum) {
        super(code, codeEnum.getCode() + ":" + codeEnum.getMsg());
    }

    public HuaWeiSmsSdkException(String code,
                                 HuaWeiSmsStatusEnum statusEnum) {
        super(code, statusEnum.getCode() + ":" + statusEnum.getMsg());
    }


}
