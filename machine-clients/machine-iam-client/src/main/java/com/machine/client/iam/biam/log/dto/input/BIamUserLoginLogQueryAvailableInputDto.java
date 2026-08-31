
package com.machine.client.iam.biam.log.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BIamUserLoginLogQueryAvailableInputDto {

    @NotNull(message = "用户id集合不能为空")
    private List<String> userIdList;

    public Long currentTimeMillis;

    public BIamUserLoginLogQueryAvailableInputDto(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
