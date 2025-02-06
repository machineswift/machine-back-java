package com.machine.client.iam.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String userName;
    private String password;
    private boolean isEnabled;
}
