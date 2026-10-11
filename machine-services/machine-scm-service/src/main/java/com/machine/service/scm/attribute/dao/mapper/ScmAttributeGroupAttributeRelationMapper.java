package com.machine.service.scm.attribute.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupAttributeRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface ScmAttributeGroupAttributeRelationMapper extends BaseMapper<ScmAttributeGroupAttributeRelationEntity> {

    int deleteByGroupId(@Param("groupId") String groupId);

    int deleteByAttributeId(@Param("attributeId") String attributeId);

    int countByGroupAndAttribute(@Param("groupId") String groupId, @Param("attributeId") String attributeId);

    List<String> listAttributeIdByGroupId(@Param("groupId") String groupId);

    List<ScmAttributeGroupAttributeRelationEntity> listByGroupIdSet(@Param("groupIdSet") Set<String> groupIdSet);

    List<ScmAttributeGroupAttributeRelationEntity> listByAttributeIdSet(@Param("attributeIdSet") Set<String> attributeIdSet);
}