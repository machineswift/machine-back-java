package com.machine.service.data.tag.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_tag_category")
@EqualsAndHashCode(callSuper = true)
public class DataTagCategoryEntity extends BaseEntity {

    /**
     * {@link ProfileSubjectTypeEnum}
     */
    @TableField("type")
    private ProfileSubjectTypeEnum type;
    
    /**
     * 父ID
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 排序，sort值大的排序靠前
     */
    @TableField("sort")
    private Integer sort;
}
