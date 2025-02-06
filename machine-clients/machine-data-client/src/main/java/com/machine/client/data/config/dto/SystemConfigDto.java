package com.machine.client.data.config.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SystemConfigDto {

    public SystemConfigDto(String category,
                           String code) {
        this.category = category;
        this.code = code;
    }

    public SystemConfigDto(String category,
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

    private String content;

}
