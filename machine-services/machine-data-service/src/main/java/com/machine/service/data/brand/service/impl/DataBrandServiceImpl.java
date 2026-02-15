package com.machine.service.data.brand.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.brand.dto.input.DataBrandCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateStatusInputDto;
import com.machine.client.data.brand.dto.output.DataBrandDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandListOutputDto;
import com.machine.client.data.leaf.IDataLeaf4DataCodeClient;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.brand.dao.IDataBrandDao;
import com.machine.service.data.brand.dao.mapper.entity.DataBrandEntity;
import com.machine.service.data.brand.service.IDataBrandService;
import com.machine.service.data.file.material.dao.IDataMaterialDao;
import com.machine.service.data.file.material.dao.mapper.entity.DataMaterialEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DataBrandServiceImpl implements IDataBrandService {

    @Autowired
    private IDataBrandDao brandDao;

    @Autowired
    private IDataMaterialDao materialDao;

    @Autowired
    private IDataLeaf4DataCodeClient leaf4DataCodeClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataBrandCreateInputDto inputDto) {
        //验证名称是否存在
        DataBrandEntity entityByName = brandDao.getByName(inputDto.getName());
        if (null != entityByName) {
            throw new DataBusinessException("data.brand.service.create.nameAlreadyExists", "名称已经存在");
        }

        //验证logo 素材是否存在
        String logoMaterialId = inputDto.getLogoMaterialId();
        if (StrUtil.isNotBlank(logoMaterialId)) {
            DataMaterialEntity materialEntity = materialDao.getById(logoMaterialId);
            if (null == materialEntity) {
                throw new DataBusinessException("data.brand.service.create.logoBotExists", "LOGO不存在");
            }
        }

        DataBrandEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataBrandEntity.class);
        insertEntity.setStatus(StatusEnum.ENABLE);
        //角色编码
        insertEntity.setCode(leaf4DataCodeClient.brandCode());

        return brandDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        DataBrandEntity entity = brandDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        if (StatusEnum.ENABLE.equals(entity.getStatus())) {
            throw new DataBusinessException("data.brand.service.delete.enableStatus", "启用状态，不能删除");
        }
        return brandDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataBrandUpdateInputDto inputDto) {
        DataBrandEntity entity = brandDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        //验证名称是否存在
        DataBrandEntity entityByName = brandDao.getByName(inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new DataBusinessException("data.brand.service.update.nameAlreadyExists", "名称已经存在");
        }

        //验证logo 素材是否存在
        String logoMaterialId = inputDto.getLogoMaterialId();
        if (StrUtil.isNotBlank(logoMaterialId)) {
            DataMaterialEntity materialEntity = materialDao.getById(logoMaterialId);
            if (null == materialEntity) {
                throw new DataBusinessException("data.brand.service.update.logoBotExists", "LOGO不存在");
            }
        }

        DataBrandEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataBrandEntity.class);
        updateEntity.setId(entity.getId());
        return brandDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(DataBrandUpdateStatusInputDto inputDto) {
        DataBrandEntity entity = brandDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (entity.getStatus() == inputDto.getStatus()) {
            return 0;
        }

        return brandDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    public DataBrandDetailOutputDto detail(IdRequest request) {
        DataBrandEntity entity = brandDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataBrandDetailOutputDto.class);
    }

    @Override
    public Page<DataBrandListOutputDto> page(DataBrandQueryPageInputDto inputDto) {
        Page<DataBrandEntity> page = brandDao.selectPage(inputDto);
        Page<DataBrandListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataBrandListOutputDto.class));
        return pageResult;
    }

    @Override
    public Map<String, DataBrandDetailOutputDto> mapByIdSet(IdSetRequest request) {
        List<DataBrandEntity> userEntityList = brandDao.selectByIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(DataBrandEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), DataBrandDetailOutputDto.class)));
    }
}
