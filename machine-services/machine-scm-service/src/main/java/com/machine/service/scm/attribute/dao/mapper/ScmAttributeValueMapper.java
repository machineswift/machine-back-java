package com.machine.service.scm.attribute.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeValueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScmAttributeValueMapper extends BaseMapper<ScmAttributeValueEntity> {

    int deleteByAttributeId(@Param("attributeId") String attributeId);

    int countByAttributeIdAndCode(@Param("attributeId") String attributeId, @Param("code") String code);

    int countByAttributeIdAndName(@Param("attributeId") String attributeId, @Param("name") String name);

    ScmAttributeValueEntity getByAttributeIdAndCode(@Param("attributeId") String attributeId, @Param("code") String code);

    ScmAttributeValueEntity getByAttributeIdAndName(@Param("attributeId") String attributeId, @Param("name") String name);

    List<ScmAttributeValueEntity> listByAttributeId(@Param("attributeId") String attributeId);

}