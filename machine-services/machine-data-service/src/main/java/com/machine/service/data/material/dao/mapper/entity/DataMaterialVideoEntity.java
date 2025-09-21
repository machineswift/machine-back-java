package com.machine.service.data.material.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.material.DataMaterialVideoFormatEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_material_video")
@EqualsAndHashCode(callSuper = true)
public class DataMaterialVideoEntity extends BaseEntity {
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
     * 视频宽度(像素)
     */
    @TableField("width")
    private Integer width;

    /**
     * 视频高度(像素)
     */
    @TableField("height")
    private Integer height;

    /**
     * 视频格式
     */
    @TableField("format")
    private DataMaterialVideoFormatEnum format;

    /**
     * 比特率(kbps)
     */
    @TableField("bitrate")
    private Integer bitrate;

    /**
     * 帧率
     */
    @TableField("fps")
    private Integer fps;

    /**
     * 视频编码格式
     */
    @TableField("codec")
    private String codec;

    /**
     * 音频编码格式
     */
    @TableField("audio_codec")
    private String audioCodec;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}