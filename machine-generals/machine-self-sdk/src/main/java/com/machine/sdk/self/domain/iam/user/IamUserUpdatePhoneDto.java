package com.machine.sdk.self.domain.iam.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamUserUpdatePhoneDto {

    private String id;

    private String phone;

    public IamUserUpdatePhoneDto(String id,
                                 String phone) {
        this.id = id;
        this.phone = phone;
    }
}
