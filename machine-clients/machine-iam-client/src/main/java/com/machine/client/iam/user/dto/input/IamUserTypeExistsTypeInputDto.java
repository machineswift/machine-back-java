package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.envm.iam.UserTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IamUserTypeExistsTypeInputDto {

    @NotBlank(message = "用户id不能为空")
    private String userId;

    /**
     * 用户类型
     */
    private UserTypeEnum userType;

    /**
     * 用户类型
     */
    private List<UserTypeEnum> userTypeList;


    public IamUserTypeExistsTypeInputDto(String userId,
                                         UserTypeEnum userType) {
        this.userId = userId;
        this.userType = userType;
    }

    public IamUserTypeExistsTypeInputDto(String userId,
                                         List<UserTypeEnum> userTypeList) {
        this.userTypeList = userTypeList;
        this.userId = userId;
    }
}
