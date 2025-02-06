package com.machine.service.data.information.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.informaion.input.*;
import com.machine.client.data.informaion.output.AppMessageContentDto;
import com.machine.client.data.informaion.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.informaion.output.AppMessageListOutputDto;
import com.machine.client.data.informaion.output.AppMessageListSuperOutputDto;
import com.machine.client.data.messageTemplate.dto.input.AppMessageTemplateDetailByTypeInputDto;
import com.machine.client.data.messageTemplate.dto.output.AppMessageTemplateDetailOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.data.MessageChannelEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.sdk.feishu.client.FeiShuMessageHttpClient;
import com.machine.sdk.feishu.client.dto.input.FeiShuSendMessageInput;
import com.machine.service.data.information.dao.IAppMessageDao;
import com.machine.service.data.information.dao.mapper.AppMessageMapper;
import com.machine.service.data.information.dao.mapper.entity.AppMessageEntity;
import com.machine.service.data.information.service.IAppMessageService;
import com.machine.service.data.messageTemplate.service.IAppMessageTemplateService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppMessageServiceImpl implements IAppMessageService {

    @Autowired
    private IAppMessageDao appMessageDao;

    @Resource(name = "feiShuMessageHttpClient")
    private FeiShuMessageHttpClient feishuMessageHttpClient;

    @Autowired
    private IAppMessageTemplateService appMessageTemplateService;

    @Autowired
    private AppMessageMapper appMessageMapper;

    @Autowired
    private IIamUserClient userClient;

    @Override
    public PageResponse<AppMessageListOutputDto> managePage(AppMessagePageInputDto inputDto) {
        Page<AppMessageListOutputDto> outputDtoPage = appMessageDao.managePage(inputDto);
        List<AppMessageListOutputDto> records = outputDtoPage.getRecords();
        if (CollectionUtil.isEmpty(records)) {
            return new PageResponse<>(outputDtoPage.getCurrent(), outputDtoPage.getSize(), outputDtoPage.getTotal());
        }
        for (AppMessageListOutputDto record : records) {
            AppMessageContentDto bean = JSONUtil.toBean(JSONUtil.toJsonStr(record.getBusinessContent()), AppMessageContentDto.class);
            record.setBusinessContentDto(bean);
        }
        return new PageResponse<>(
                outputDtoPage.getCurrent(),
                outputDtoPage.getSize(),
                outputDtoPage.getTotal(),
                records);
    }

    @Override
    public PageResponse<AppMessageListSuperOutputDto> superPage(AppMessagePageSuperInputDto inputDto) {
        String userId = AppContext.getContext().getUserId();
        inputDto.setReceiver(userId);
        Page<AppMessageListSuperOutputDto> outputDtoPage = appMessageDao.superPage(inputDto);
        List<AppMessageListSuperOutputDto> records = outputDtoPage.getRecords();
        if (CollectionUtil.isEmpty(records)) {
            return new PageResponse<>(outputDtoPage.getCurrent(), outputDtoPage.getSize(), outputDtoPage.getTotal());
        }
        for (AppMessageListSuperOutputDto record : records) {
            AppMessageContentDto bean = JSONUtil.toBean(JSONUtil.toJsonStr(record.getBusinessContent()), AppMessageContentDto.class);
            record.setBusinessContentDto(bean);
        }
        return new PageResponse<>(
                outputDtoPage.getCurrent(),
                outputDtoPage.getSize(),
                outputDtoPage.getTotal(),
                records);
    }

    @Override
    public Boolean saveMessageRecord(AppMessageSaveInputDto request) {
        if (StringUtils.isBlank(request.getBatchCode())) {
            request.setBatchCode(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        }
        log.info("本次存储消息的批次号为：{}", request.getBatchCode());
        Set<String> receiverSet = request.getReceiverSet();
        if (CollectionUtils.isEmpty(receiverSet)) {
            throw new IamBusinessException("data.message.saveMessageRecord.receiverIsNull", "接收人不能为空");
        }
        AppMessageTemplateDetailByTypeInputDto inputDto = new AppMessageTemplateDetailByTypeInputDto();
        inputDto.setTemplateType(request.getMessageTemplateTypeEnum());
        AppMessageTemplateDetailOutputDto outputDto = appMessageTemplateService.detailByType(inputDto);
        if(outputDto == null || CollectionUtil.isEmpty(outputDto.getChannels())) {
            throw new IamBusinessException("data.message.sendMessage.channelsIsNull", "根据模板查询渠道失败");
        }
        Set<MessageChannelEnum> channels = outputDto.getChannels();

        for (String receiver : receiverSet) {
            for (MessageChannelEnum channel : channels) {
                AppMessageEntity appMessageEntity = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageEntity.class);
                appMessageEntity.setBusinessContent(JSONUtil.toJsonStr(request.getBusinessContentDto()));
                appMessageEntity.setReceiver(receiver);
                appMessageEntity.setChannel(channel.getCode());
                appMessageEntity.setMessageTemplateId(outputDto.getId());
                appMessageDao.insert(appMessageEntity);
            }
        }
        return true;
    }

    @Override
    public Boolean sendMessage(AppMessageSendInputDto appMessageSendInputDto) {
        AppMessageTemplateDetailByTypeInputDto inputDto = new AppMessageTemplateDetailByTypeInputDto();
        inputDto.setTemplateType(appMessageSendInputDto.getMessageTemplateTypeEnum());
        AppMessageTemplateDetailOutputDto outputDto = appMessageTemplateService.detailByType(inputDto);
        if(outputDto == null || CollectionUtil.isEmpty(outputDto.getChannels())) {
            throw new IamBusinessException("data.message.sendMessage.channelsIsNull", "根据模板查询渠道失败");
        }
        for (MessageChannelEnum channel : outputDto.getChannels()) {
            if(MessageChannelEnum.FEI_SHU_ROBOT.equals(channel)){
                sendFeiShuMessage(appMessageSendInputDto);
            }else if(MessageChannelEnum.ENTERPRISE_WECHAT.equals(channel)){

            }else if(MessageChannelEnum.SMS.equals(channel)){

            }
        }
        AppMessageSaveInputDto saveInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(appMessageSendInputDto), AppMessageSaveInputDto.class);
        saveMessageRecord(saveInputDto);
        return true;
    }

    private void sendFeiShuMessage(AppMessageSendInputDto request) {
        FeiShuSendMessageInput feiShuSendMessageInput = new FeiShuSendMessageInput();
        feiShuSendMessageInput.setMobiles(convertIdToMobiles(request.getReceiverSet()).toArray(String[]::new));
        feiShuSendMessageInput.setTitle(request.getTitle());
        feiShuSendMessageInput.setContent(request.getInformContent());
        feishuMessageHttpClient.sendFeiShuMessage(feiShuSendMessageInput);
    }

    private List<String> convertIdToMobiles(Set<String> ids) {
        IdSetRequest request = new IdSetRequest();
        request.setIdSet(ids);
        Map<String, UserDetailOutputDto> map = userClient.mapByIdSet(request);
        if (CollectionUtil.isEmpty(map)) {
            return null;
        }
        return map.values().stream().map(UserDetailOutputDto::getPhone).collect(Collectors.toList());
    }

    @Override
    public Boolean readMessage(AppMessageReadInputDto request) {
        String messageId = request.getMessageId();
        if (StringUtils.isBlank(messageId)) {
            throw new IamBusinessException("data.message.readMessage.messageIdIsNull", "消息id不能为空");
        }
        appMessageDao.readMessage(messageId);
        return true;
    }

    @Override
    public Boolean disposeMessage(AppMessageReadInputDto request) {
        String messageId = request.getMessageId();
        if (StringUtils.isBlank(messageId)) {
            throw new IamBusinessException("data.message.disposeMessage.messageIdIsNull", "消息id不能为空");
        }
        appMessageDao.disposeMessage(messageId);
        return true;
    }

    @Override
    public List<AppMessageGroupCountOutputDto> groupCount(AppMessageGroupCountInputDto inputDto) {
        String userId = AppContext.getContext().getUserId();
        inputDto.setReceiver(userId);
        log.info("分组查询当前用户未读消息，input={}", JSONUtil.toJsonStr(inputDto));
        return appMessageDao.groupCount(inputDto);
    }

    @Override
    public Integer getUnreadCount(AppMessageUnreadCountInputDto inputDto) {
        String userId = AppContext.getContext().getUserId();
        inputDto.setReceiver(userId);
        return appMessageDao.getUnreadCount(inputDto);
    }
}
