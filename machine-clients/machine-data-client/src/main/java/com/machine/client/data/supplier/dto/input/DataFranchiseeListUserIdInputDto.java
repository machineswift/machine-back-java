package com.machine.client.data.supplier.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class DataFranchiseeListUserIdInputDto {

    @Schema(description = "用户ID集合")
    private Set<String> userIdSet;

    public DataFranchiseeListUserIdInputDto(Set<String> userIdSet) {
        this.userIdSet = userIdSet;
    }
}
