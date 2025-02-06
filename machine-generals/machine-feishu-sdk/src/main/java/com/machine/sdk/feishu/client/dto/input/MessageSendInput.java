package com.machine.sdk.feishu.client.dto.input;

import com.lark.oapi.service.im.v1.model.CreateMessageReq;
import com.lark.oapi.service.im.v1.model.CreateMessageReqBody;
/**
 * @description:
 * @author：Geek Wang
 * @date: 2023/10/9
 */
public class MessageSendInput extends ClientRequest {

    /**
     * 消息接收者id类型 open_id/user_id/union_id/email/chat_id
     *
     * 示例值："open_id"
     *
     * 可选值有：
     *
     * open_id：标识一个用户在某个应用中的身份。同一个用户在不同应用中的 Open ID 不同。了解更多：如何获取 Open ID
     * user_id：标识一个用户在某个租户内的身份。同一个用户在租户 A 和租户 B 内的 User ID 是不同的。在同一个租户内，一个用户的 User ID 在所有应用（包括商店应用）中都保持一致。User ID 主要用于在不同的应用间打通用户数据。了解更多：如何获取 User ID？
     * union_id：标识一个用户在某个应用开发商下的身份。同一用户在同一开发商下的应用中的 Union ID 是相同的，在不同开发商下的应用中的 Union ID 是不同的。通过 Union ID，应用开发商可以把同个用户在多个应用中的身份关联起来。了解更多：如何获取 Union ID？
     * email：以用户的真实邮箱来标识用户。
     * chat_id：以群ID来标识群聊。了解更多：如何获取群ID
     * 当值为 user_id，字段权限要求：
     */
    private String receiveIdType;

    /**
     * 消息接收者的ID，ID类型应与查询参数receive_id_type 对应；推荐使用 OpenID
     */
    private String receiveId;

    /**
     * 消息类型 包括：text、post、image、file、audio、media、sticker、interactive、share_chat、share_user等，类型定义请参考发送消息内容
     */
    private String msgType;

    /**
     * 消息内容，JSON结构序列化后的字符串。不同msg_type对应不同内容，具体格式说明参考：发送消息Content
     *
     * 注意：
     * JSON字符串需进行转义，如换行符转义后为\\n
     * 文本消息请求体最大不能超过150KB
     * 卡片及富文本消息请求体最大不能超过30KB
     * 示例值："{"text":"test content"}"
     */
    private String content;

    /**
     * 由开发者生成的唯一字符串序列，用于发送消息请求去重；持有相同uuid的请求1小时内至多成功发送一条消息
     *
     * 示例值："选填，若填写每次调用前请更换，如：a0d69e20-1dd1-458b-k525-dfeca4015204"
     *
     * 数据校验规则：
     *
     * 最大长度：50 字符
     */
    private String uuid;

    public String getReceiveIdType() {
        return receiveIdType;
    }

    public void setReceiveIdType(String receiveIdType) {
        this.receiveIdType = receiveIdType;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 构建消息内容体
     * @return
     */
    public CreateMessageReq buildCreateMessageReq(){
        return CreateMessageReq.newBuilder()
                .receiveIdType(receiveIdType)
                .createMessageReqBody(CreateMessageReqBody.newBuilder()
                        .receiveId(receiveId)
                        .msgType(msgType)
                        .content(content)
                        .uuid(uuid)
                        .build())
                .build();
    }
}
