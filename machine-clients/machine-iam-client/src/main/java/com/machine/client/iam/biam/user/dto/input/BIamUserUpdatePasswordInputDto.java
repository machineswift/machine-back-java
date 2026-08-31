package com.machine.client.iam.biam.user.dto.input;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BIamUserUpdatePasswordInputDto {

    public BIamUserUpdatePasswordInputDto(String userId,
                                          String password) {
        this.userId = userId;
        this.password = password;
    }

    private String userId;

    private String password;
}
