package com.machine.service.hrm.jobpost.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPostListSimpleInputDto;
import com.machine.service.hrm.jobpost.dao.mapper.entity.JobPostEntity;

import java.util.List;
import java.util.Set;

public interface IJobPostDao {

    void batchInsert(List<JobPostEntity> entityList);

    JobPostEntity getById(String id);

    List<JobPostEntity> selectByIdSet(Set<String> idSet);

    List<JobPostEntity> listByRoleId(String roleId);

    Page<JobPostEntity> pageSimple(HrmJobPostListSimpleInputDto inputDto);

}
