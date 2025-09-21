package com.machine.client.data.message.dto.output;

import com.machine.sdk.common.envm.data.message.DataMessageTemplateCategoryEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppMessageGroupCountOutputDto {
    /**
     * 模版分类(合同、选址任务等)
     */
    private DataMessageTemplateCategoryEnum templateCategory;

    /**
     * 通知内容
     */
    private String informContent;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 数量
     */
    private Integer count;
}