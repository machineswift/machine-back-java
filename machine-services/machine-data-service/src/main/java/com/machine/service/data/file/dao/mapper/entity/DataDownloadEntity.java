package com.machine.service.data.file.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.download.DataDownloadStatusEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("t_data_download")
@EqualsAndHashCode(callSuper = true)
public class DataDownloadEntity extends BaseEntity {

    /**
     * 任务状态
     */
    @TableField("status")
    private DataDownloadStatusEnum status;

    /**
     * 附件ID
     */
    @TableField("attachment_id")
    private String attachmentId;


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