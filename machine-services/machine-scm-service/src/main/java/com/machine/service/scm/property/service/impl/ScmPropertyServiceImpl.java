package com.machine.service.scm.property.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.scm.property.dto.input.ScmPropertyCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyQueryPageInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyListOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueListOutputDto;
import com.machine.sdk.base.exception.scm.ScmBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.scm.property.dao.IScmBackCategoryPropertyRelationDao;
import com.machine.service.scm.property.dao.IScmPropertyDao;
import com.machine.service.scm.property.dao.IScmPropertyGroupRelationDao;
import com.machine.service.scm.property.dao.IScmPropertyValueDao;
import com.machine.service.scm.property.dao.IScmPropertyValueRelationDao;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyEntity;
import com.machine.service.scm.property.service.IScmPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScmPropertyServiceImpl implements IScmPropertyService {

    @Autowired
    private IScmPropertyDao propertyDao;

    @Autowired
    private IScmPropertyValueDao propertyValueDao;

    @Autowired
    private IScmBackCategoryPropertyRelationDao backCategoryPropertyRelationDao;

    @Autowired
    private IScmPropertyGroupRelationDao propertyGroupRelationDao;

    @Autowired
    private IScmPropertyValueRelationDao propertyValueRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmPropertyCreateInputDto inputDto) {
        if (propertyDao.countByName(inputDto.getName()) > 0) {
            throw new ScmBusinessException("scm.property.service.create.nameAlreadyExists", "名称已经存在");
        }
        if (propertyDao.countByCode(inputDto.getCode()) > 0) {
            throw new ScmBusinessException("scm.property.service.create.codeAlreadyExists", "编码已经存在");
        }
        ScmPropertyEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmPropertyEntity.class);
        return propertyDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmPropertyUpdateInputDto inputDto) {
        ScmPropertyEntity entityById = propertyDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.property.service.update.notExists", "属性不存在");
        }
        if (propertyDao.countByNameExcludeId(inputDto.getName(), inputDto.getId()) > 0) {
            throw new ScmBusinessException("scm.property.service.update.nameAlreadyExists", "名称已经存在");
        }
        ScmPropertyEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmPropertyEntity.class);
        return propertyDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        ScmPropertyEntity entityById = propertyDao.getById(request.getId());
        if (entityById == null) {
            return 0;
        }
        if (backCategoryPropertyRelationDao.countByPropertyId(request.getId()) > 0) {
            throw new ScmBusinessException("scm.property.service.delete.boundCategory", "属性已绑定后台类目，不能删除");
        }
        if (propertyGroupRelationDao.countByPropertyId(request.getId()) > 0) {
            throw new ScmBusinessException("scm.property.service.delete.boundGroup", "属性已加入属性分组，不能删除");
        }
        propertyValueRelationDao.deleteByChildPropertyId(request.getId());
        propertyValueDao.deleteByPropertyId(request.getId());
        return propertyDao.deleteById(request.getId());
    }

    @Override
    public ScmPropertyDetailOutputDto getById(IdRequest request) {
        ScmPropertyEntity entity = propertyDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        ScmPropertyDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmPropertyDetailOutputDto.class);
        List<ScmPropertyValueListOutputDto> valueList = propertyValueDao.listByPropertyId(request.getId()).stream()
                .map(valueEntity -> JSONUtil.toBean(JSONUtil.toJsonStr(valueEntity), ScmPropertyValueListOutputDto.class))
                .collect(Collectors.toList());
        outputDto.setValueList(valueList);
        return outputDto;
    }

    @Override
    public List<ScmPropertyListOutputDto> listAll() {
        return propertyDao.listAll().stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmPropertyListOutputDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<ScmPropertyListOutputDto> selectPage(ScmPropertyQueryPageInputDto inputDto) {
        Page<ScmPropertyEntity> page = propertyDao.selectPage(inputDto);
        if (page.getRecords() == null || page.getRecords().isEmpty()) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }
        List<ScmPropertyListOutputDto> records = page.getRecords().stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmPropertyListOutputDto.class))
                .collect(Collectors.toList());
        return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }
}
