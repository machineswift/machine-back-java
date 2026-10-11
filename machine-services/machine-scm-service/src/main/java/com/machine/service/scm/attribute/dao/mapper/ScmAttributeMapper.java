package com.machine.service.scm.attribute.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScmAttributeMapper extends BaseMapper<ScmAttributeEntity> {

}