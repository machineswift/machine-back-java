package com.machine.service.data.mesage.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("t_data_mq_message_external")
@EqualsAndHashCode(callSuper = true)
public class DataMqMessageExternalEntity extends BaseEntity {

    /**
     * 表名
     */
    @TableField(value = "table_name")
    private String tableName;

    /**
     * 数据Id
     */
    @TableField(value = "data_id")
    private String dataId;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 最近一次失败原因
     */
    @TableField(value = "fail_reason")
    private String failReason;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;
}