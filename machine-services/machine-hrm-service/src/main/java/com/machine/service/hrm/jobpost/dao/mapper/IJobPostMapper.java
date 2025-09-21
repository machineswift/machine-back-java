package com.machine.service.hrm.jobpost.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPostListSimpleInputDto;
import com.machine.service.hrm.jobpost.dao.mapper.entity.JobPostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IJobPostMapper extends BaseMapper<JobPostEntity> {

    List<JobPostEntity> selectByRoleId(@Param("roleId") String roleId);

    Page<JobPostEntity> pageSimple(@Param("inputDto") HrmJobPostListSimpleInputDto inputDto,
                                   IPage<JobPostEntity> page);

}
