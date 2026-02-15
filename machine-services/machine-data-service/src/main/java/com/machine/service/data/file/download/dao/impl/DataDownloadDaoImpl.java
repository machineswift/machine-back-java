package com.machine.service.data.file.download.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.download.dto.input.DataDownloadQueryPageInputDto;
import com.machine.client.data.file.download.dto.input.DataDownloadQueryInputDto;
import com.machine.service.data.file.download.dao.IDataDownloadDao;
import com.machine.service.data.file.download.dao.mapper.DataDownloadMapper;
import com.machine.service.data.file.download.dao.mapper.entity.DataDownloadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataDownloadDaoImpl implements IDataDownloadDao {

    @Autowired
    private DataDownloadMapper dataDownloadMapper;

    @Override
    public String insert(DataDownloadEntity entity) {
        dataDownloadMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(DataDownloadEntity dataDownloadEntity) {
        return dataDownloadMapper.updateById(dataDownloadEntity);
    }

    @Override
    public DataDownloadEntity getById(String id) {
        return dataDownloadMapper.selectById(id);
    }


    @Override
    public List<DataDownloadEntity> queryByLimit(DataDownloadQueryInputDto inputDto) {
        Wrapper<DataDownloadEntity> queryWrapper = new LambdaQueryWrapper<DataDownloadEntity>()
                .eq(DataDownloadEntity::getStatus, inputDto.getStatus())
                .orderByAsc(DataDownloadEntity::getCreateTime)
                .last("LIMIT " + inputDto.getLimit());
        return dataDownloadMapper.selectList(queryWrapper);
    }

    @Override
    public Page<DataDownloadEntity> selectPage(DataDownloadQueryPageInputDto inputDto) {
        IPage<DataDownloadEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return dataDownloadMapper.selectPage(inputDto, page);
    }
}
