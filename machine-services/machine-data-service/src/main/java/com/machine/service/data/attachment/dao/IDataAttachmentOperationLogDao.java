package com.machine.service.data.attachment.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.attachment.dto.input.DataAttachmentQueryPageInputDto;
import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentOperationLogEntity;

import java.util.List;
import java.util.Set;

public interface IDataAttachmentOperationLogDao {

    String insert(DataAttachmentOperationLogEntity entity);

    int update(DataAttachmentOperationLogEntity entity);

    DataAttachmentOperationLogEntity getById(String id);

    List<DataAttachmentOperationLogEntity> selectByIdSet(Set<String> idSet);

    Page<DataAttachmentOperationLogEntity> selectPage(DataAttachmentQueryPageInputDto inputDto);
}
