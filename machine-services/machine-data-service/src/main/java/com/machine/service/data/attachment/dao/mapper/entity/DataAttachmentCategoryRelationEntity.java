package com.machine.service.data.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_attachment_category_relation")
@EqualsAndHashCode(callSuper = true)
public class DataAttachmentCategoryRelationEntity extends BaseEntity {

    /**
     * 分类ID
     */
    @TableField("category_id")
    private String categoryId;

    /**
     * 附件ID
     */
    @TableField("attachment_id")
    private String attachmentId;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}