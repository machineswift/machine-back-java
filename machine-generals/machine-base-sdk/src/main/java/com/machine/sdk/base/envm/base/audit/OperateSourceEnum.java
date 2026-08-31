package com.machine.sdk.base.envm.base.audit;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperateSourceEnum implements BaseEnum<OperateSourceEnum, String> {
    IAM_APP("IAM_APP", "权限管理中心"),
    ADMIN_APP("ADMIN_APP", "管理后台"),
    PARTNER_APP("PARTNER_APP", "伙伴APP"),
    CUSTOMER_APP("CUSTOMER_APP", "客户APP");
    
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }

}
