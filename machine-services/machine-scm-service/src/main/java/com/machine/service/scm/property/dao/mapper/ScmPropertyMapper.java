package com.machine.service.scm.property.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScmPropertyMapper extends BaseMapper<ScmPropertyEntity> {

}