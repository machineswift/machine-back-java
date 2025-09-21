package com.machine.service.data.external.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_data_external_field_data")
public class DataExternalFieldDataEntity extends BaseEntity {
    /**
     * 表名
     */
    @TableField("table_name")
    private String tableName;

    /**
     * 字段名
     */
    @TableField("field_name")
    private String fieldName;

    /**
     * 数据Id
     */
    @TableField("data_id")
    private String dataId;

    /**
     * 扩展字段值
     */
    @TableField("external_value")
    private String externalValue;
}