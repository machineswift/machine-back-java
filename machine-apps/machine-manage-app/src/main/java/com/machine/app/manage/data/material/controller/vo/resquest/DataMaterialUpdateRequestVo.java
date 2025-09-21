package com.machine.app.manage.data.material.controller.vo.resquest;

import com.machine.client.data.material.dto.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialUpdateRequestVo {

    @NotBlank(message = "Id不能为空")
    @Schema(description = "Id")
    private String id;

    @NotBlank(message = "素材标题不能为空")
    @Schema(description = "素材标题")
    private String title;

    @Schema(description = "素材分类集合")
    private Set<String> categoryIdSet;

    @Schema(description = "过期时间（默认30年）")
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
