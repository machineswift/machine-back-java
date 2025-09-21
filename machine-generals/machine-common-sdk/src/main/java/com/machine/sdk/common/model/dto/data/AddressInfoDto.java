package com.machine.sdk.common.model.dto.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Schema
@Validated
public class AddressInfoDto {

    @Schema(description = "国家，默认:中国")
    private String country;
    @NotBlank(message = "国家编码不能为空")
    @Schema(description = "国家编码，默认:CHINA")
    private String countryCode;

    @Schema(description = "省")
    public String province;
    @NotBlank(message = "省编码不能为空")
    @Schema(description = "省编码")
    public String provinceCode;

    @Schema(description = "市")
    public String city;
    @NotBlank(message = "市编码不能为空")
    @Schema(description = "市编码")
    public String cityCode;

    @Schema(description = "区")
    public String area;
    @NotBlank(message = "区编码不能为空")
    @Schema(description = "区编码")
    public String areaCode;

    @Schema(description = "街道")
    public String town;
    @Schema(description = "街道编码")
    public String townCode;

    @Schema(description = "详细地址")
    public String address;

}
