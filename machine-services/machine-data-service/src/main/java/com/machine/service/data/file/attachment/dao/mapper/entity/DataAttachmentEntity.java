package com.machine.service.data.file.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.sdk.common.envm.data.file.DataAttachmentStatusEnum;
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
     * 实体
     */
    @TableField("entity")
    private ModuleEntityEnum entity;

    /**
     * 实体Id
     */
    @TableField("entity_id")
    private String entityId;

    /**
     * 文件Id
     */
    @TableField("file_id")
    private String fileId;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private Long expireTime;

}