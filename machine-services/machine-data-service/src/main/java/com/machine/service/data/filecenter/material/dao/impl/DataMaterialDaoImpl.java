package com.machine.service.data.filecenter.material.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.filecenter.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.service.data.filecenter.material.dao.IDataMaterialDao;
import com.machine.service.data.filecenter.material.dao.mapper.DataMaterialMapper;
import com.machine.service.data.filecenter.material.dao.mapper.entity.DataMaterialEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public class DataMaterialDaoImpl implements IDataMaterialDao {

    @Autowired
    private DataMaterialMapper materialMapper;

    @Override
    public String insert(DataMaterialEntity entity) {
        materialMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void update(DataMaterialEntity entity) {

        materialMapper.updateById(entity);
    }

    @Override
    public DataMaterialEntity getById(String id) {
        return materialMapper.selectById(id);
    }

    @Override
    public List<DataMaterialEntity> selectByIdSet(Set<String> idSet) {
        return materialMapper.selectByIds(idSet);
    }

    @Override
    public Page<DataMaterialEntity> selectPage(DataMaterialQueryPageInputDto inputDto) {
        IPage<DataMaterialEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return materialMapper.selectPage(inputDto, page);
    }

}
