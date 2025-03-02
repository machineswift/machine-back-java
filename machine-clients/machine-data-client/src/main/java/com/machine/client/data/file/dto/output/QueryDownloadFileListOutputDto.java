package com.machine.client.data.file.dto.output;

import com.xijie.sdk.common.envm.data.download.DownLoadFileChannelEnum;
import com.xijie.sdk.common.envm.data.download.ExportTaskStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class QueryDownloadFileListOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "渠道")
    private DownLoadFileChannelEnum channel;

    @Schema(description = "任务状态")
    private ExportTaskStatusEnum status;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "附件信息")
    private String material;

    @Schema(description = "过期时间")
    private Long expirationIn;

    @Schema(description = "失败原因")
    private String failCause;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "修改人")
    private String updateBy;

    @Schema(description = "更新时间")
    private Long updateTime;


}