package com.machine.client.data.tag.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataTagQueryPageInputDto extends PageRequest {

    @NotNull(message = "画像主题类型不能为空")
    @Schema(description = "画像主体类型（ProfileSubjectTypeEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private ProfileSubjectTypeEnum type;

    @Schema(description = "分类ID集合")
    private Set<String> categoryIdSet;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

}
