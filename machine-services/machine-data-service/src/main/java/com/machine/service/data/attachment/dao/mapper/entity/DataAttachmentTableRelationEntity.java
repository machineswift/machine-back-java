package com.machine.service.data.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.system.SystemTableNameEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_attachment_table_relation")
@EqualsAndHashCode(callSuper = true)
public class DataAttachmentTableRelationEntity extends BaseEntity {
    /**
     * 附件ID
     */
    @TableField("attachment_id")
    private String attachmentId;

    /**
     * 表名
     */
    @TableField("table_name")
    private SystemTableNameEnum tableName;

    /**
     * 数据Id
     */
    @TableField("data_id")
    private String dataId;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;

}