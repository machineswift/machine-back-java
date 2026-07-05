package com.machine.service.data.filecenter.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentChangeTypeEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentStatusEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_attachment_version")
@EqualsAndHashCode(callSuper = true)
public class DataAttachmentVersionEntity extends BaseEntity {

    /**
     * 附件ID
     */
    @TableField("attachment_id")
    private String attachmentId;

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
     * 附件分组
     */
    @TableField("attachment_group")
    private String attachmentGroup;

    /**
     * 版本号
     */
    @TableField("version_no")
    private Integer versionNo;

    /**
     * 是否为当前版本 (0-否, 1-是)
     */
    @TableField("is_current")
    private Integer isCurrent;

    /**
     * 源版本ID
     */
    @TableField("source_version_id")
    private String sourceVersionId;

    /**
     * 变更类型
     */
    @TableField("change_type")
    private DataAttachmentChangeTypeEnum changeType;

    /**
     * 变更发生时间
     */
    @TableField("change_time")
    private Long changeTime;

    /**
     * 变更描述
     */
    @TableField("change_desc")
    private String changeDesc;

}