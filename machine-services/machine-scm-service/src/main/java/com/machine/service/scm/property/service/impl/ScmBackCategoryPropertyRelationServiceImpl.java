package com.machine.service.scm.property.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.property.dto.input.ScmBackCategoryPropertyRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmBackCategoryPropertyRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.dao.IScmBackCategoryPropertyRelationDao;
import com.machine.service.scm.property.dao.mapper.entity.ScmBackCategoryPropertyRelationEntity;
import com.machine.service.scm.property.service.IScmBackCategoryPropertyRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ScmBackCategoryPropertyRelationServiceImpl implements IScmBackCategoryPropertyRelationService {

    @Autowired
    private IScmBackCategoryPropertyRelationDao relationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmBackCategoryPropertyRelationCreateInputDto inputDto) {
        ScmBackCategoryPropertyRelationEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmBackCategoryPropertyRelationEntity.class);
        return relationDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmBackCategoryPropertyRelationEntity entity) {
        return relationDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        return relationDao.deleteById(request.getId());
    }

    @Override
    public ScmBackCategoryPropertyRelationDetailOutputDto getById(IdRequest request) {
        ScmBackCategoryPropertyRelationEntity entity = relationDao.getById(request.getId());
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmBackCategoryPropertyRelationDetailOutputDto.class);
    }

    @Override
    public List<ScmBackCategoryPropertyRelationEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet) {
        return relationDao.listByBackCategoryIdSet(backCategoryIdSet);
    }

}