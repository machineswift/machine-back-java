package com.machine.client.crm.member.dto.output;

import com.machine.sdk.common.envm.base.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class CrmMemberDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "性别（CrmGenderEnum）")
    private GenderEnum gender;

    @Schema(description = "生日年")
    private Integer birthYear;

    @Schema(description = "生日月")
    private Integer birthMonth;

    @Schema(description = "生日天")
    private Integer birthDay;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
