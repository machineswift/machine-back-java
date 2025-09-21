package com.machine.service.data.file.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.dto.input.DataDownloadQueryPageInputDto;
import com.machine.client.data.file.dto.input.DataDownloadQueryInputDto;
import com.machine.service.data.file.dao.IDataDownloadDao;
import com.machine.service.data.file.dao.mapper.DataDownloadEntityMapper;
import com.machine.service.data.file.dao.mapper.entity.DataDownloadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataDownloadDaoImpl implements IDataDownloadDao {

    @Autowired
    private DataDownloadEntityMapper dataDownloadEntityMapper;

    @Override
    public String insert(DataDownloadEntity entity) {
        dataDownloadEntityMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(DataDownloadEntity dataDownloadEntity) {
        return dataDownloadEntityMapper.updateById(dataDownloadEntity);
    }

    @Override
    public DataDownloadEntity getById(String id) {
        return dataDownloadEntityMapper.selectById(id);
    }


    @Override
    public List<DataDownloadEntity> queryByLimit(DataDownloadQueryInputDto inputDto) {
        Wrapper<DataDownloadEntity> queryWrapper = new LambdaQueryWrapper<DataDownloadEntity>()
                .eq(DataDownloadEntity::getStatus, inputDto.getStatus())
                .orderByAsc(DataDownloadEntity::getCreateTime)
                .last("LIMIT " + inputDto.getLimit());
        return dataDownloadEntityMapper.selectList(queryWrapper);
    }

    @Override
    public Page<DataDownloadEntity> selectPage(DataDownloadQueryPageInputDto inputDto) {
        IPage<DataDownloadEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return dataDownloadEntityMapper.selectPage(inputDto, page);
    }
}
