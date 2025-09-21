package com.machine.service.data.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_attachment_category")
@EqualsAndHashCode(callSuper = true)
public class DataAttachmentCategoryEntity extends BaseEntity {
    /**
     * 父分类ID
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 分类编码
     */
    @TableField("code")
    private String code;

    /**
     * 分类名称
     */
    @TableField("name")
    private String name;

    /**
     * 排序，值大的靠前
     */
    @TableField("sort")
    private Long sort;
}
