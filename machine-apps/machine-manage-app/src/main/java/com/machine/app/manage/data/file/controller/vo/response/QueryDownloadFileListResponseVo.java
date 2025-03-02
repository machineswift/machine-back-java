package com.machine.app.manage.data.file.controller.vo.response;

import com.machine.sdk.common.envm.data.MaterIalTypeEnum;
import com.machine.sdk.common.envm.data.download.DownLoadFileChannelEnum;
import com.machine.sdk.common.envm.data.download.ExportTaskStatusEnum;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class QueryDownloadFileListResponseVo {

    @Schema(description = "id")
    private String id;

    @Schema(description = " 渠道（DownLoadFileChannelEnum）")
    private DownLoadFileChannelEnum channel;

    @Schema(description = " 任务状态（ExportTaskStatusEnum）")
    private ExportTaskStatusEnum status;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件类型（MaterIalTypeEnum）")
    private MaterIalTypeEnum fileType;

    @Schema(description = "附件信息")
    private MaterialDto material;

    @Schema(description = "失败原因")
    private String failCause;

    @Schema(description = "过期时间")
    private Long expirationIn;

    @Schema(description = "更新时间")
    private Long updateTime;

    @Schema(description = "创建时间时间")
    private Long createTime;

}