package com.machine.service.data.mesage.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.message.dto.input.AppMessageGroupCountInputDto;
import com.machine.client.data.message.dto.input.AppMessagePageInputDto;
import com.machine.client.data.message.dto.input.AppMessagePageSuperInputDto;
import com.machine.client.data.message.dto.input.AppMessageUnreadCountInputDto;
import com.machine.client.data.message.dto.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListSuperOutputDto;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.service.data.mesage.dao.IDataAppMessageDao;
import com.machine.service.data.mesage.dao.mapper.DataAppMessageMapper;
import com.machine.service.data.mesage.dao.mapper.entity.DataAppMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataAppMessageDaoImpl implements IDataAppMessageDao {

    @Autowired
    private DataAppMessageMapper dataAppMessageMapper;

    @Override
    public Page<AppMessageListOutputDto> managePage(AppMessagePageInputDto inputDto) {
        IPage<AppMessageListOutputDto> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return dataAppMessageMapper.managePage(inputDto, page);
    }

    @Override
    public Page<AppMessageListSuperOutputDto> superPage(AppMessagePageSuperInputDto inputDto) {
        if(StringUtils.isBlank(inputDto.getReceiver())){
            throw new IamBusinessException("client.data.dao.message.groupCount", "当前用户id不能为空");
        }
        IPage<AppMessageListSuperOutputDto> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return dataAppMessageMapper.superPage(inputDto, page);
    }

    @Override
    public void insert(DataAppMessageEntity dataAppMessageEntity) {
        dataAppMessageMapper.insert(dataAppMessageEntity);
    }

    @Override
    public void disposeMessage(String messageId) {
        DataAppMessageEntity messageEntity = dataAppMessageMapper.selectById(messageId);
        dataAppMessageMapper.disposeMessage(messageEntity.getBatchCode(), messageEntity.getReceiver());
    }

    @Override
    public void readMessage(String messageId) {
        DataAppMessageEntity messageEntity = dataAppMessageMapper.selectById(messageId);
        dataAppMessageMapper.readMessage(messageEntity.getBatchCode(), messageEntity.getReceiver());
    }

    @Override
    public List<AppMessageGroupCountOutputDto> groupCount(AppMessageGroupCountInputDto inputDto) {
        if(StringUtils.isBlank(inputDto.getReceiver())){
            throw new IamBusinessException("client.data.message.groupCount", "当前用户id不能为空");
        }
        return dataAppMessageMapper.groupCount(inputDto);
    }

    @Override
    public Integer getUnreadCount(AppMessageUnreadCountInputDto inputDto) {
        if(StringUtils.isBlank(inputDto.getReceiver())){
            throw new IamBusinessException("client.data.message.getUnreadCount", "当前用户id不能为空");
        }
        return dataAppMessageMapper.getUnreadCount(inputDto);
    }
}
