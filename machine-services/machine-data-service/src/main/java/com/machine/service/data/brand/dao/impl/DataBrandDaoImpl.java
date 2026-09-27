package com.machine.service.data.brand.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.hutool.core.collection.CollectionUtil;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQuerySimplePageInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.service.data.brand.dao.IDataBrandDao;
import com.machine.service.data.brand.dao.mapper.DataBrandMapper;
import com.machine.service.data.brand.dao.mapper.entity.DataBrandEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataBrandDaoImpl implements IDataBrandDao {

    @Autowired
    private DataBrandMapper brandMapper;

    @Override
    public String insert(DataBrandEntity entity) {
        brandMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int delete(String id) {
        return brandMapper.deleteById(id);
    }

    @Override
    public int update(DataBrandEntity updateEntity) {
        return brandMapper.updateById(updateEntity);
    }

    @Override
    public int updateStatus(String id,
                            StatusEnum status) {
        DataBrandEntity updateEntity = new DataBrandEntity();
        updateEntity.setId(id);
        updateEntity.setStatus(status);
        return brandMapper.updateById(updateEntity);
    }

    @Override
    public int updateLogoAttachmentId(String id,
                                      String logoAttachmentId) {
        DataBrandEntity updateEntity = new DataBrandEntity();
        updateEntity.setId(id);
        updateEntity.setLogoAttachmentId(logoAttachmentId);
        return brandMapper.updateById(updateEntity);
    }

    @Override
    public int updateParent(String id,
                            String parentId) {
        DataBrandEntity updateEntity = new DataBrandEntity();
        updateEntity.setId(id);
        updateEntity.setParentId(parentId);
        return brandMapper.updateById(updateEntity);
    }

    @Override
    public DataBrandEntity getById(String id) {
        return brandMapper.selectById(id);
    }

    @Override
    public boolean exists(String id) {
        Wrapper<DataBrandEntity> wrapper = new LambdaQueryWrapper<DataBrandEntity>()
                .eq(DataBrandEntity::getId, id);
        return brandMapper.exists(wrapper);
    }

    @Override
    public DataBrandEntity getByName(String name) {
        Wrapper<DataBrandEntity> wrapper = new LambdaQueryWrapper<DataBrandEntity>()
                .eq(DataBrandEntity::getName, name);
        return brandMapper.selectOne(wrapper);
    }

    @Override
    public boolean existsByParentId(String parentId) {
        Wrapper<DataBrandEntity> wrapper = new LambdaQueryWrapper<DataBrandEntity>()
                .eq(DataBrandEntity::getParentId, parentId);
        return brandMapper.exists(wrapper);
    }

    @Override
    public boolean existsEnabledByParentId(String parentId) {
        Wrapper<DataBrandEntity> wrapper = new LambdaQueryWrapper<DataBrandEntity>()
                .eq(DataBrandEntity::getParentId, parentId)
                .eq(DataBrandEntity::getStatus, StatusEnum.ENABLE);
        return brandMapper.exists(wrapper);
    }

    @Override
    public Set<String> selectHasChildrenIdSet(Set<String> idSet) {
        if (CollectionUtil.isEmpty(idSet)) {
            return Set.of();
        }

        return brandMapper.selectHasChildrenIdSet(idSet);
    }

    @Override
    public List<DataBrandEntity> selectAncestorList(String id) {
        return brandMapper.selectAncestorList(id);
    }

    @Override
    public List<DataBrandEntity> selectByIdSet(Set<String> idSet) {
        return brandMapper.selectByIds(idSet);
    }

    @Override
    public Page<DataBrandEntity> selectPage(DataBrandQueryPageInputDto inputDto) {
        IPage<DataBrandEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return brandMapper.selectPage(inputDto, page);
    }

    @Override
    public Page<DataBrandEntity> selectChildrenPage(DataBrandQueryPageInputDto inputDto) {
        IPage<DataBrandEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return brandMapper.selectChildrenPage(inputDto, page);
    }

    @Override
    public Page<DataBrandEntity> selectSimplePage(DataBrandQuerySimplePageInputDto inputDto) {
        IPage<DataBrandEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return brandMapper.selectSimplePage(inputDto, page);
    }

}
