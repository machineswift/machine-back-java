package com.machine.service.scm.attribute.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryPageInputDto;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface ScmAttributeGroupMapper extends BaseMapper<ScmAttributeGroupEntity> {

    Page<ScmAttributeGroupEntity> selectPage(@Param("inputDto") ScmAttributeGroupQueryPageInputDto inputDto,
                                            @Param("groupIdIn") Set<String> groupIdIn,
                                            IPage<ScmAttributeGroupEntity> page);
}