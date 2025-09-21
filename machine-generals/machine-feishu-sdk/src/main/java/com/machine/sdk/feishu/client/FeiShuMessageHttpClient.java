package com.machine.sdk.feishu.client;

import cn.hutool.json.JSONUtil;
import com.lark.oapi.Client;
import com.lark.oapi.core.request.RequestOptions;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.contact.v3.model.BatchGetIdUser;
import com.lark.oapi.service.contact.v3.model.BatchGetIdUserReq;
import com.lark.oapi.service.contact.v3.model.BatchGetIdUserResp;
import com.lark.oapi.service.contact.v3.model.UserContactInfo;
import com.lark.oapi.service.im.v1.model.CreateMessageReq;
import com.lark.oapi.service.im.v1.model.CreateMessageResp;
import com.machine.sdk.feishu.client.dto.input.FeiShuSendMessageInput;
import com.machine.sdk.feishu.client.dto.input.MessageSendInput;
import com.machine.sdk.feishu.client.dto.input.UserBatchGetIdInput;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public class FeiShuMessageHttpClient {

    private final String appId;
    private final String appSecret;

    public FeiShuMessageHttpClient(String appId,
                                   String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }
    /**
     * 通过手机号或邮箱获取用户ID
     */
    public static BatchGetIdUserResp batchGetId(UserBatchGetIdInput input){

        log.info(">>>>>>>>>调用飞书消息API,通过手机号或邮箱获取用户ID开始<<<<<<<<<<<<<<");

        // 构建client
        Client client = Client.newBuilder(input.getAppId(), input.getAppSecret()).build();

        // 创建请求对象
        BatchGetIdUserReq req = input.buildBatchGetIdUserReq();

        // 发起请求
        BatchGetIdUserResp resp = null;
        try {
            resp = client.contact().user().batchGetId(req, RequestOptions.newBuilder().build());
        } catch (Exception e) {
            log.error("调用手机号或邮箱获取用户ID接口异常,请求header:{},返回body:{},请求ID:{}",
                    Jsons.DEFAULT.toJson(resp.getRawResponse().getHeaders())
                    ,resp.getRawResponse().getBody(),resp.getRawResponse().getRequestID());
        }

        if (!resp.success()){
            log.error("调用手机号或邮箱获取用户ID接口异常,请求header:{},返回body:{},请求ID:{}",
                    Jsons.DEFAULT.toJson(resp.getRawResponse().getHeaders())
                    ,resp.getRawResponse().getBody(),resp.getRawResponse().getRequestID());
            return null;
        }

        return resp;
    }

    /**
     * 发送消息
     * @param userBatchGetIdInput
     * @param messageSendInput
     * @return
     */
    public static List<CreateMessageResp> sendMessages(UserBatchGetIdInput userBatchGetIdInput,
                                                       MessageSendInput messageSendInput){
        log.info(">>>>>>>>>调用飞书消息API,发送飞书消息开始<<<<<<<<<<<<<<");

        BatchGetIdUserResp batchGetIdUserResp = batchGetId(userBatchGetIdInput);

        if (Objects.isNull(batchGetIdUserResp)){
            return null;
        }

        final BatchGetIdUser respBody = batchGetIdUserResp.getData();
        final UserContactInfo[] userList = respBody.getUserList();

        List<CreateMessageResp> createMessageRespList = new ArrayList<>();

        for (UserContactInfo userContactInfo : userList){
            messageSendInput.setReceiveId(userContactInfo.getUserId());
            createMessageRespList.add(sendMessages(messageSendInput));
        }
        return createMessageRespList;
    }

    /**
     * 发送消息
     * @param input
     * @return
     */
    public static CreateMessageResp sendMessages(MessageSendInput input){
        // 构建client
        Client client = Client.newBuilder(input.getAppId(), input.getAppSecret()).build();

        // 创建请求对象
        CreateMessageReq req = input.buildCreateMessageReq();

        // 发起请求
        CreateMessageResp resp = null;
        try {
            resp = client.im().message().create(req);
        } catch (Exception e) {
            log.error("调用飞书消息API,发送飞书消息异常，code:{},message:{},reqId:{},异常信息：{}",
                    resp.getCode(), resp.getMsg(), resp.getRequestId(),e.getMessage(),e);
        }
        return resp;
    }

    public void sendFeiShuMessage(FeiShuSendMessageInput dto) {
        log.info("喜管家给飞书发送卡片消息：" + JSONUtil.toJsonStr(dto));
        String elementsContent = "";
        if (StringUtils.isNotEmpty(dto.getHyperlink())) {
            //末尾部分文字做成超链接
            if (StringUtils.isNotEmpty(dto.getHyperlinkName())) {
                elementsContent = "      \"content\": \"${content}[" + dto.getHyperlinkName() + "](" + dto.getHyperlink() + ")\"\n";
            } else {
                //卡片内容整体做成超链接
                elementsContent = "      \"content\": \"[${content}](" + dto.getHyperlink() + ")\"\n";
            }
        } else {
            elementsContent = "      \"content\": \"${content}\"\n";
        }

        String messageContent = "{\n" +
                "  \"config\": {\n" +
                "    \"wide_screen_mode\": true\n" +
                "  },\n" +
                "  \"header\": {\n" +
                "    \"template\": \"blue\",\n" +
                "    \"title\": {\n" +
                "      \"tag\": \"plain_text\",\n" +
                "      \"content\": \"${title}\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"elements\": [\n" +
                "    {\n" +
                "      \"tag\": \"markdown\",\n" +
                elementsContent +
                "    }\n" +
                "  ]\n" +
                "}";

        messageContent = messageContent.replace("${title}", dto.getTitle());

        messageContent = messageContent.replace("${content}", dto.getContent());

        UserBatchGetIdInput userBatchGetIdInput = new UserBatchGetIdInput();
        userBatchGetIdInput.setAppId(appId);
        userBatchGetIdInput.setAppSecret(appSecret);
        userBatchGetIdInput.setUserIdType("open_id");
        userBatchGetIdInput.setMobiles(Arrays.stream(dto.getMobiles()).distinct().toArray(String[]::new));
        MessageSendInput messageSendInput = new MessageSendInput();
        messageSendInput.setAppId(appId);
        messageSendInput.setAppSecret(appSecret);
        messageSendInput.setMsgType("interactive");
        messageSendInput.setReceiveIdType("open_id");
        messageSendInput.setContent(messageContent);
        log.info("给【" + JSONUtil.toJsonStr(dto.getMobiles()) + "】发送飞书卡片消息，消息标题【" + dto.getTitle() + "】,消息内容：" + messageContent);
        List<CreateMessageResp> createMessageResps = sendMessages(userBatchGetIdInput, messageSendInput);
        log.info("发送飞书卡片消息结果：" + JSONUtil.toJsonStr(createMessageResps));
    }
}
