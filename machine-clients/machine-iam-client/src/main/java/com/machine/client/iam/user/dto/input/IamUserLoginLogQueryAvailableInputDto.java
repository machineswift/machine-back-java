
package com.machine.client.iam.user.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IamUserLoginLogQueryAvailableInputDto {

    @NotNull(message = "用户id集合不能为空")
    private List<String> userIdList;

    public Long currentTimeMillis;

    public IamUserLoginLogQueryAvailableInputDto(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
