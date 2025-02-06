package com.machine.service.data.config.service;

import com.machine.client.data.config.dto.SystemConfigDto;

public interface ISystemConfigService {

    String create(SystemConfigDto dto);

    int delete(SystemConfigDto dto);

    int update(SystemConfigDto dto);

    SystemConfigDto getByUk(SystemConfigDto dto);

}
