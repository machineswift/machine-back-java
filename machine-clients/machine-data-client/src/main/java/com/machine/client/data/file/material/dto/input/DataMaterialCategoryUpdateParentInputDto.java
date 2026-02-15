package com.machine.client.data.file.material.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataMaterialCategoryUpdateParentInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "父id不能为空")
    private String parentId;
}
