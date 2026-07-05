package com.machine.service.scm.property.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyGroupRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface ScmPropertyGroupRelationMapper extends BaseMapper<ScmPropertyGroupRelationEntity> {

    List<String> listPropertyIdByGroupId(@Param("groupId") String groupId);

    List<ScmPropertyGroupRelationEntity> listByGroupIdSet(@Param("groupIdSet") Set<String> groupIdSet);

    int deleteByGroupId(@Param("groupId") String groupId);

    int deleteByPropertyId(@Param("propertyId") String propertyId);

    int countByGroupAndProperty(@Param("groupId") String groupId, @Param("propertyId") String propertyId);
}