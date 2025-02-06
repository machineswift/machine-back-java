package com.machine.service.hrm.jobpost.server;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.hrm.jobpost.IHrmJobPostClient;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPostListSimpleInputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostDetailOutputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostListSimpleOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.hrm.jobpost.service.IJobPostService;
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

import static com.machine.client.data.config.constant.DataSystemConfigCodeConstant.BeiSen.CODE_JOB_POST_SYNC_TIME;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Hrm.HRM_SYNC_BEI_SEN_JOB_POST_LOCK;

@Slf4j
@RestController
@RequestMapping("server/hrm/job_post")
public class HrmJobPostServer implements IHrmJobPostClient {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ISyncService syncService;

    @Autowired
    private IJobPostService jobPostService;

    @Override
    @GetMapping("sync")
    public void sync() {
        RLock lock = redissonClient.getLock(HRM_SYNC_BEI_SEN_JOB_POST_LOCK);
        try {
            lock.lock();
            log.info("同步职务信息开始，userId={}", AppContext.getContext().getUserId());
            //获取上次同步时间
            HrmSyncDto syncDto = syncService.getHrmSyncDto(CODE_JOB_POST_SYNC_TIME);
            //同步数据
            jobPostService.sync(syncDto.getLastSyncTime(), syncDto.getEnSyncTime());
            //保存本次执行时间
            syncService.saveLastSyncTime(CODE_JOB_POST_SYNC_TIME, syncDto.getEnSyncTime());
            log.info("同步职务信息结束");
        } finally {
            lock.unlock();
        }
    }

    @Override
    @PostMapping("get_by_userId")
    public HrmJobPostDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request) {
        return jobPostService.getByUserId(request);
    }

    @Override
    @PostMapping("list_by_roleId")
    public List<HrmJobPostListSimpleOutputDto> listByRoleId(@RequestBody @Validated IdRequest request) {
        return jobPostService.listByRoleId(request);
    }

    @Override
    @PostMapping("page_simple")
    public PageResponse<HrmJobPostListSimpleOutputDto> pageSimple(@RequestBody @Validated HrmJobPostListSimpleInputDto inputDto) {
        Page<HrmJobPostListSimpleOutputDto> pageResult = jobPostService.pageSimple(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, HrmJobPostDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        return jobPostService.mapByIdSet(request);
    }

}
