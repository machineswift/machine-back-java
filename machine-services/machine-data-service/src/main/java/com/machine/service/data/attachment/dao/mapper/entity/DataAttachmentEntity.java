package com.machine.service.data.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.attachment.DataAttachmentStatusEnum;
import com.machine.sdk.common.envm.data.attachment.DataAttachmentTypeEnum;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_attachment")
@EqualsAndHashCode(callSuper = true)
public class DataAttachmentEntity extends BaseEntity {
    /**
     * 状态
     */
    @TableField("status")
    private DataAttachmentStatusEnum status;

    /**
     * 类型
     */
    @TableField("type")
    private DataAttachmentTypeEnum type;

    /**
     * 持久化类型
     */
    @TableField("storage_type")
    private SystemStorageTypeEnum storageType;

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
     * 大小（字节）
     */
    @TableField("size")
    private Long size;

    /**
     * obs文件信息
     */
    @TableField("file_info")
    private String fileInfo;
    /**
     * 过期时间
     */
    @TableField("expire_time")
    private Long expireTime;

    /**
     * 文件描述
     */
    @TableField("description")
    private String description;
}