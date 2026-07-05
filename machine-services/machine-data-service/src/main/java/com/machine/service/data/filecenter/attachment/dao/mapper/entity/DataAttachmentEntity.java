package com.machine.service.data.filecenter.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentStatusEnum;
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
     * 附件分组（同一分组所有版本共享）
     */
    @TableField("attachment_group")
    private String attachmentGroup;

    /**
     * 当前版本ID
     */
    @TableField("current_version_id")
    private String currentVersionId;

    /**
     * 最大版本号
     */
    @TableField("max_version_no")
    private Integer maxVersionNo;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private Long expireTime;

}