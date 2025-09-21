package com.machine.app.manage.data.attachment.controller.vo.resquest;

import com.machine.sdk.common.envm.data.attachment.DataAttachmentStatusEnum;
import com.machine.sdk.common.envm.data.attachment.DataAttachmentTypeEnum;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DataAttachmentQueryPageRequestVo extends PageRequest {

    @Schema(description = "状态（DataAttachmentStatusEnum）")
    private DataAttachmentStatusEnum status;

    @Schema(description = "类型（DataAttachmentTypeEnum）")
    private DataAttachmentTypeEnum type;

    @Schema(description = "持久化类型（SystemStorageTypeEnum）")
    private SystemStorageTypeEnum storageType;

    @Schema(description = "附件标题")
    private String title;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "分类集合")
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
