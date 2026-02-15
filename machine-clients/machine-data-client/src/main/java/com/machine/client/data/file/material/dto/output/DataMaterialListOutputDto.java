package com.machine.client.data.file.material.dto.output;

import com.machine.sdk.common.envm.data.file.DataFileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialListOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "文件类型（DataFileTypeEnum）")
    private DataFileTypeEnum fileType;

    @Schema(description = "素材标题")
    private String title;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "素材分类集合")
    private Set<String> categoryIdSet;

    @Schema(description = "obs文件信息")
    private String fileInfo;

    @Schema(description = "大小（字节）")
    private Long size;

    @Schema(description = "过期时间")
    private Long expireTime;

    @Schema(description = "描述")
    private String description;

}


