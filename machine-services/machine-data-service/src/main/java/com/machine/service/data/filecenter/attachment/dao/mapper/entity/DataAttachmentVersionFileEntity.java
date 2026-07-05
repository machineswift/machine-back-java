package com.machine.service.data.filecenter.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_attachment_version_file")
@EqualsAndHashCode(callSuper = true)
public class DataAttachmentVersionFileEntity extends BaseEntity {

    /**
     * 附件版本ID
     */
    @TableField("attachment_version_id")
    private String attachmentVersionId;

    /**
     * 文件ID
     */
    @TableField("file_id")
    private String fileId;

    /**
     * 排序号
     */
    @TableField("sort")
    private Long sort;

    /**
     * 扩展信息JSON
     */
    @TableField("features")
    private String features;

}