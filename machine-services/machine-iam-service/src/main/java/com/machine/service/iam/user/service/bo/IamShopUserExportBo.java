package com.machine.service.iam.user.service.bo;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class IamShopUserExportBo {

    @ExcelProperty("ID")
    private String id;

    @ExcelProperty("系统账号")
    private String username;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("编码")
    private String code;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("手机号")
    private String phone;

    @ExcelProperty("性别")
    private String gender;
}
