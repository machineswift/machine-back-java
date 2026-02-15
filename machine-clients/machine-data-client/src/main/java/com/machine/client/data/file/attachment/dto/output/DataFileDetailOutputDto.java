package com.machine.client.data.file.attachment.dto.output;

import com.machine.sdk.common.envm.data.file.DataFileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;

@Data
@Schema
@NoArgsConstructor
public class DataFileDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "文件类型(DataFileTypeEnum)")
    private DataFileTypeEnum fileType;

    @Schema(description = "原始名称")
    private String originalName;

    @Schema(description = "存储名称")
    private String storageName;

    @Schema(description = "文件MD5-用于去重和秒传")
    private String md5Hash;

    @Schema(description = "文件信息")
    private FileInfo fileInfo;

    @Schema(description = "大小（字节）")
    private Long size;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

}


