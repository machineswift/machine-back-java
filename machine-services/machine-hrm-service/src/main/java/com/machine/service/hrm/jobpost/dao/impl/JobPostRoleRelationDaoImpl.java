package com.machine.service.hrm.jobpost.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.hrm.jobpost.dao.IJobPostRoleRelationDao;
import com.machine.service.hrm.jobpost.dao.mapper.IJobPostRoleRelationMapper;
import com.machine.service.hrm.jobpost.dao.mapper.entity.JobPostRoleRelationEntityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class JobPostRoleRelationDaoImpl implements IJobPostRoleRelationDao {

    @Autowired
    private IJobPostRoleRelationMapper jobPostRoleRelationMapper;

    @Override
    public void batchCreate(List<JobPostRoleRelationEntityEntity> insertEntityList) {
        for (JobPostRoleRelationEntityEntity insertEntity : insertEntityList) {
            jobPostRoleRelationMapper.insert(insertEntity);
        }
    }

    @Override
    public int deleteByRoleId(String roleId) {
        Wrapper<JobPostRoleRelationEntityEntity> wrapper = new LambdaQueryWrapper<JobPostRoleRelationEntityEntity>()
                .eq(JobPostRoleRelationEntityEntity::getRoleId, roleId);
        return jobPostRoleRelationMapper.delete(wrapper);
    }

    @Override
    public List<JobPostRoleRelationEntityEntity> selectByRoleId(String roleId) {
        Wrapper<JobPostRoleRelationEntityEntity> wrapper = new LambdaQueryWrapper<JobPostRoleRelationEntityEntity>()
                .eq(JobPostRoleRelationEntityEntity::getRoleId, roleId);
        return jobPostRoleRelationMapper.selectList(wrapper);
    }

    @Override
    public List<JobPostRoleRelationEntityEntity> listByRoleIds(Set<String> roleIds) {
        Wrapper<JobPostRoleRelationEntityEntity> wrapper = new LambdaQueryWrapper<JobPostRoleRelationEntityEntity>()
                .in(JobPostRoleRelationEntityEntity::getRoleId, roleIds);
        return jobPostRoleRelationMapper.selectList(wrapper);
    }

    @Override
    public List<JobPostRoleRelationEntityEntity> selectByJobPostId(String jobPostId) {
        Wrapper<JobPostRoleRelationEntityEntity> wrapper = new LambdaQueryWrapper<JobPostRoleRelationEntityEntity>()
                .eq(JobPostRoleRelationEntityEntity::getJobPostId, jobPostId);
        return jobPostRoleRelationMapper.selectList(wrapper);
    }

    @Override
    public List<JobPostRoleRelationEntityEntity> listByJobPostIds(Set<String> jobPostIds) {
        Wrapper<JobPostRoleRelationEntityEntity> wrapper = new LambdaQueryWrapper<JobPostRoleRelationEntityEntity>()
                .in(JobPostRoleRelationEntityEntity::getJobPostId, jobPostIds);
        return jobPostRoleRelationMapper.selectList(wrapper);
    }
}
