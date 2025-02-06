package com.machine.client.data.supplier.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSupplierCompanyFinancialInformationDto {

    /**
     * 纳税人识别号(Taxpayer Identification Number)
     */
    private String TIN;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 银行账号
     */
    private String bankAccountNumber;

}
