package com.machine.service.data.filecenter.material.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_material_reference")
@EqualsAndHashCode(callSuper = true)
public class DataMaterialReferenceEntity extends BaseEntity {

    /**
     * 素材ID
     */
    @TableField("material_id")
    private String materialId;

    /**
     * 附件ID
     */
    @TableField("attachment_id")
    private String attachmentId;

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
}
