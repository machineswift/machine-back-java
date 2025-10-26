package com.machine.app.manage.crm.member.controller.vo.response;

import com.machine.sdk.common.envm.common.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class CrmMemberExpandListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "编码")
    private String code;

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
