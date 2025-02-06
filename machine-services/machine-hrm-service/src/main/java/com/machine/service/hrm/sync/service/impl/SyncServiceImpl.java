package com.machine.service.hrm.sync.service.impl;

import com.machine.client.data.config.IDataHrmConfigClient;
import com.machine.service.hrm.sync.service.ISyncService;
import com.machine.service.hrm.sync.service.bo.HrmSyncDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SyncServiceImpl implements ISyncService {

    @Autowired
    private IDataHrmConfigClient hrmConfigClient;

    @Override
    public void saveLastSyncTime(String code,
                                 long enSyncTime) {
        //保存上次执行时间
        hrmConfigClient.saveBeiSenLastSyncTime(code, enSyncTime);
    }

    @Override
    public HrmSyncDto getHrmSyncDto(String code) {
        Long lastSyncTime = hrmConfigClient.beiSenLastSyncTime(code);

        //提前60秒，方式这边和北森有时间误差
        long enSyncTime = System.currentTimeMillis() - 60 * 1000;
        return new HrmSyncDto(lastSyncTime, enSyncTime);
    }
}
