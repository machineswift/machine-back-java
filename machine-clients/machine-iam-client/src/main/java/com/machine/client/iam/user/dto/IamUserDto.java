package com.machine.client.iam.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamUserDto {
    private String userId;
    private String username;
    private String password;
    private boolean isEnabled;
}
