package com.machine.service.data.file.material.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.data.file.DataFileTypeEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialAuditStatusEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialBusinessStatusEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialProcessStatusEnum;
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
     * 文件类型
     */
    @TableField("file_type")
    private DataFileTypeEnum fileType;

    /**
     * 附件ID
     */
    @TableField("attachment_id")
    private String attachmentId;

    /**
     * 系统处理状态
     */
    @TableField("process_status")
    private DataMaterialProcessStatusEnum processStatus;

    /**
     * 业务状态
     */
    @TableField("business_status")
    private DataMaterialBusinessStatusEnum businessStatus;

    /**
     * 审核状态
     */
    @TableField("audit_status")
    private DataMaterialAuditStatusEnum auditStatus;

    /**
     * 标题
     */
    @TableField("title")
    private String title;
}
