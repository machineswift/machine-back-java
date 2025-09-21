package com.machine.service.hrm.jobpost.dao;

import com.machine.service.hrm.jobpost.dao.mapper.entity.JobPostRoleRelationEntityEntity;

import java.util.List;
import java.util.Set;

public interface IJobPostRoleRelationDao {

    void batchCreate(List<JobPostRoleRelationEntityEntity> insertEntityList);

    int deleteByRoleId(String roleId);

    List<JobPostRoleRelationEntityEntity> selectByRoleId(String roleId);

    List<JobPostRoleRelationEntityEntity> listByRoleIds(Set<String> roleIds);

    List<JobPostRoleRelationEntityEntity> selectByJobPostId(String jobPostId);

    List<JobPostRoleRelationEntityEntity> listByJobPostIds(Set<String> jobPostIds);
}
