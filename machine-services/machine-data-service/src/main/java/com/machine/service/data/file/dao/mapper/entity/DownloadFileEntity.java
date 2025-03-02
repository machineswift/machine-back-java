package com.machine.service.data.file.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.MaterIalTypeEnum;
import com.machine.sdk.common.envm.data.download.DownLoadFileChannelEnum;
import com.machine.sdk.common.envm.data.download.ExportTaskStatusEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("t_download_file")
@EqualsAndHashCode(callSuper = true)
public class DownloadFileEntity extends BaseEntity {

    /**
     * 渠道
     */
    @TableField("channel")
    private DownLoadFileChannelEnum channel;

    /**
     * 任务状态
     */
    @TableField("status")
    private ExportTaskStatusEnum status;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private MaterIalTypeEnum fileType;

    /**
     * url地址md5
     */
    @TableField("url_md5")
    private String urlMd5;

    /**
     * 附件信息
     */
    @TableField("material")
    private String material;

    /**
     * 过期时间
     */
    @TableField("expiration_in")
    private Long expirationIn;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 失败原因
     */
    @TableField("fail_cause")
    private String failCause;

}