package com.machine.service.hrm.jobpost.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPosRoleRelationBatchInsertInputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostRoleRelationListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.hrm.jobpost.dao.IJobPostRoleRelationDao;
import com.machine.service.hrm.jobpost.dao.mapper.entity.JobPostRoleRelationEntityEntity;
import com.machine.service.hrm.jobpost.service.IJobPostRoleRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JobPostRoleRelationServiceImpl implements IJobPostRoleRelationService {

    @Autowired
    private IJobPostRoleRelationDao jobPostRoleRelationDao;


    @Override
    public void batchCreate(HrmJobPosRoleRelationBatchInsertInputDto inputDto) {
        List<JobPostRoleRelationEntityEntity> insertEntityList = JSONUtil.toList(
                JSONUtil.toJsonStr(inputDto.getInputDtoList()), JobPostRoleRelationEntityEntity.class);
        jobPostRoleRelationDao.batchCreate(insertEntityList);
    }

    @Override
    public int deleteByRoleId(IdRequest request) {
        return jobPostRoleRelationDao.deleteByRoleId(request.getId());
    }

    @Override
    public List<HrmJobPostRoleRelationListOutputDto> listByRoleIds(IdSetRequest request) {
        List<JobPostRoleRelationEntityEntity> entityList = jobPostRoleRelationDao.listByRoleIds(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), HrmJobPostRoleRelationListOutputDto.class);
    }

    @Override
    public List<HrmJobPostRoleRelationListOutputDto> listByJobPostIds(IdSetRequest request) {
        List<JobPostRoleRelationEntityEntity> entityList = jobPostRoleRelationDao.listByJobPostIds(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), HrmJobPostRoleRelationListOutputDto.class);
    }
}
