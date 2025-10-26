package com.machine.client.crm.member.dto.input;

import com.machine.sdk.common.envm.common.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class CrmMemberCreateInputDto {

    @Schema(description = "邮箱")
    private String email;

    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

    @NotNull(message = "姓名不能为空")
    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull(message = "性别不能为空")
    @Schema(description = "性别（CrmGenderEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private GenderEnum gender;

    @Schema(description = "生日年")
    private Integer birthYear;

    @Schema(description = "生日月")
    private Integer birthMonth;

    @Schema(description = "生日天")
    private Integer birthDay;

}
