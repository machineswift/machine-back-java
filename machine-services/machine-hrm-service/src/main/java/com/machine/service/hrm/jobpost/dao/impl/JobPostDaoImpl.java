package com.machine.service.hrm.jobpost.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPostListSimpleInputDto;
import com.machine.service.hrm.jobpost.dao.IJobPostDao;
import com.machine.service.hrm.jobpost.dao.mapper.IJobPostMapper;
import com.machine.service.hrm.jobpost.dao.mapper.entity.JobPostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class JobPostDaoImpl implements IJobPostDao {

    @Autowired
    private IJobPostMapper jobPostMapper;

    @Override
    public void batchInsert(List<JobPostEntity> entityList) {
        for (JobPostEntity entity : entityList) {
            JobPostEntity e = jobPostMapper.selectById(entity.getId());
            if (e == null) {
                jobPostMapper.insert(entity);
            } else {
                jobPostMapper.updateById(entity);
            }
        }
    }

    @Override
    public JobPostEntity getById(String id) {
        return jobPostMapper.selectById(id);
    }

    @Override
    public List<JobPostEntity> selectByIdSet(Set<String> idSet) {
        return jobPostMapper.selectByIds(idSet);
    }

    @Override
    public List<JobPostEntity> listByRoleId(String roleId) {
        return jobPostMapper.selectByRoleId(roleId);
    }

    @Override
    public Page<JobPostEntity> pageSimple(HrmJobPostListSimpleInputDto inputDto) {
        IPage<JobPostEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return jobPostMapper.pageSimple(inputDto, page);
    }
}
