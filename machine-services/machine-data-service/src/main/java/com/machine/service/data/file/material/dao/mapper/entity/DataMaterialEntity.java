package com.machine.service.data.file.material.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_material")
@EqualsAndHashCode(callSuper = true)
public class DataMaterialEntity extends BaseEntity {

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * obs文件信息
     */
    @TableField("file_info")
    private String fileInfo;

    /**
     * 大小（字节）
     */
    @TableField("size")
    private Long size;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private Long expireTime;
}
