package com.machine.client.data.file.material.dto.output;

import com.machine.sdk.base.envm.data.file.DataFileTypeEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialAuditStatusEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialBusinessStatusEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialProcessStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "文件类型（DataFileTypeEnum）")
    private DataFileTypeEnum fileType;

    @Schema(description = "附件ID")
    private String attachmentId;

    @Schema(description = "系统处理状态")
    private DataMaterialProcessStatusEnum processStatus;

    @Schema(description = "业务状态")
    private DataMaterialBusinessStatusEnum businessStatus;

    @Schema(description = "审核状态")
    private DataMaterialAuditStatusEnum auditStatus;

    @Schema(description = "素材标题")
    private String title;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

}


