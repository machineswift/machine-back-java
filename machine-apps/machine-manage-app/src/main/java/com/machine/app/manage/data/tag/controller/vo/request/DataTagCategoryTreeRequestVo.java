package com.machine.app.manage.data.tag.controller.vo.request;

import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataTagCategoryTreeRequestVo {

    @NotNull(message = "画像主题类型不能为空")
    @Schema(description = "画像主体类型（ProfileSubjectTypeEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private ProfileSubjectTypeEnum type;

    public DataTagCategoryTreeRequestVo(ProfileSubjectTypeEnum type) {
        this.type = type;
    }
}
