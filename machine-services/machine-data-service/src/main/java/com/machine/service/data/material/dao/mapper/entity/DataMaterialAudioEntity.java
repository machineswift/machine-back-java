package com.machine.service.data.material.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.material.DataMaterialAudioFormatEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_material_audio")
@EqualsAndHashCode(callSuper = true)
public class DataMaterialAudioEntity extends BaseEntity {
    /**
     * 素材ID
     */
    @TableField("material_id")
    private String materialId;

    /**
     * 时长(秒)
     */
    @TableField("duration")
    private Integer duration;

    /**
     * 音频格式
     */
    @TableField("format")
    private DataMaterialAudioFormatEnum format;

    /**
     * 比特率(kbps)
     */
    @TableField("bitrate")
    private Integer bitrate;

    /**
     * 采样率(Hz)
     */
    @TableField("sample_rate")
    private Integer sampleRate;

    /**
     * 声道数(1-单声道,2-立体声)
     */
    @TableField("channels")
    private Integer channels;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
