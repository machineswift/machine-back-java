package com.machine.service.data.config.server;

import com.machine.client.data.config.IDataHrmConfigClient;
import com.machine.client.data.config.dto.SystemConfigDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Slf4j
@RestController
@RequestMapping("server/data/hrm_config")
public class DataHrmConfigServer implements IDataHrmConfigClient {

    public static final String CATEGORY = "HRM";

    /**
     * 北森
     */
    public static final String CATEGORY_BEI_SEN = "BEI_SEN";

    @Autowired
    private DataSystemConfigServer systemConfigServer;

    @Override
    @GetMapping("save_bei_sen_lastSyncTime")
    public void saveBeiSenLastSyncTime(@RequestParam(name = "code") String code,
                                       @RequestParam(name = "enSyncTime") long enSyncTime) {
        //保存上次执行时间
        SystemConfigDto systemConfigDto = new SystemConfigDto(CATEGORY_BEI_SEN, code);
        systemConfigDto.setContent(enSyncTime + "");
        systemConfigServer.create(systemConfigDto);
    }

    @Override
    @GetMapping("bei_sen_lastSyncTime")
    public Long beiSenLastSyncTime(@RequestParam(name = "code") String code) {
        Long lastSyncTime = systemConfigServer.getLong(new SystemConfigDto(CATEGORY_BEI_SEN, code));
        if (null == lastSyncTime) {
            //为空的话取 2020-01-01 对应的时间（保证获取懂啊所有的数据）
            LocalDateTime localTime = LocalDateTime.of(2020, 1, 1, 0, 0);
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localTime, ZoneOffset.UTC);
            lastSyncTime = zonedDateTime.toInstant().toEpochMilli();
        }
        return lastSyncTime;
    }
}
