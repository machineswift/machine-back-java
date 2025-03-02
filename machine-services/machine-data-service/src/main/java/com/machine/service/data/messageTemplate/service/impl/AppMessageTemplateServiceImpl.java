package com.machine.service.data.messageTemplate.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.informaion.input.AppMessageTemplateDto;
import com.machine.client.data.messageTemplate.dto.input.*;
import com.machine.client.data.messageTemplate.dto.output.AppMessageTemplateDetailOutputDto;
import com.machine.client.data.messageTemplate.dto.output.AppMessageTemplateInfoDto;
import com.machine.client.data.messageTemplate.dto.output.AppMessageTemplateListOutPutDto;
import com.machine.sdk.common.envm.data.message.MessageChannelEnum;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.sdk.common.tool.BaseEnumUtil;
import com.machine.service.data.messageTemplate.dao.IAppMessageTemplateDao;
import com.machine.service.data.messageTemplate.dao.mapper.entity.AppMessageTemplateEntity;
import com.machine.service.data.messageTemplate.service.IAppMessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AppMessageTemplateServiceImpl implements IAppMessageTemplateService {

    @Autowired
    private IAppMessageTemplateDao appMessageTemplateDao;

    @Override
    public PageResponse<AppMessageTemplateListOutPutDto> page(AppMessageTemplateQueryPageInputDto inputDto) {
        Page<AppMessageTemplateEntity> messageTemplatePage = appMessageTemplateDao.page(inputDto);
        List<AppMessageTemplateEntity> records = messageTemplatePage.getRecords();
        if (CollectionUtil.isEmpty(records)) {
            return new PageResponse<>(messageTemplatePage.getCurrent(), messageTemplatePage.getSize(), messageTemplatePage.getTotal());
        }
        List<AppMessageTemplateListOutPutDto> outPutDtos = CollectionUtil.newArrayList();
        for (AppMessageTemplateEntity record : records) {
            AppMessageTemplateListOutPutDto outPutDto = JSONUtil.toBean(JSONUtil.toJsonStr(record), AppMessageTemplateListOutPutDto.class);
            AppMessageTemplateInfoDto infoDto = null;
            if(record.getTemplateInfo() != null){
                infoDto = JSONUtil.toBean(JSONUtil.toJsonStr(record.getTemplateInfo()), AppMessageTemplateInfoDto.class);
            }
            outPutDto.setTemplateInfoDto(infoDto);
            if(record.getInformContent() != null){
                outPutDto.setInformContentDto(JSONUtil.toList(record.getInformContent(), AppMessageTemplateDto.class));
            }
            Set<MessageChannelEnum> messageChannelEnumsByStr = getMessageChannelEnumsByStr(record.getChannel());
            outPutDto.setChannels(messageChannelEnumsByStr);
            outPutDtos.add(outPutDto);
        }
        return new PageResponse<>(
                messageTemplatePage.getCurrent(),
                messageTemplatePage.getSize(),
                messageTemplatePage.getTotal(),
                outPutDtos);
    }

    private Set<MessageChannelEnum> getMessageChannelEnumsByStr(String channelStr) {
        if(StringUtils.isEmpty(channelStr)) {
            return new HashSet<>();
        }
        String[] channelCodes = channelStr.split(",");
        Set<MessageChannelEnum> channelEnums = new HashSet<>();
        for (String channelCode : channelCodes) {
            MessageChannelEnum enumByCode = BaseEnumUtil.getEnumByCode(MessageChannelEnum.class, channelCode);
            channelEnums.add(enumByCode);
        }
        return channelEnums;
    }

    @Override
    public AppMessageTemplateDetailOutputDto detailByType(AppMessageTemplateDetailByTypeInputDto request) {
        AppMessageTemplateEntity appMessageTemplateEntity = appMessageTemplateDao.detailByType(request);
        if(appMessageTemplateEntity == null){
            return null;
        }
        AppMessageTemplateDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(appMessageTemplateEntity), AppMessageTemplateDetailOutputDto.class);
        AppMessageTemplateInfoDto infoDto = JSONUtil.toBean(JSONUtil.toJsonStr(appMessageTemplateEntity.getTemplateInfo()), AppMessageTemplateInfoDto.class);
        outputDto.setTemplateInfoDto(infoDto);
        Set<MessageChannelEnum> messageChannelEnumsByStr = getMessageChannelEnumsByStr(appMessageTemplateEntity.getChannel());
        outputDto.setChannels(messageChannelEnumsByStr);
        return outputDto;
    }

    @Override
    public void updateMessageTemplate(AppMessageTemplateUpdateInputDto inputDto) {
        AppMessageTemplateDetailByTypeInputDto detailByTypeInputDto = new AppMessageTemplateDetailByTypeInputDto();
        detailByTypeInputDto.setTemplateType(inputDto.getTemplateType());
        AppMessageTemplateEntity appMessageTemplateEntity = appMessageTemplateDao.detailByType(detailByTypeInputDto);
        if(CollectionUtil.isNotEmpty(inputDto.getChannels())){
            appMessageTemplateEntity.setChannel(String.join(",", inputDto.getChannels()));
        }
        if(inputDto.getTemplateInfoDto() != null){
            appMessageTemplateEntity.setTemplateInfo(JSONUtil.toJsonStr(inputDto.getTemplateInfoDto()));
        }
        if(StringUtils.isNotBlank(inputDto.getInformTitle())){
            appMessageTemplateEntity.setInformTitle(inputDto.getInformTitle());
        }
        if(CollectionUtil.isNotEmpty(inputDto.getInformContentDto())){
            appMessageTemplateEntity.setInformContent(JSONUtil.toJsonStr(inputDto.getInformContentDto()));
        }
        appMessageTemplateEntity.setSmsContent(inputDto.getSmsContent());
        appMessageTemplateDao.update(appMessageTemplateEntity);
    }

    @Override
    public void updateMessageTemplateStatus(AppMessageTemplateUpdateStatusInputDto inputDto) {
        appMessageTemplateDao.updateMessageTemplateStatus(inputDto);
    }

    @Override
    public void updateMessageTemplateChannel(AppMessageTemplateUpdateChannelInput inputDto) {
        appMessageTemplateDao.updateMessageTemplateChannel(inputDto);
    }
}
