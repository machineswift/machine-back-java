package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.IamUserTypeEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamUserQueryListOffsetInputDto {

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 类型
     */
    private IamUserTypeEnum userType;

    /**
     * 偏移量，不传的话取默认值。下一次请求取当前结果集最后一条数据的ID。
     */
    private String offset;

    /**
     * 支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，最大100。
     */
    @Min(10)
    @Max(100)
    @NotNull(message = "分页大小不能为空")
    private Integer size;

}
