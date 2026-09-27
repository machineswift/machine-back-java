package com.machine.service.data.filecenter.attachment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogQueryPageInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentOperationLogDetailOutputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentOperationLogListOutputDto;
import com.machine.service.data.filecenter.attachment.dao.IDataAttachmentOperationLogDao;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataAttachmentOperationLogEntity;
import com.machine.service.data.filecenter.attachment.service.IDataAttachmentOperationLogService;
import com.machine.sdk.base.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DataAttachmentOperationLogServiceImpl implements IDataAttachmentOperationLogService {

    @Autowired
    private IDataAttachmentOperationLogDao attachmentOperationLogDao;

    @Override
    public void create(DataAttachmentOperationLogCreateInputDto inputDto) {
        DataAttachmentOperationLogEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataAttachmentOperationLogEntity.class);
        attachmentOperationLogDao.insert(entity);
    }

    @Override
    public DataAttachmentOperationLogDetailOutputDto detail(IdRequest request) {
        DataAttachmentOperationLogEntity entity = attachmentOperationLogDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataAttachmentOperationLogDetailOutputDto.class);
    }

    @Override
    public Page<DataAttachmentOperationLogListOutputDto> selectPage(DataAttachmentOperationLogQueryPageInputDto inputDto) {
        Page<DataAttachmentOperationLogEntity> entityPage = attachmentOperationLogDao.selectPage(inputDto);

        Page<DataAttachmentOperationLogListOutputDto> pageResult =
                new Page<>(entityPage.getCurrent(), entityPage.getSize(), entityPage.getTotal());

        if (CollectionUtil.isEmpty(entityPage.getRecords())) {
            return pageResult;
        }

        List<DataAttachmentOperationLogListOutputDto> outputDtoList =
                JSONUtil.toList(JSONUtil.toJsonStr(entityPage.getRecords()), DataAttachmentOperationLogListOutputDto.class);
        pageResult.setRecords(outputDtoList);
        return pageResult;
    }
}
