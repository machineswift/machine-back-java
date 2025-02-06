package com.machine.service.data.external.service;

import com.machine.client.data.external.dto.input.ExternalFieldDataCreateInputDto;
import com.machine.client.data.external.dto.input.ExternalFieldDataDeleteInputDto;
import com.machine.client.data.external.dto.input.ExternalFieldDataGetValueInputDto;

public interface IExternalFieldDataService {
    String create(ExternalFieldDataCreateInputDto inputDto);

    int delete(ExternalFieldDataDeleteInputDto inputDto);

    String getValue(ExternalFieldDataGetValueInputDto inputDto);
}
