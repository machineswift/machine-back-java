package com.machine.service.hrm.jobpost.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPosBatchInsertInputDto;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPostCreateInputDto;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPostListSimpleInputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostDetailOutputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostListSimpleOutputDto;
import com.machine.sdk.beisen.client.jobpost.BeiSenJobPostClient;
import com.machine.sdk.beisen.client.jobpost.dto.input.BeiSenJobPostWindowInputDto;
import com.machine.sdk.beisen.client.jobpost.dto.output.BeiSenJobPostOutputDto;
import com.machine.sdk.beisen.domain.BeiSenBaseResponse;
import com.machine.sdk.beisen.envm.BeiSenJobPostStatusEnum;
import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.hrm.HrmJobPostStatusEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.hrm.jobpost.dao.IJobPostDao;
import com.machine.service.hrm.jobpost.dao.mapper.entity.JobPostEntity;
import com.machine.service.hrm.jobpost.service.IJobPostService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JobPostServiceImpl implements IJobPostService {

    @Resource(name = "beiSenOkHttpClient")
    private OkHttpClient okHttpClient;

    @Autowired
    private IJobPostDao jobPostDao;

    @Override
    public void sync(long lastSyncTime,
                     long enSyncTime) {

        long nextSyncTime = lastSyncTime + 30 * 24 * 60 * 60 * 1000L;
        if (nextSyncTime > enSyncTime) {
            nextSyncTime = enSyncTime;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        long startTime = lastSyncTime;
        long stopTime = nextSyncTime;
        String scrollId = null;
        List<BeiSenJobPostOutputDto> outputDtoList = new ArrayList<>();

        while (true) {
            BeiSenJobPostWindowInputDto inputDto = new BeiSenJobPostWindowInputDto();
            inputDto.setStartTime(sdf.format(new Date(startTime)));
            inputDto.setStopTime(sdf.format(new Date(stopTime)));
            inputDto.setTimeWindowQueryType("2");
            inputDto.setScrollId(scrollId);
            BeiSenBaseResponse<BeiSenJobPostOutputDto> baseResponse = BeiSenJobPostClient.getByTimeWindow(okHttpClient, inputDto);
            if (!CollectionUtil.isEmpty(baseResponse.getData())) {
                outputDtoList.addAll(baseResponse.getData());
            }

            Boolean isLastData = baseResponse.getIsLastData();
            if (isLastData || CollectionUtil.isEmpty(baseResponse.getData())) {
                scrollId = null;
                if (stopTime == enSyncTime) {
                    break;
                }

                startTime = stopTime;
                stopTime = startTime + 30 * 24 * 60 * 60 * 1000L;
                if (stopTime > enSyncTime) {
                    stopTime = enSyncTime;
                }
            } else {
                scrollId = baseResponse.getScrollId();
            }
        }

        if (CollectionUtil.isEmpty(outputDtoList)) {
            log.info("定时任务更新职务信息查询结果为空");
            return;
        }
        log.info("定时任务更新职务信息查询结果，size={}", outputDtoList.size());

        List<HrmJobPostCreateInputDto> inputDtoList = new ArrayList<>();
        for (BeiSenJobPostOutputDto outputDto : outputDtoList) {
            HrmJobPostCreateInputDto inputDto = new HrmJobPostCreateInputDto();
            inputDto.setId(outputDto.getOId());
            inputDto.setName(outputDto.getName());
            inputDto.setCode(outputDto.getCode());
            inputDto.setStatus(BaseEnum.getInstance(HrmJobPostStatusEnum.class, outputDto.getStatus() + ""));
            inputDto.setSort(0L);
            if (null != outputDto.getOrder()) {
                inputDto.setSort(outputDto.getOrder().longValue());
            }
            inputDtoList.add(inputDto);
        }
        batchCreate(new HrmJobPosBatchInsertInputDto(inputDtoList));
    }

    @Override
    public void batchCreate(HrmJobPosBatchInsertInputDto inputDto) {
        List<JobPostEntity> entityList = JSONUtil.toList(JSONUtil.toJsonStr(inputDto.getInputDtoList()), JobPostEntity.class);
        jobPostDao.batchInsert(entityList);
    }

    @Override
    public HrmJobPostDetailOutputDto getByUserId(IdRequest request) {
        JobPostEntity entity = jobPostDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), HrmJobPostDetailOutputDto.class);
    }

    @Override
    public List<HrmJobPostListSimpleOutputDto> listByRoleId(IdRequest request) {
        List<JobPostEntity> entityList = jobPostDao.listByRoleId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), HrmJobPostListSimpleOutputDto.class);
    }

    @Override
    public Page<HrmJobPostListSimpleOutputDto> pageSimple(HrmJobPostListSimpleInputDto inputDto) {
        Page<JobPostEntity> page = jobPostDao.pageSimple(inputDto);
        Page<HrmJobPostListSimpleOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), HrmJobPostListSimpleOutputDto.class));
        return pageResult;
    }

    @Override
    public Map<String, HrmJobPostDetailOutputDto> mapByIdSet(IdSetRequest request) {
        List<JobPostEntity> userEntityList = jobPostDao.selectByIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(JobPostEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), HrmJobPostDetailOutputDto.class)));
    }
}
