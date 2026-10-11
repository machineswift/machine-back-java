package com.machine.service.scm.attribute.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.scm.attribute.dto.input.ScmAttributeQueryPageInputDto;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeEntity;

import java.util.List;
import java.util.Set;

public interface IScmAttributeDao {

    String insert(ScmAttributeEntity entity);

    int deleteById(String id);

    int update(ScmAttributeEntity entity);

    long countByName(String name);

    long countByNameExcludeId(String name, String excludeId);

    ScmAttributeEntity getByCode(String code);

    ScmAttributeEntity getById(String id);

    List<ScmAttributeEntity> listByIdSet(Set<String> idSet);

    List<ScmAttributeEntity> listAll();

    Page<ScmAttributeEntity> selectPage(ScmAttributeQueryPageInputDto inputDto);
}