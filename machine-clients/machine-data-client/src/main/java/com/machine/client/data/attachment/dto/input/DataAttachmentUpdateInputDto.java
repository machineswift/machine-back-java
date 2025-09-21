package com.machine.client.data.attachment.dto.input;

import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentUpdateInputDto {

    @Schema(description = "Id")
    private String id;

    @Schema(description = "持久化类型（SystemStorageTypeEnum）")
    private SystemStorageTypeEnum storageType;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "分类集合")
    private Set<String> categoryIdSet;

    @Schema(description = "过期时间（默认30年）")
    private Long expireTime;

    @Schema(description = "描述")
    private String description;

}


