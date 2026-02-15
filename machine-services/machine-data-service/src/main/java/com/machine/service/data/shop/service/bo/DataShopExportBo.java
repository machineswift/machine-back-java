package com.machine.service.data.shop.service.bo;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DataShopExportBo {

    @ExcelProperty("ID")
    private String id;

    @ExcelProperty("编码")
    private String code;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("经营状态")
    private String businessStatus;

    @ExcelProperty("运营状态")
    private String operationStatus;

    @ExcelProperty("物理状态")
    private String physicalStatus;

    @ExcelProperty("区域编码")
    private String areaCode;

    @ExcelProperty("详细地址")
    private String address;

}
