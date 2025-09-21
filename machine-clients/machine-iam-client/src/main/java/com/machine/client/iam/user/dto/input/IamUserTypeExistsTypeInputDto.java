package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.envm.iam.IamUserTypeEnum;
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
    private IamUserTypeEnum userType;

    /**
     * 用户类型
     */
    private List<IamUserTypeEnum> userTypeList;


    public IamUserTypeExistsTypeInputDto(String userId,
                                         IamUserTypeEnum userType) {
        this.userId = userId;
        this.userType = userType;
    }

    public IamUserTypeExistsTypeInputDto(String userId,
                                         List<IamUserTypeEnum> userTypeList) {
        this.userTypeList = userTypeList;
        this.userId = userId;
    }
}
