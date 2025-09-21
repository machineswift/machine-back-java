package com.machine.client.data.config.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataSystemConfigDto {

    public DataSystemConfigDto(String category,
                               String code) {
        this.category = category;
        this.code = code;
    }

    public DataSystemConfigDto(String category,
                               String code,
                               String content) {
        this.category = category;
        this.code = code;
        this.content = content;
    }

    @NotBlank(message = "分类不能为空")
    private String category;

    @NotBlank(message = "编码不能为空")
    private String code;

    @NotBlank(message = "内容")
    private String content;

}
