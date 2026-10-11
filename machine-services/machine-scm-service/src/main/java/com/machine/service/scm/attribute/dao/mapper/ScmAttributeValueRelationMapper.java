package com.machine.service.scm.attribute.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeValueRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface ScmAttributeValueRelationMapper extends BaseMapper<ScmAttributeValueRelationEntity> {

    int deleteByParentValueId(@Param("parentValueId") String parentValueId);

    int deleteByParentValueIdSet(@Param("parentValueIdSet") Set<String> parentValueIdSet);

    int deleteByChildAttributeId(@Param("childAttributeId") String childAttributeId);

    int countByParentValueId(@Param("parentValueId") String parentValueId);

    int countByParentValueAndChildAttribute(@Param("parentValueId") String parentValueId, @Param("childAttributeId") String childAttributeId);

    List<String> listChildAttributeIdByParentValueId(@Param("parentValueId") String parentValueId);

    List<ScmAttributeValueRelationEntity> listByParentValueIdSet(@Param("parentValueIdSet") Set<String> parentValueIdSet);
}