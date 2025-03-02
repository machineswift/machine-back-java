package com.machine.service.hrm.department.server;

import com.machine.client.hrm.department.IHrmDepartmentClient;
import com.machine.client.hrm.department.dto.output.DepartmentDetailOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentExpansionListOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentListOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentTreeOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.hrm.department.service.IDepartmentService;
import com.machine.service.hrm.sync.service.ISyncService;
import com.machine.service.hrm.sync.service.bo.HrmSyncDto;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.machine.client.data.config.constant.DataSystemConfigCodeConstant.BeiSen.CODE_DEPARTMENT_SYNC_TIME;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Hrm.HRM_SYNC_BEI_SEN_DEPARTMENT_LOCK;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Hrm.HRM_SYNC_BEI_SEN_DEPARTMENT_PERSON_IN_CHARGE_LOCK;

@Slf4j
@RestController
@RequestMapping("server/hrm/department")
public class HrmDepartmentServer implements IHrmDepartmentClient {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ISyncService syncService;

    @Autowired
    private IDepartmentService departmentService;

    @Override
    @GetMapping("sync")
    public void sync() {
        RLock lock = redissonClient.getLock(HRM_SYNC_BEI_SEN_DEPARTMENT_LOCK);
        try {
            lock.lock();

            log.info("同步部门信息开始，userId={}", AppContext.getContext().getUserId());
            //获取上次同步时间
            HrmSyncDto syncDto = syncService.getHrmSyncDto(CODE_DEPARTMENT_SYNC_TIME);
            //同步数据
            departmentService.sync(syncDto.getLastSyncTime(), syncDto.getEnSyncTime());
            //保存本次执行时间
            syncService.saveLastSyncTime(CODE_DEPARTMENT_SYNC_TIME, syncDto.getEnSyncTime());
            log.info("同步部门信息结束");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void syncDepartmentPersonInCharge() {
        RLock lock = redissonClient.getLock(HRM_SYNC_BEI_SEN_DEPARTMENT_PERSON_IN_CHARGE_LOCK);
        try {
            lock.lock();

            log.info("同步部门信息负责人开始，userId={}", AppContext.getContext().getUserId());

            //同步数据
            departmentService.syncDepartmentPersonInCharge();

            log.info("同步部门信息负责人结束");
        } finally {
            lock.unlock();
        }
    }

    @Override
    @GetMapping("clear_cache")
    public void clearCache() {
        log.info("清理区部门缓存");
        departmentService.clearCache();
    }

    @Override
    @PostMapping("detail_by_id")
    public DepartmentDetailOutputDto detailById(@RequestBody @Validated IdRequest request) {
        return departmentService.detailById(request);
    }

    @Override
    @GetMapping("list_all")
    public List<DepartmentListOutputDto> listAll() {
        return departmentService.listAll();
    }

    @Override
    @GetMapping("tree_all_simple")
    public DepartmentTreeOutputDto treeAllSimple() {
        return departmentService.treeAllSimple();
    }

    @Override
    public Map<String, DepartmentExpansionListOutputDto> mapDepartmentExpansionByDepartmentIdSet(IdSetRequest idSetRequest) {
        return departmentService.mapDepartmentExpansionByDepartmentIdSet(idSetRequest);
    }
}
