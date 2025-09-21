package com.machine.service.data.external.dao;

import com.machine.client.data.external.dto.input.DataExternalFieldDataGetValueInputDto;
import com.machine.service.data.external.dao.mapper.entity.DataExternalFieldDataEntity;

public interface IDataExternalFieldDataDao {
    String insert(DataExternalFieldDataEntity entity);

    int delete(DataExternalFieldDataEntity entity);

    String getValue(DataExternalFieldDataGetValueInputDto inputDto);
}
