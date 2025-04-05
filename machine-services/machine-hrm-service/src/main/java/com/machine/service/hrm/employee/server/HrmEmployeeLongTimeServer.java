package com.machine.service.hrm.employee.server;

import com.machine.client.hrm.employee.IHrmEmployeeLongTimeClient;
import com.machine.sdk.common.context.AppContext;
import com.machine.service.hrm.employee.service.IHrmEmployeeService;
import com.machine.service.hrm.sync.service.bo.HrmSyncDto;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.machine.client.data.config.constant.DataSystemConfigCodeConstant.BeiSen.CODE_EMPLOYEE_SYNC_TIME;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Hrm.HRM_SYNC_BEI_SEN_EMPLOYEE_LOCK;

@Slf4j
@RestController
@RequestMapping("server/hrm/employee_long_time")
public class HrmEmployeeLongTimeServer implements IHrmEmployeeLongTimeClient {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ISyncService syncService;

    @Autowired
    private IHrmEmployeeService employeeService;

    @Override
    @GetMapping("sync")
    public void sync() {
        RLock lock = redissonClient.getLock(HRM_SYNC_BEI_SEN_EMPLOYEE_LOCK);
        try {
            lock.lock();

            log.info("同步员工信息开始，userId={}", AppContext.getContext().getUserId());
            //获取上次同步时间
            HrmSyncDto syncDto = syncService.getHrmSyncDto(CODE_EMPLOYEE_SYNC_TIME);
            //同步数据
            employeeService.sync(syncDto.getLastSyncTime(), syncDto.getEnSyncTime());
            //保存本次执行时间
            syncService.saveLastSyncTime(CODE_EMPLOYEE_SYNC_TIME, syncDto.getEnSyncTime());
            log.info("同步员工信息结束");
        } finally {
            lock.unlock();
        }
    }

}
