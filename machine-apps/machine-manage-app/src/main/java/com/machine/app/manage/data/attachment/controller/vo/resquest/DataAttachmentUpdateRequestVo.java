package com.machine.app.manage.data.attachment.controller.vo.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentUpdateRequestVo {

    @NotBlank(message = "Id不能为空")
    @Schema(description = "Id")
    private String id;

    @NotBlank(message = "附件标题不能为空")
    @Schema(description = "附件标题")
    private String title;

    @Schema(description = "附件分类集合")
    private Set<String> categoryIdSet;

    @Schema(description = "过期时间（默认30年）")
    private Long expireTime;

    @Schema(description = "描述")
    private String description;

}
