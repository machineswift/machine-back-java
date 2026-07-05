package com.machine.client.data.filecenter.attachment.dto.output;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.data.filecenter.DataFileTypeEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentWithCurrentFileInfoOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "状态")
    private DataAttachmentStatusEnum status;

    @Schema(description = "实体")
    private ModuleEntityEnum entity;

    @Schema(description = "实体Id")
    private String entityId;

    @Schema(description = "附件分组")
    private String attachmentGroup;

    @Schema(description = "当前版本ID")
    private String currentVersionId;

    @Schema(description = "最大版本号")
    private Integer maxVersionNo;

    @Schema(description = "过期时间")
    private Long expireTime;

    @Schema(description = "文件信息")
    private List<DataFileInfo> fileInfoList;

    @Data
    @Schema
    @NoArgsConstructor
    public static class DataFileInfo{
        @Schema(description = "ID")
        private String id;

        @Schema(description = "文件类型(DataFileTypeEnum)")
        private DataFileTypeEnum fileType;

        @Schema(description = "原始名称")
        private String originalName;

        @Schema(description = "存储名称")
        private String storageName;

        @Schema(description = "文件SHA-256哈希值-用于去重和秒传")
        private String hashSha256;

        @Schema(description = "文件信息")
        private FileInfo fileInfo;

        @Schema(description = "大小（字节）")
        private Long size;
    }

}


