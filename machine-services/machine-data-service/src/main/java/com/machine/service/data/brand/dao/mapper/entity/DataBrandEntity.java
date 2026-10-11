package com.machine.service.data.brand.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("t_data_brand")
@EqualsAndHashCode(callSuper = true)
public class DataBrandEntity extends BaseEntity {

    /**
     * 父品牌ID
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
     * 拼音全拼
     */
    @TableField("name_pinyin_full")
    private String namePinyinFull;

    /**
     * 拼音首字母
     */
    @TableField("name_pinyin_initial")
    private String namePinyinInitial;

    /**
     * 英文名称
     */
    @TableField("name_en")
    private String nameEn;

    /**
     * 状态
     * {@link StatusEnum}
     */
    @TableField("status")
    private StatusEnum status;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;

    /**
     * LOGO 附件Id
     */
    @TableField("logo_attachment_id")
    private String logoAttachmentId;


    /**
     * 描述
     */
    @TableField("description")
    private String description;


}
