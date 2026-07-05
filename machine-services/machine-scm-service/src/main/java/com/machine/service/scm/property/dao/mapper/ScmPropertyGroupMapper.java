package com.machine.service.scm.property.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface ScmPropertyGroupMapper extends BaseMapper<ScmPropertyGroupEntity> {

    List<ScmPropertyGroupEntity> listByBackCategoryId(@Param("backCategoryId") String backCategoryId);

    List<ScmPropertyGroupEntity> listByBackCategoryIdSet(@Param("backCategoryIdSet") Set<String> backCategoryIdSet);

    int deleteByBackCategoryId(@Param("backCategoryId") String backCategoryId);

    int countByBackCategoryAndName(@Param("backCategoryId") String backCategoryId, @Param("name") String name);
}