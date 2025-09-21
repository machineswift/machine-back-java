package com.machine.service.iam.user.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.auth.IamAuth2SourceEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_iam_third_party_user")
public class IamThirdPartyUserEntity extends BaseEntity {
    /**
     * 来源
     */
    @TableField("source")
    private IamAuth2SourceEnum source;

    @TableField("uuid")
    private String uuid;

    /**
     * 用户名（账号）
     */
    @TableField("user_name")
    private String userName;

    /**
     * 昵称
     */
    @TableField("display_name")
    private String displayName;

    /**
     * 头像
     */
    @TableField("head_picture_url")
    private String headPictureUrl;

    /**
     * 内容
     */
    @TableField("content")
    private String content;
}