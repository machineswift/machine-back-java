package com.machine.service.data.config.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_system_config")
@EqualsAndHashCode(callSuper = true)
public class SystemConfigEntity extends BaseEntity {

    /**
     * 分类
     */
    @TableField("category")
    private String category;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 内容
     */
    @TableField("content")
    private String content;
}