package com.machine.service.data.brand.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
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
    public DataBrandEntity getById(String id) {
        return brandMapper.selectById(id);
    }

    @Override
    public DataBrandEntity getByName(String name) {
        Wrapper<DataBrandEntity> queryWrapper = new LambdaQueryWrapper<DataBrandEntity>()
                .eq(DataBrandEntity::getName, name);
        return brandMapper.selectOne(queryWrapper);
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

}
