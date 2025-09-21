package com.machine.service.data.leaf.service.impl;

import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.exception.data.DataLeafBusinessException;
import com.machine.service.data.leaf.dao.IDataLeafDao;
import com.machine.service.data.leaf.dao.mapper.entity.DataLeafEntity;
import com.machine.service.data.leaf.service.IDataLeafService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Objects;

@Slf4j
@Service
public class DataLeafServiceImpl implements IDataLeafService {

    @Autowired
    private IDataLeafDao leafDao;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public String getKqBatchNo(String category,
                               String prefix,
                               int length,
                               int step,
                               String remark) {
        final long expireTime = System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000;
        final String dateStr = LocalDateTime.now().format(DATE_FORMATTER);
        final String bizTag = category + prefix + dateStr;

        StringBuilder value = new StringBuilder(getLeafId(bizTag, step, expireTime, remark).toString());
        if (value.length() > length) {
            throw new DataLeafBusinessException("data.leaf." + category + prefix, "取号超过" + length + "位数");
        }
        while (value.length() < length) {
            value.insert(0, "0");
        }
        return prefix + dateStr + value;
    }

    @Override
    public Long getLeafId(String bizTag,
                          Integer step,
                          Long expireTime,
                          String remark) {
        Objects.requireNonNull(bizTag, "bizTag 不能为空");
        if (step != null && step < 1) {
            throw new InvalidParameterException("step 不能为空小于1");
        }

        LeafData leafData = leafMap.computeIfAbsent(bizTag, k -> new LeafData(expireTime));

        Long id = leafData.poll();
        if (id == null) {
            DataLeafEntity entity = new DataLeafEntity();
            entity.setBizTag(bizTag);
            entity.setStep(step);
            entity.setExpireTime(expireTime);
            entity.setRemark(remark);
            DataLeafEntity leafAlloc = leafDao.updateMaxId(entity);
            if (leafAlloc == null) {
                throw new DataBusinessException("data.leaf.service.failedTakeNumber", "leaf 取号失败");
            }

            id = allocateLeafId(leafData, leafAlloc);
        }
        return id;
    }

    private Long allocateLeafId(LeafData leafData, DataLeafEntity leafAlloc) {
        Long id;
        if (leafAlloc.getStep() > 1) {
            id = leafAlloc.getMaxId() + 1;
            for (int i = 1; i < leafAlloc.getStep(); i++) {
                leafData.add(leafAlloc.getMaxId() + i + 1);
            }
        } else if (leafAlloc.getStep() == 1) {
            id = leafAlloc.getMaxId() + 1;
        } else {
            throw new DataBusinessException("data.leaf.service.failedTakeNumber", "获取leaf数据失败,step 小于1");
        }
        return id;
    }

    @Data
    @NoArgsConstructor
    private static class LeafData {
        private Long expireTime;
        private Queue<Long> queue = new ConcurrentLinkedQueue<>();

        public LeafData(Long expireTime) {
            this.expireTime = expireTime;
        }

        void add(Long id) {
            this.queue.add(id);
        }

        Long poll() {
            return this.queue.poll();
        }
    }

    /**
     * 清理过期leaf数据（每天早上6点执行一次）
     */
    @Scheduled(cron = "0 6 * * * ?")
    private void loadTenantInfo() {
        final long currentTimeMillis = System.currentTimeMillis();
        leafMap.entrySet().removeIf(entry -> {
            LeafData leafData = entry.getValue();
            return leafData != null &&
                    leafData.getExpireTime() != null &&
                    leafData.getExpireTime() != null &&
                    leafData.getExpireTime().compareTo(currentTimeMillis) < 0;
        });
    }

    private static final Map<String, LeafData> leafMap = new ConcurrentHashMap<>();

}
