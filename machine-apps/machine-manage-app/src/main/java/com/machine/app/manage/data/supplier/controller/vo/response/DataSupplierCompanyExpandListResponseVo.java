package com.machine.app.manage.data.supplier.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.data.SupplierBusinessCategoryEnum;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DataSupplierCompanyExpandListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "供应商业务类别（SupplierBusinessCategoryEnum）")
    private SupplierBusinessCategoryEnum businessCategory;

    @Schema(description = "联系人")
    private String contactName;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "通讯地址")
    private AddressInfoDto correspondenceAddress;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

}
