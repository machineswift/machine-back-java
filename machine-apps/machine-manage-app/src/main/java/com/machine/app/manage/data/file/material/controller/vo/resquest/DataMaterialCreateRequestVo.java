package com.machine.app.manage.data.file.material.controller.vo.resquest;

import com.machine.sdk.common.envm.data.file.DataFileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialCreateRequestVo {

    @NotBlank(message = "素材Id不能为空")
    @Schema(description = "素材Id")
    private String materialId;

    @NotNull(message = "文件类型不能为空")
    @Schema(description = "文件类型")
    private DataFileTypeEnum fileType;

    @NotBlank(message = "素材标题不能为空")
    @Schema(description = "素材标题")
    private String title;

    @Schema(description = "素材分类集合")
    private Set<String> categoryIdSet;

    @Schema(description = "过期时间")
    private Long expireTime;

    @Schema(description = "描述")
    private String description;

}
