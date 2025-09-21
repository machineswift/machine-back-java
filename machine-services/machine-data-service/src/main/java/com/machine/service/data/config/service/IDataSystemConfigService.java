package com.machine.service.data.config.service;

import com.machine.client.data.config.dto.DataSystemConfigDto;

public interface IDataSystemConfigService {

    String create(DataSystemConfigDto dto);

    int delete(DataSystemConfigDto dto);

    int update(DataSystemConfigDto dto);

    DataSystemConfigDto getByUk(DataSystemConfigDto dto);

}
