package com.machine.client.data.config;

import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/hrm_config",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataHrmConfigClient {

    @GetMapping("save_bei_sen_lastSyncTime")
    void saveBeiSenLastSyncTime(@RequestParam(name = "code") String code,
                                @RequestParam(name = "enSyncTime") long enSyncTime);

    @GetMapping("bei_sen_lastSyncTime")
    Long beiSenLastSyncTime(@RequestParam(name = "code") String code);

}
