package com.machine.service.scm.property.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupQueryInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupListOutputDto;
import com.machine.sdk.base.exception.scm.ScmBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.dao.IScmPropertyGroupDao;
import com.machine.service.scm.property.dao.IScmPropertyGroupRelationDao;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyGroupEntity;
import com.machine.service.scm.property.service.IScmPropertyGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScmPropertyGroupServiceImpl implements IScmPropertyGroupService {

    @Autowired
    private IScmPropertyGroupDao propertyGroupDao;

    @Autowired
    private IScmPropertyGroupRelationDao propertyGroupRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmPropertyGroupCreateInputDto inputDto) {
        if (propertyGroupDao.countByBackCategoryAndName(inputDto.getBackCategoryId(), inputDto.getName()) > 0) {
            throw new ScmBusinessException("scm.propertyGroup.service.create.nameAlreadyExists", "分组名称已经存在");
        }
        ScmPropertyGroupEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmPropertyGroupEntity.class);
        return propertyGroupDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmPropertyGroupUpdateInputDto inputDto) {
        ScmPropertyGroupEntity entityById = propertyGroupDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.propertyGroup.service.update.notExists", "属性分组不存在");
        }
        if (propertyGroupDao.countByBackCategoryAndName(entityById.getBackCategoryId(), inputDto.getName()) > 0
                && !inputDto.getName().equals(entityById.getName())) {
            throw new ScmBusinessException("scm.propertyGroup.service.update.nameAlreadyExists", "分组名称已经存在");
        }
        ScmPropertyGroupEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmPropertyGroupEntity.class);
        entity.setBackCategoryId(entityById.getBackCategoryId());
        return propertyGroupDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        ScmPropertyGroupEntity entity = propertyGroupDao.getById(request.getId());
        if (entity == null) {
            return 0;
        }
        propertyGroupRelationDao.deleteByGroupId(request.getId());
        return propertyGroupDao.deleteById(request.getId());
    }

    @Override
    public ScmPropertyGroupDetailOutputDto getById(IdRequest request) {
        ScmPropertyGroupEntity entity = propertyGroupDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmPropertyGroupDetailOutputDto.class);
    }

    @Override
    public List<ScmPropertyGroupListOutputDto> listByBackCategoryId(ScmPropertyGroupQueryInputDto inputDto) {
        return propertyGroupDao.listByBackCategoryId(inputDto.getBackCategoryId()).stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmPropertyGroupListOutputDto.class))
                .collect(Collectors.toList());
    }

}
