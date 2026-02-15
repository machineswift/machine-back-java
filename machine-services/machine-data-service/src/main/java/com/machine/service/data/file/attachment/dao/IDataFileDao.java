package com.machine.service.data.file.attachment.dao;

import com.machine.service.data.file.attachment.dao.mapper.entity.DataFileEntity;

import java.util.List;
import java.util.Set;

public interface IDataFileDao {

    String insert(DataFileEntity entity);

    int update(DataFileEntity entity);

    DataFileEntity getById(String id);

    List<DataFileEntity> selectByIdSet(Set<String> idSet);
}
