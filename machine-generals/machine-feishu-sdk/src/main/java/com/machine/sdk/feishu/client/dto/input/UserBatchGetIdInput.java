package com.machine.sdk.feishu.client.dto.input;

import com.lark.oapi.service.contact.v3.model.BatchGetIdUserReq;
import com.lark.oapi.service.contact.v3.model.BatchGetIdUserReqBody;

/**
 * @description:
 * @author：Geek Wang
 * @date: 2023/10/9
 */
public class UserBatchGetIdInput extends ClientRequest {

    /**
     * 用户 ID 类型
     *
     *
     * 可选值有：
     *
     * open_id：标识一个用户在某个应用中的身份。同一个用户在不同应用中的 Open ID 不同。了解更多：如何获取 Open ID
     * union_id：标识一个用户在某个应用开发商下的身份。同一用户在同一开发商下的应用中的 Union ID 是相同的，在不同开发商下的应用中的 Union ID 是不同的。通过 Union ID，应用开发商可以把同个用户在多个应用中的身份关联起来。
     * user_id：标识一个用户在某个租户内的身份。同一个用户在租户 A 和租户 B 内的 User ID 是不同的。在同一个租户内，一个用户的 User ID 在所有应用（包括商店应用）中都保持一致。User ID 主要用于在不同的应用间打通用户数据。
     * 默认值：open_id
     */
    private String userIdType;

    /**
     * 要查询的用户邮箱（不支持企业邮箱），最多 50 条。
     *
     * 注意，emails与mobiles相互独立，每条用户邮箱返回对应的用户ID。
     *
     * 本接口返回的用户ID数量为emails数量与mobiles数量的和。
     *
     * 示例值：["abc@z.com"]
     *
     * 数据校验规则：
     *
     * 最大长度：50
     */
    private String[] emails;

    /**
     * 要查询的用户手机号，最多 50 条。
     *
     * 注意
     *
     * emails与mobiles相互独立，每条用户手机号返回对应的用户ID。
     * 非中国大陆地区的手机号需要添加以 “+” 开头的国家 / 地区代码。
     * 示例值：["13812345678"]
     *
     * 数据校验规则：
     *
     * 最大长度：50
     */
    private String[] mobiles;

    public String getUserIdType() {
        return userIdType;
    }

    public void setUserIdType(String userIdType) {
        this.userIdType = userIdType;
    }

    public String[] getEmails() {
        return emails;
    }

    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    public String[] getMobiles() {
        return mobiles;
    }

    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }

    public BatchGetIdUserReq buildBatchGetIdUserReq(){
        return BatchGetIdUserReq.newBuilder()
                .userIdType(userIdType)
                .batchGetIdUserReqBody(BatchGetIdUserReqBody.newBuilder()
                        .emails(emails)
                        .mobiles(mobiles)
                        .build())
                .build();
    }
}
