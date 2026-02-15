package com.machine.client.data.file.material.dto.input;

import com.machine.sdk.common.envm.data.file.DataFileTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DataMaterialQueryPageInputDto extends PageRequest {

    @NotNull(message = "文件类型不能为空")
    @Schema(description = "文件类型（DataFileTypeEnum）")
    private DataFileTypeEnum fileType;

    @Schema(description = "素材标题")
    private String title;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "是否包含【未分类】节点，默认：false")
    private Boolean containVirtualNode = false;

    @Schema(description = "素材分类集合")
    private Set<String> categoryIdSet;

    @Schema(description = "创建人ID集合")
    private Set<String> createUserIdSet;

    @Schema(description = "修改人ID集合")
    private Set<String> updateUserIdSet;

    @Schema(description = "更新开始时间")
    private Long updateStartTime;

    @Schema(description = "更新结束时间")
    private Long updateEndTime;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;

}
