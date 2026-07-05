package com.machine.service.data.filecenter.attachment.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogPageInputDto;
import com.machine.service.data.filecenter.attachment.dao.IDataAttachmentOperationLogDao;
import com.machine.service.data.filecenter.attachment.dao.mapper.DataAttachmentOperationLogMapper;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataAttachmentOperationLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataAttachmentOperationLogDaoImpl implements IDataAttachmentOperationLogDao {

    @Autowired
    private DataAttachmentOperationLogMapper attachmentOperationLogMapper;

    @Override
    public String insert(DataAttachmentOperationLogEntity entity) {
        attachmentOperationLogMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(DataAttachmentOperationLogEntity entity) {
        return attachmentOperationLogMapper.updateById(entity);
    }

    @Override
    public DataAttachmentOperationLogEntity getById(String id) {
        return attachmentOperationLogMapper.selectById(id);
    }

    @Override
    public List<DataAttachmentOperationLogEntity> selectByIdSet(Set<String> idSet) {
        return attachmentOperationLogMapper.selectByIds(idSet);
    }

    @Override
    public Page<DataAttachmentOperationLogEntity> selectPage(DataAttachmentOperationLogPageInputDto inputDto) {
        IPage<DataAttachmentOperationLogEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return attachmentOperationLogMapper.selectPage(inputDto, page);
    }
}
