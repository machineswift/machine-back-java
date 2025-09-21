package com.machine.service.data.external.service;

import com.machine.client.data.external.dto.input.DataExternalFieldDataCreateInputDto;
import com.machine.client.data.external.dto.input.DataExternalFieldDataDeleteInputDto;
import com.machine.client.data.external.dto.input.DataExternalFieldDataGetValueInputDto;

public interface IDataExternalFieldDataService {
    String create(DataExternalFieldDataCreateInputDto inputDto);

    int delete(DataExternalFieldDataDeleteInputDto inputDto);

    String getValue(DataExternalFieldDataGetValueInputDto inputDto);
}
