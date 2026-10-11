package com.machine.service.scm.attribute.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueQueryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateStatusInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueSimpleListOutputDto;
import com.machine.sdk.base.exception.scm.ScmBusinessException;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.attribute.dao.IScmAttributeDao;
import com.machine.service.scm.attribute.dao.IScmAttributeValueDao;
import com.machine.service.scm.attribute.dao.IScmAttributeValueRelationDao;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeEntity;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeValueEntity;
import com.machine.service.scm.attribute.service.IScmAttributeValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScmAttributeValueServiceImpl implements IScmAttributeValueService {

    @Autowired
    private IScmAttributeDao attributeDao;

    @Autowired
    private IScmAttributeValueDao attributeValueDao;

    @Autowired
    private IScmAttributeValueRelationDao attributeValueRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmAttributeValueCreateInputDto inputDto) {
        validateAttributeExists(inputDto.getAttributeId());
        if (attributeValueDao.countByAttributeIdAndCode(inputDto.getAttributeId(), inputDto.getCode()) > 0) {
            throw new ScmBusinessException("scm.attributeValue.service.create.codeAlreadyExists", "属性值编码已经存在");
        }
        if (attributeValueDao.countByAttributeIdAndName(inputDto.getAttributeId(), inputDto.getName()) > 0) {
            throw new ScmBusinessException("scm.attributeValue.service.create.nameAlreadyExists", "属性值名称已经存在");
        }
        ScmAttributeValueEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmAttributeValueEntity.class);
        entity.setStatus(StatusEnum.ENABLE);
        return attributeValueDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        ScmAttributeValueEntity entity = attributeValueDao.getById(request.getId());
        if (entity == null) {
            return 0;
        }
        if (StatusEnum.ENABLE.equals(entity.getStatus())) {
            throw new ScmBusinessException("scm.attributeValue.service.delete.enableStatus", "启用状态不能删除");
        }
        if (attributeValueRelationDao.countByParentValueId(request.getId()) > 0) {
            throw new ScmBusinessException("scm.attributeValue.service.delete.boundChildAttribute", "属性值已关联子属性，不能删除");
        }
        return attributeValueDao.deleteById(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmAttributeValueUpdateInputDto inputDto) {
        ScmAttributeValueEntity entityById = attributeValueDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attributeValue.service.update.notExists", "属性值不存在");
        }
        ScmAttributeValueEntity entityByName = attributeValueDao.getByAttributeIdAndName(
                entityById.getAttributeId(), inputDto.getName());
        if (entityByName != null && !entityByName.getId().equals(inputDto.getId())) {
            throw new ScmBusinessException("scm.attributeValue.service.update.nameAlreadyExists", "属性值名称已经存在");
        }
        ScmAttributeValueEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmAttributeValueEntity.class);
        entity.setAttributeId(entityById.getAttributeId());
        return attributeValueDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(ScmAttributeValueUpdateStatusInputDto inputDto) {
        ScmAttributeValueEntity entityById = attributeValueDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attributeValue.service.updateStatus.notExists", "属性值不存在");
        }
        if (inputDto.getStatus().equals(entityById.getStatus())) {
            return 0;
        }
        ScmAttributeValueEntity entity = new ScmAttributeValueEntity();
        entity.setId(inputDto.getId());
        entity.setStatus(inputDto.getStatus());
        return attributeValueDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCode(ScmAttributeValueUpdateCodeInputDto inputDto) {
        ScmAttributeValueEntity entityById = attributeValueDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.attributeValue.service.updateCode.notExists", "属性值不存在");
        }
        if (entityById.getCode().equals(inputDto.getCode())) {
            return 0;
        }
        ScmAttributeValueEntity entityByCode = attributeValueDao.getByAttributeIdAndCode(
                entityById.getAttributeId(), inputDto.getCode());
        if (entityByCode != null && !entityByCode.getId().equals(inputDto.getId())) {
            throw new ScmBusinessException("scm.attributeValue.service.updateCode.codeAlreadyExists", "属性值编码已经存在");
        }
        ScmAttributeValueEntity entity = new ScmAttributeValueEntity();
        entity.setId(inputDto.getId());
        entity.setCode(inputDto.getCode());
        return attributeValueDao.update(entity);
    }

    @Override
    public ScmAttributeValueDetailOutputDto getById(IdRequest request) {
        ScmAttributeValueEntity entity = attributeValueDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeValueDetailOutputDto.class);
    }

    @Override
    public List<ScmAttributeValueSimpleListOutputDto> listSimple(ScmAttributeValueQueryInputDto inputDto) {
        return attributeValueDao.listByAttributeId(inputDto.getAttributeId()).stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeValueSimpleListOutputDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScmAttributeValueListOutputDto> listExpand(ScmAttributeValueQueryInputDto inputDto) {
        return attributeValueDao.listByAttributeId(inputDto.getAttributeId()).stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeValueListOutputDto.class))
                .collect(Collectors.toList());
    }


    private void validateAttributeExists(String attributeId) {
        ScmAttributeEntity attribute = attributeDao.getById(attributeId);
        if (attribute == null) {
            throw new ScmBusinessException("scm.attributeValue.service.attributeNotExists", "属性不存在");
        }
    }
}
