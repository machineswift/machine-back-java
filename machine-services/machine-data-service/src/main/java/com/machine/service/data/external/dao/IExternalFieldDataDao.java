package com.machine.service.data.external.dao;

import com.machine.client.data.external.dto.input.ExternalFieldDataGetValueInputDto;
import com.machine.service.data.external.dao.mapper.entity.ExternalFieldDataEntity;

public interface IExternalFieldDataDao {
    String insert(ExternalFieldDataEntity entity);

    int delete(ExternalFieldDataEntity entity);

    String getValue(ExternalFieldDataGetValueInputDto inputDto);
}
