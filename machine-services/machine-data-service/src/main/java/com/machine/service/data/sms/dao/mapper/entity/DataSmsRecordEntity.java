package com.machine.service.data.sms.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.sms.DataSmsCategoryEnum;
import com.machine.sdk.common.envm.data.sms.DataSmsSendResultEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_sms_record")
@EqualsAndHashCode(callSuper = true)
public class DataSmsRecordEntity extends BaseEntity {

    /**
     * 分类
     */
    @TableField("category")
    private DataSmsCategoryEnum category;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 结果
     */
    @TableField("result")
    private DataSmsSendResultEnum result;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 消息内容
     */
    @TableField("message_content")
    private String messageContent;

    /**
     * 返回内容
     */
    @TableField("result_content")
    private String resultContent;

    /**
     * 失败原因
     */
    @TableField("fail_reason")
    private String failReason;

}
