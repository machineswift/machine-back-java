package com.machine.service.data.mesage.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.message.dto.input.AppMessageTemplateDetailByTypeInputDto;
import com.machine.client.data.message.dto.input.AppMessageTemplateQueryPageInputDto;
import com.machine.client.data.message.dto.input.AppMessageTemplateUpdateChannelInput;
import com.machine.client.data.message.dto.input.AppMessageTemplateUpdateStatusInputDto;
import com.machine.sdk.common.envm.data.message.DataMessageChannelEnum;
import com.machine.service.data.mesage.dao.IDataAppMessageTemplateDao;
import com.machine.service.data.mesage.dao.mapper.DataAppMessageTemplateMapper;
import com.machine.service.data.mesage.dao.mapper.entity.DataAppMessageTemplateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class DataAppMessageTemplateDaoImpl implements IDataAppMessageTemplateDao {

    @Autowired
    private DataAppMessageTemplateMapper dataAppMessageTemplateMapper;

    @Override
    public Page<DataAppMessageTemplateEntity> page(AppMessageTemplateQueryPageInputDto inputDto) {
        IPage<DataAppMessageTemplateEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return dataAppMessageTemplateMapper.page(inputDto, page);
 }

    @Override
    public DataAppMessageTemplateEntity detailByType(AppMessageTemplateDetailByTypeInputDto inputDto) {
        Wrapper<DataAppMessageTemplateEntity> queryWrapper = new LambdaQueryWrapper<DataAppMessageTemplateEntity>()
                .eq(DataAppMessageTemplateEntity::getTemplateType, inputDto.getTemplateType().getCode());
        return dataAppMessageTemplateMapper.selectOne(queryWrapper);
    }

    @Override
    public void update(DataAppMessageTemplateEntity dataAppMessageTemplateEntity) {
        dataAppMessageTemplateMapper.updateById(dataAppMessageTemplateEntity);
    }

    @Override
    public void updateMessageTemplateStatus(AppMessageTemplateUpdateStatusInputDto inputDto) {
        LambdaUpdateWrapper<DataAppMessageTemplateEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(DataAppMessageTemplateEntity::getId, inputDto.getTemplateIdSet());
        updateWrapper.set(DataAppMessageTemplateEntity::getStatus, inputDto.getStatusEnum().getCode());
        dataAppMessageTemplateMapper.update(null, updateWrapper);
    }

    @Override
    public void updateMessageTemplateChannel(AppMessageTemplateUpdateChannelInput inputDto) {
        Set<DataMessageChannelEnum> channelEnumSet = inputDto.getChannelEnumSet();
        Set<String> channelSet = channelEnumSet.stream().map(DataMessageChannelEnum::getName).collect(Collectors.toSet());
        String channelStr = String.join(",", channelSet);
        LambdaUpdateWrapper<DataAppMessageTemplateEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(DataAppMessageTemplateEntity::getId, inputDto.getTemplateIdSet());
        updateWrapper.set(DataAppMessageTemplateEntity::getChannel, channelStr);
        dataAppMessageTemplateMapper.update(null, updateWrapper);
    }
}
