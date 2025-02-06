package com.machine.service.hrm.department.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.hrm.department.dao.mapper.entity.DepartmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDepartmentMapper extends BaseMapper<DepartmentEntity> {
    List<DepartmentEntity> listAll();
}
