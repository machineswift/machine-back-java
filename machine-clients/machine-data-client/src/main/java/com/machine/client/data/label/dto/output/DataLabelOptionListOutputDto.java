package com.machine.client.data.label.dto.output;

import com.machine.sdk.common.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataLabelOptionListOutputDto {

    private String id;

    /**
     * 人工标签ID
     */
    private String labelId;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     * {@link StatusEnum}
     */
    private StatusEnum status;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建时间（Unix 时间戳）
     */
    private Long createTime;

    /**
     * 操作人ID
     */
    private String updateBy;

    /**
     * 更新时间（Unix 时间戳）
     */
    private Long updateTime;
}
