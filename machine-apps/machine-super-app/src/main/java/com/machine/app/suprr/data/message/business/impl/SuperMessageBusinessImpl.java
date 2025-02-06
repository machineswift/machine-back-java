package com.machine.app.suprr.data.message.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.suprr.data.message.business.ISuperMessageBusiness;
import com.machine.app.suprr.data.message.controller.vo.request.*;
import com.machine.app.suprr.data.message.controller.vo.response.SuperAppMessageGroupCountResponseVo;
import com.machine.app.suprr.data.message.controller.vo.response.SuperAppMessageListResponseVo;
import com.machine.client.data.config.IDataSystemConfigClient;
import com.machine.client.data.config.dto.DataSystemConfigCustomCategoryDto;
import com.machine.client.data.config.dto.SystemConfigDto;
import com.machine.client.data.informaion.IDataAppMessageClient;
import com.machine.client.data.informaion.input.AppMessageGroupCountInputDto;
import com.machine.client.data.informaion.input.AppMessagePageSuperInputDto;
import com.machine.client.data.informaion.input.AppMessageReadInputDto;
import com.machine.client.data.informaion.input.AppMessageUnreadCountInputDto;
import com.machine.client.data.informaion.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.informaion.output.AppMessageListSuperOutputDto;
import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.data.MessageChannelEnum;
import com.machine.sdk.common.envm.data.MessageTemplateTypeEnum;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.machine.client.data.config.constant.DataSystemConfigCategoryConstant.CATEGORY_MESSAGE;
import static com.machine.client.data.config.constant.DataSystemConfigCategoryConstant.CODE_MESSAGE_CUSTOM_CATEGORY;

@Slf4j
@Component
public class SuperMessageBusinessImpl implements ISuperMessageBusiness {

    @Autowired
    private IDataAppMessageClient appMessageClient;

    @Autowired
    private IDataSystemConfigClient systemConfigClient;

    @Override
    public PageResponse<SuperAppMessageListResponseVo> page(SuperMessagePageRequestVo request) {
        AppMessagePageSuperInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessagePageSuperInputDto.class);
        PageResponse<AppMessageListSuperOutputDto> page = appMessageClient.superPage(inputDto);
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), SuperAppMessageListResponseVo.class));
    }

    @Override
    public Boolean read(SuperReadMessageRequestVo request) {
        AppMessageReadInputDto messageReadInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageReadInputDto.class);
        return appMessageClient.readMessage(messageReadInputDto);
    }

    @Override
    public List<SuperAppMessageGroupCountResponseVo> groupCount(SuperAppMessageGroupCountRequestVo request) {
        AppMessageGroupCountInputDto countInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageGroupCountInputDto.class);
        List<AppMessageGroupCountOutputDto> list = appMessageClient.groupCount(countInputDto);
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return BeanUtil.copyToList(list, SuperAppMessageGroupCountResponseVo.class);
    }

    @Override
    public Integer getUnreadCount(SuperMessageUnreadCountRequestVo request) {
        AppMessageUnreadCountInputDto countInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageUnreadCountInputDto.class);
        return appMessageClient.getUnreadCount(countInputDto);
    }

    private Set<MessageTemplateTypeEnum> getTemplateTypesByCustomCategory(BaseEnum baseEnum){
        SystemConfigDto systemConfigDto = new SystemConfigDto();
        systemConfigDto.setCategory(CATEGORY_MESSAGE);
        systemConfigDto.setCode(CODE_MESSAGE_CUSTOM_CATEGORY);
        String customCategoryString = systemConfigClient.getString(systemConfigDto);
        if(StrUtil.isBlank(customCategoryString)) {
            return new HashSet<>();
        }
        List<DataSystemConfigCustomCategoryDto> list = JSONUtil.toList(customCategoryString, DataSystemConfigCustomCategoryDto.class);
        for (DataSystemConfigCustomCategoryDto dataSystemConfigCustomCategoryDto : list) {
            if(baseEnum.getName().equals(dataSystemConfigCustomCategoryDto.getBaseEnumCategory())){
                return dataSystemConfigCustomCategoryDto.getTemplateTypeEnums();
            }
        }
        return new HashSet<>();
    }

    @Override
    public PageResponse<SuperAppMessageListResponseVo> customCategoryPage(MessageChannelEnum channelEnum, BaseEnum baseEnum, Long current, Long size) {
        Set<MessageTemplateTypeEnum> typeEnums = getTemplateTypesByCustomCategory(baseEnum);
        if(CollectionUtil.isEmpty(typeEnums)) {
            return new PageResponse<>(current, size, 0);
        }
        AppMessagePageSuperInputDto inputDto = new AppMessagePageSuperInputDto();
        inputDto.setTemplateTypeEnums(typeEnums);
        inputDto.setCurrent(current);
        inputDto.setSize(size);
        inputDto.setDispose(0);
        inputDto.setChannel(channelEnum);
        PageResponse<AppMessageListSuperOutputDto> page = appMessageClient.superPage(inputDto);
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), SuperAppMessageListResponseVo.class));
    }

}
