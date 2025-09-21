package com.machine.service.data.label.dao;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.data.label.dao.mapper.entity.DataLabelOptionEntity;

import java.util.List;
import java.util.Set;

public interface IDataLabelOptionDao {

    String create(DataLabelOptionEntity entity);

    int deleteById(String id,
                   String labelId);

    int updateStatus(String id,
                     String labelId,
                     StatusEnum status);

    int update(String labelId,
               DataLabelOptionEntity entity);


    DataLabelOptionEntity getById(String id);

    DataLabelOptionEntity getByLabelIdAndName(String labelId,
                                              String name);

    List<DataLabelOptionEntity> listByIdSet(Set<String> idSet);

}
