package com.machine.service.scm.property.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.scm.property.dto.input.ScmPropertyQueryPageInputDto;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyEntity;

import java.util.List;

public interface IScmPropertyDao {

    String insert(ScmPropertyEntity entity);

    int deleteById(String id);

    int update(ScmPropertyEntity entity);

    long countByCode(String code);

    long countByName(String name);

    long countByNameExcludeId(String name, String excludeId);

    ScmPropertyEntity getById(String id);

    List<ScmPropertyEntity> listAll();

    Page<ScmPropertyEntity> selectPage(ScmPropertyQueryPageInputDto inputDto);
}