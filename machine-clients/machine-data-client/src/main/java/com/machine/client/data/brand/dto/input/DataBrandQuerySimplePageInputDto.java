package com.machine.client.data.brand.dto.input;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataBrandQuerySimplePageInputDto extends PageRequest {

    @Schema(description = "父品牌ID（不传则不按父级过滤）")
    private String parentId;

    @Schema(description = "关键字（模糊匹配名称或编码）")
    private String keyword;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;
}
