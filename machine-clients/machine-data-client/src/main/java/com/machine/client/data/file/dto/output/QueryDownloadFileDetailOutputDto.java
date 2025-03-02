package com.machine.client.data.file.dto.output;

import com.machine.sdk.common.envm.data.MaterIalTypeEnum;
import com.machine.sdk.common.envm.data.download.DownLoadFileChannelEnum;
import com.machine.sdk.common.envm.data.download.ExportTaskStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryDownloadFileDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "渠道")
    private DownLoadFileChannelEnum channel;

    @Schema(description = "任务状态")
    private ExportTaskStatusEnum status;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件类型")
    private MaterIalTypeEnum fileType;

    @Schema(description = "url地址md5")
    private String urlMd5;

    @Schema(description = "附件信息")
    private String material;

    @Schema(description = "过期时间")
    private Long expirationIn;

    @Schema(description = "内容")
    private String content;

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