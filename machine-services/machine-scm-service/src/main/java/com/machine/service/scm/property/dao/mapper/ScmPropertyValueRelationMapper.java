package com.machine.service.scm.property.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyValueRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface ScmPropertyValueRelationMapper extends BaseMapper<ScmPropertyValueRelationEntity> {

    List<String> listChildPropertyIdByParentValueId(@Param("parentValueId") String parentValueId);

    List<ScmPropertyValueRelationEntity> listByParentValueIdSet(@Param("parentValueIdSet") Set<String> parentValueIdSet);

    int deleteByParentValueId(@Param("parentValueId") String parentValueId);

    int deleteByChildPropertyId(@Param("childPropertyId") String childPropertyId);

    int countByParentValueAndChildProperty(@Param("parentValueId") String parentValueId, @Param("childPropertyId") String childPropertyId);
}