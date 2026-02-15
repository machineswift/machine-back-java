package com.machine.service.data.file.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.file.DataFileTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_file")
@EqualsAndHashCode(callSuper = true)
public class DataFileEntity extends BaseEntity {

    /**
     * 文件类型
     */
    @TableField("file_type")
    private DataFileTypeEnum fileType;

    /**
     * 原始名称
     */
    @TableField("original_name")
    private String originalName;

    /**
     * 存储名称
     */
    @TableField("storage_name")
    private String storageName;

    /**
     * 文件MD5-用于去重和秒传
     */
    @TableField("md5_hash")
    private String md5Hash;

    /**
     * 文件信息
     */
    @TableField("file_info")
    private String fileInfo;

    /**
     * 大小（字节）
     */
    @TableField("size")
    private Long size;
}