package com.machine.service.data.material.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.material.DataMaterIalTypeEnum;
import com.machine.sdk.common.envm.data.material.DataMaterialStatusEnum;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
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
     * 状态
     */
    @TableField("status")
    private DataMaterialStatusEnum status;

    /**
     * 类型
     */
    @TableField("type")
    private DataMaterIalTypeEnum type;

    /**
     * 持久化类型
     */
    @TableField("storage_type")
    private SystemStorageTypeEnum storageType;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * obs文件信息
     */
    @TableField("file_info")
    private String fileInfo;

    /**
     * 大小（字节）
     */
    @TableField("size")
    private Long size;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private Long expireTime;
}
