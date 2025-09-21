package com.machine.service.data.supplier.dao.po;

import com.machine.sdk.common.envm.StatusEnum;
import lombok.Data;

@Data
public class DataSupplierCompanySimpleListPo {

    /**
     * ID
     */
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private StatusEnum status;

}
