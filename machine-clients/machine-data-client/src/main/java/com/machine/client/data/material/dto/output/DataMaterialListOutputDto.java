package com.machine.client.data.material.dto.output;

import com.machine.client.data.material.dto.*;
import com.machine.sdk.common.envm.data.material.DataMaterIalTypeEnum;
import com.machine.sdk.common.envm.data.material.DataMaterialStatusEnum;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialListOutputDto implements IDataMaterialOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "状态（DataMaterialStatusEnum）")
    private DataMaterialStatusEnum status;

    @Schema(description = "类型（DataMaterIalTypeEnum）")
    private DataMaterIalTypeEnum type;

    @Schema(description = "持久化类型（SystemStorageTypeEnum）")
    private SystemStorageTypeEnum storageType;

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

    /**
     * 根据不同的类型接受对应的元数据
     */
    @Schema(description = "文本素材元信息")
    private DataMaterialTextDto textMetaInfo;

    @Schema(description = "图片素材元信息")
    private DataMaterialImageDto imageMetaInfo;

    @Schema(description = "音频素材元信息")
    private DataMaterialAudioDto audioMetaInfo;

    @Schema(description = "视频素材元信息")
    private DataMaterialVideoDto videoMetaInfo;

    @Schema(description = "文档素材元信息")
    private DataMaterialDocumentDto documentMetaInfo;

    @Schema(description = "文件素材元信息")
    private DataMaterialFileDto fileMetaInfo;
}


