package com.machine.service.data.attachment.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.attachment.dto.input.DataAttachmentQueryPageInputDto;
import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentEntity;

import java.util.List;
import java.util.Set;

public interface IDataAttachmentDao {

    String insert(DataAttachmentEntity entity);

    int update(DataAttachmentEntity entity);

    DataAttachmentEntity getById(String id);

    List<DataAttachmentEntity> selectByIdSet(Set<String> idSet);

    Page<DataAttachmentEntity> selectPage(DataAttachmentQueryPageInputDto inputDto);
}
