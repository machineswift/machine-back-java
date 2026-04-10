package com.machine.client.data.file.material.dto.input;

import com.machine.sdk.base.envm.data.file.DataFileTypeEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialAuditStatusEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialBusinessStatusEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialProcessStatusEnum;
import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DataMaterialQueryPageInputDto extends PageRequest {

    @Schema(description = "文件类型集合，多选；为空则不按文件类型过滤")
    private Set<DataFileTypeEnum> fileTypeSet;

    @Schema(description = "素材标题")
    private String title;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "系统处理状态")
    private DataMaterialProcessStatusEnum processStatus;

    @Schema(description = "业务状态")
    private DataMaterialBusinessStatusEnum businessStatus;

    @Schema(description = "审核状态")
    private DataMaterialAuditStatusEnum auditStatus;

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
