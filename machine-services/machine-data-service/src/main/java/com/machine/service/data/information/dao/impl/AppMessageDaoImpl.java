package com.machine.service.data.information.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.informaion.input.AppMessageGroupCountInputDto;
import com.machine.client.data.informaion.input.AppMessagePageInputDto;
import com.machine.client.data.informaion.input.AppMessagePageSuperInputDto;
import com.machine.client.data.informaion.input.AppMessageUnreadCountInputDto;
import com.machine.client.data.informaion.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.informaion.output.AppMessageListOutputDto;
import com.machine.client.data.informaion.output.AppMessageListSuperOutputDto;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.information.dao.IAppMessageDao;
import com.machine.service.data.information.dao.mapper.AppMessageMapper;
import com.machine.service.data.information.dao.mapper.entity.AppMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppMessageDaoImpl implements IAppMessageDao {

    @Autowired
    private AppMessageMapper appMessageMapper;

    @Override
    public Page<AppMessageListOutputDto> managePage(AppMessagePageInputDto inputDto) {
        IPage<AppMessageListOutputDto> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return appMessageMapper.managePage(inputDto, page);
    }

    @Override
    public Page<AppMessageListSuperOutputDto> superPage(AppMessagePageSuperInputDto inputDto) {
        if(StringUtils.isBlank(inputDto.getReceiver())){
            throw new IamBusinessException("client.data.message.groupCount", "当前用户id不能为空");
        }
        IPage<AppMessageListSuperOutputDto> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return appMessageMapper.superPage(inputDto, page);
    }

    @Override
    public void insert(AppMessageEntity appMessageEntity) {
        appMessageMapper.insert(appMessageEntity);
    }

    @Override
    public void disposeMessage(String messageId) {
        AppMessageEntity messageEntity = appMessageMapper.selectById(messageId);
        appMessageMapper.disposeMessage(messageEntity.getBatchCode(), messageEntity.getReceiver());
    }

    @Override
    public void readMessage(String messageId) {
        AppMessageEntity messageEntity = appMessageMapper.selectById(messageId);
        appMessageMapper.readMessage(messageEntity.getBatchCode(), messageEntity.getReceiver());
    }

    @Override
    public List<AppMessageGroupCountOutputDto> groupCount(AppMessageGroupCountInputDto inputDto) {
        if(StringUtils.isBlank(inputDto.getReceiver())){
            throw new IamBusinessException("client.data.message.groupCount", "当前用户id不能为空");
        }
        return appMessageMapper.groupCount(inputDto);
    }

    @Override
    public Integer getUnreadCount(AppMessageUnreadCountInputDto inputDto) {
        if(StringUtils.isBlank(inputDto.getReceiver())){
            throw new IamBusinessException("client.data.message.getUnreadCount", "当前用户id不能为空");
        }
        return appMessageMapper.getUnreadCount(inputDto);
    }
}
