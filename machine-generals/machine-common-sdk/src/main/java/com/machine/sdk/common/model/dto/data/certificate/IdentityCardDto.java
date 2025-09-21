package com.machine.sdk.common.model.dto.data.certificate;

import com.machine.sdk.common.envm.crm.customer.CrmGenderEnum;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * 身份证
 */
@Data
@Schema
@Validated
public class IdentityCardDto {

    @Schema(description = "身份证号码")
    private String number;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "性别")
    private CrmGenderEnum gender;

    @Schema(description = "生日-年")
    private Integer birthYear;

    @Schema(description = "生日-月")
    private Integer birthMonth;

    @Schema(description = "生日-天")
    private Integer birthDay;

    @Schema(description = "签发机关")
    public String issuingAuthority;

    @Schema(description = "发证日期")
    private Long issueDate;

    @Schema(description = "到期日期")
    private Long expiryDate;

    @Schema(description = "地址")
    private AddressInfoDto addressInfo;

    @Schema(description = "正面图片附件信息")
    public MaterialDto frontPictureMaterial;

    @Schema(description = "反面图片附件信息")
    public MaterialDto backPictureMaterial;

}
