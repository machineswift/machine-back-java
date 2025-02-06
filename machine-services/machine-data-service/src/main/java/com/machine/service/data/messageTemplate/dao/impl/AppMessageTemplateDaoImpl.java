package com.machine.service.data.messageTemplate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.messageTemplate.dto.input.AppMessageTemplateDetailByTypeInputDto;
import com.machine.client.data.messageTemplate.dto.input.AppMessageTemplateQueryPageInputDto;
import com.machine.client.data.messageTemplate.dto.input.AppMessageTemplateUpdateChannelInput;
import com.machine.client.data.messageTemplate.dto.input.AppMessageTemplateUpdateStatusInputDto;
import com.machine.sdk.common.envm.data.MessageChannelEnum;
import com.machine.service.data.messageTemplate.dao.IAppMessageTemplateDao;
import com.machine.service.data.messageTemplate.dao.mapper.AppMessageTemplateMapper;
import com.machine.service.data.messageTemplate.dao.mapper.entity.AppMessageTemplateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class AppMessageTemplateDaoImpl implements IAppMessageTemplateDao {

    @Autowired
    private AppMessageTemplateMapper appMessageTemplateMapper;

    @Override
    public Page<AppMessageTemplateEntity> page(AppMessageTemplateQueryPageInputDto inputDto) {
        IPage<AppMessageTemplateEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return appMessageTemplateMapper.page(inputDto, page);
 }

    @Override
    public AppMessageTemplateEntity detailByType(AppMessageTemplateDetailByTypeInputDto inputDto) {
        Wrapper<AppMessageTemplateEntity> queryWrapper = new LambdaQueryWrapper<AppMessageTemplateEntity>()
                .eq(AppMessageTemplateEntity::getTemplateType, inputDto.getTemplateType().getCode());
        return appMessageTemplateMapper.selectOne(queryWrapper);
    }

    @Override
    public void update(AppMessageTemplateEntity appMessageTemplateEntity) {
        appMessageTemplateMapper.updateById(appMessageTemplateEntity);
    }

    @Override
    public void updateMessageTemplateStatus(AppMessageTemplateUpdateStatusInputDto inputDto) {
        LambdaUpdateWrapper<AppMessageTemplateEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(AppMessageTemplateEntity::getId, inputDto.getTemplateIdSet());
        updateWrapper.set(AppMessageTemplateEntity::getStatus, inputDto.getStatusEnum().getCode());
        appMessageTemplateMapper.update(null, updateWrapper);
    }

    @Override
    public void updateMessageTemplateChannel(AppMessageTemplateUpdateChannelInput inputDto) {
        Set<MessageChannelEnum> channelEnumSet = inputDto.getChannelEnumSet();
        Set<String> channelSet = channelEnumSet.stream().map(MessageChannelEnum::getName).collect(Collectors.toSet());
        String channelStr = String.join(",", channelSet);
        LambdaUpdateWrapper<AppMessageTemplateEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(AppMessageTemplateEntity::getId, inputDto.getTemplateIdSet());
        updateWrapper.set(AppMessageTemplateEntity::getChannel, channelStr);
        appMessageTemplateMapper.update(null, updateWrapper);
    }
}
