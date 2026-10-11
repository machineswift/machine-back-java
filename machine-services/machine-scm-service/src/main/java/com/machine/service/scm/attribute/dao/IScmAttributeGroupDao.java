package com.machine.service.scm.attribute.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryPageInputDto;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupEntity;

import java.util.List;
import java.util.Set;

public interface IScmAttributeGroupDao {

    String insert(ScmAttributeGroupEntity entity);

    int deleteById(String id);

    int update(ScmAttributeGroupEntity entity);

    ScmAttributeGroupEntity getById(String id);

    ScmAttributeGroupEntity getByCode(String code);

    List<ScmAttributeGroupEntity> listByIdSet(Set<String> idSet);

    Page<ScmAttributeGroupEntity> selectPage(ScmAttributeGroupQueryPageInputDto inputDto, Set<String> groupIdIn);
}