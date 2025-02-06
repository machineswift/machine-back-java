package com.machine.service.hrm.sync.service;

import com.machine.service.hrm.sync.service.bo.HrmSyncDto;

public interface ISyncService {

    void saveLastSyncTime(String code,
                          long enSyncTime);

    HrmSyncDto getHrmSyncDto(String code);
}
