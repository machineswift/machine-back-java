package com.machine.service.data.mesage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.message.dto.input.AppMessageTemplateDto;
import com.machine.client.data.message.dto.input.*;
import com.machine.client.data.message.dto.output.AppMessageTemplateDetailOutputDto;
import com.machine.client.data.message.dto.output.AppMessageTemplateInfoDto;
import com.machine.client.data.message.dto.output.AppMessageTemplateListOutPutDto;
import com.machine.sdk.common.envm.data.message.DataMessageChannelEnum;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.sdk.common.tool.BaseEnumUtil;
import com.machine.service.data.mesage.dao.IDataAppMessageTemplateDao;
import com.machine.service.data.mesage.dao.mapper.entity.DataAppMessageTemplateEntity;
import com.machine.service.data.mesage.service.IDataAppMessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class DataAppMessageTemplateServiceImpl implements IDataAppMessageTemplateService {

    @Autowired
    private IDataAppMessageTemplateDao appMessageTemplateDao;

    @Override
    public PageResponse<AppMessageTemplateListOutPutDto> page(AppMessageTemplateQueryPageInputDto inputDto) {
        Page<DataAppMessageTemplateEntity> messageTemplatePage = appMessageTemplateDao.page(inputDto);
        List<DataAppMessageTemplateEntity> records = messageTemplatePage.getRecords();
        if (CollectionUtil.isEmpty(records)) {
            return new PageResponse<>(messageTemplatePage.getCurrent(), messageTemplatePage.getSize(), messageTemplatePage.getTotal());
        }
        List<AppMessageTemplateListOutPutDto> outPutDtos = CollectionUtil.newArrayList();
        for (DataAppMessageTemplateEntity record : records) {
            AppMessageTemplateListOutPutDto outPutDto = JSONUtil.toBean(JSONUtil.toJsonStr(record), AppMessageTemplateListOutPutDto.class);
            AppMessageTemplateInfoDto infoDto = null;
            if(record.getTemplateInfo() != null){
                infoDto = JSONUtil.toBean(JSONUtil.toJsonStr(record.getTemplateInfo()), AppMessageTemplateInfoDto.class);
            }
            outPutDto.setTemplateInfoDto(infoDto);
            if(record.getInformContent() != null){
                outPutDto.setInformContentDto(JSONUtil.toList(record.getInformContent(), AppMessageTemplateDto.class));
            }
            Set<DataMessageChannelEnum> messageChannelEnumsByStr = getMessageChannelEnumsByStr(record.getChannel());
            outPutDto.setChannels(messageChannelEnumsByStr);
            outPutDtos.add(outPutDto);
        }
        return new PageResponse<>(
                messageTemplatePage.getCurrent(),
                messageTemplatePage.getSize(),
                messageTemplatePage.getTotal(),
                outPutDtos);
    }

    private Set<DataMessageChannelEnum> getMessageChannelEnumsByStr(String channelStr) {
        if(StringUtils.isEmpty(channelStr)) {
            return new HashSet<>();
        }
        String[] channelCodes = channelStr.split(",");
        Set<DataMessageChannelEnum> channelEnums = new HashSet<>();
        for (String channelCode : channelCodes) {
            DataMessageChannelEnum enumByCode = BaseEnumUtil.getEnumByCode(DataMessageChannelEnum.class, channelCode);
            channelEnums.add(enumByCode);
        }
        return channelEnums;
    }

    @Override
    public AppMessageTemplateDetailOutputDto detailByType(AppMessageTemplateDetailByTypeInputDto request) {
        DataAppMessageTemplateEntity dataAppMessageTemplateEntity = appMessageTemplateDao.detailByType(request);
        if(dataAppMessageTemplateEntity == null){
            return null;
        }
        AppMessageTemplateDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(dataAppMessageTemplateEntity), AppMessageTemplateDetailOutputDto.class);
        AppMessageTemplateInfoDto infoDto = JSONUtil.toBean(JSONUtil.toJsonStr(dataAppMessageTemplateEntity.getTemplateInfo()), AppMessageTemplateInfoDto.class);
        outputDto.setTemplateInfoDto(infoDto);
        Set<DataMessageChannelEnum> messageChannelEnumsByStr = getMessageChannelEnumsByStr(dataAppMessageTemplateEntity.getChannel());
        outputDto.setChannels(messageChannelEnumsByStr);
        return outputDto;
    }

    @Override
    public void updateMessageTemplate(AppMessageTemplateUpdateInputDto inputDto) {
        AppMessageTemplateDetailByTypeInputDto detailByTypeInputDto = new AppMessageTemplateDetailByTypeInputDto();
        detailByTypeInputDto.setTemplateType(inputDto.getTemplateType());
        DataAppMessageTemplateEntity dataAppMessageTemplateEntity = appMessageTemplateDao.detailByType(detailByTypeInputDto);
        if(CollectionUtil.isNotEmpty(inputDto.getChannels())){
            dataAppMessageTemplateEntity.setChannel(String.join(",", inputDto.getChannels()));
        }
        if(inputDto.getTemplateInfoDto() != null){
            dataAppMessageTemplateEntity.setTemplateInfo(JSONUtil.toJsonStr(inputDto.getTemplateInfoDto()));
        }
        if(StringUtils.isNotBlank(inputDto.getInformTitle())){
            dataAppMessageTemplateEntity.setInformTitle(inputDto.getInformTitle());
        }
        if(CollectionUtil.isNotEmpty(inputDto.getInformContentDto())){
            dataAppMessageTemplateEntity.setInformContent(JSONUtil.toJsonStr(inputDto.getInformContentDto()));
        }
        dataAppMessageTemplateEntity.setSmsContent(inputDto.getSmsContent());
        appMessageTemplateDao.update(dataAppMessageTemplateEntity);
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
