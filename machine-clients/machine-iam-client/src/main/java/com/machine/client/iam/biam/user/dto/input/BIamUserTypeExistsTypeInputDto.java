package com.machine.client.iam.biam.user.dto.input;

import com.machine.sdk.base.envm.biam.user.BIamUserTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BIamUserTypeExistsTypeInputDto {

    @NotBlank(message = "用户id不能为空")
    private String userId;

    /**
     * 用户类型
     */
    private BIamUserTypeEnum userType;

    /**
     * 用户类型
     */
    private List<BIamUserTypeEnum> userTypeList;


    public BIamUserTypeExistsTypeInputDto(String userId,
                                          BIamUserTypeEnum userType) {
        this.userId = userId;
        this.userType = userType;
    }

    public BIamUserTypeExistsTypeInputDto(String userId,
                                          List<BIamUserTypeEnum> userTypeList) {
        this.userTypeList = userTypeList;
        this.userId = userId;
    }
}
