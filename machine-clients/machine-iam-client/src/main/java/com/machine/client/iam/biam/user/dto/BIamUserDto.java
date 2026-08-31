package com.machine.client.iam.biam.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BIamUserDto {
    private String userId;
    private String username;
    private String password;
    private boolean isEnabled;
}
