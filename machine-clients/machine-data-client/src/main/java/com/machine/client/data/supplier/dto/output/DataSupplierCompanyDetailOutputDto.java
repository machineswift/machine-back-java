package com.machine.client.data.supplier.dto.output;

import com.machine.client.data.supplier.dto.DataSupplierCompanyContractInformationDto;
import com.machine.client.data.supplier.dto.DataSupplierCompanyFinancialInformationDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.data.DataSupplierBusinessCategoryEnum;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSupplierCompanyDetailOutputDto {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 供应商业务类别
     */
    private DataSupplierBusinessCategoryEnum businessCategory;

    /**
     * 编码
     */
    private String code;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 通讯地址
     * {@link AddressInfoDto}
     */
    private String correspondenceAddress;

    /**
     * 财务信息
     * {@link DataSupplierCompanyFinancialInformationDto}
     */
    private String financialInformation;

    /**
     * 合同信息
     * {@link DataSupplierCompanyContractInformationDto}
     */
    private String contractInformation;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Long updateTime;

}
