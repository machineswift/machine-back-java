package com.machine.service.data.filecenter.attachment.dao;

import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataFileTempEntity;

import java.util.Collection;
import java.util.List;

public interface IDataFileTempDao {

    String insert(DataFileTempEntity entity);

    int deleteById(String id);

    DataFileTempEntity getById(String id);

    List<DataFileTempEntity> selectByIds(Collection<String> ids);

    List<DataFileTempEntity> listTempBeforeTime(Long deadline);

}
