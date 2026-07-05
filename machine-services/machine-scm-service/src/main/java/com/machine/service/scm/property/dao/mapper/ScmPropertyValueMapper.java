package com.machine.service.scm.property.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyValueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface ScmPropertyValueMapper extends BaseMapper<ScmPropertyValueEntity> {

    List<ScmPropertyValueEntity> listByPropertyId(@Param("propertyId") String propertyId);

    List<ScmPropertyValueEntity> listByPropertyIdSet(@Param("propertyIdSet") Set<String> propertyIdSet);

    int deleteByPropertyId(@Param("propertyId") String propertyId);

    int countByPropertyIdAndValue(@Param("propertyId") String propertyId, @Param("value") String value);

    int countByPropertyIdAndValueExcludeId(@Param("propertyId") String propertyId,
                                           @Param("value") String value,
                                           @Param("excludeId") String excludeId);
}