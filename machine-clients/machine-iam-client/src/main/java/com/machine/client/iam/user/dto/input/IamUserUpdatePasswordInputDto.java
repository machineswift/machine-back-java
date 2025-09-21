package com.machine.client.iam.user.dto.input;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamUserUpdatePasswordInputDto {

    public IamUserUpdatePasswordInputDto(String userId,
                                         String password) {
        this.userId = userId;
        this.password = password;
    }

    private String userId;

    private String password;
}
