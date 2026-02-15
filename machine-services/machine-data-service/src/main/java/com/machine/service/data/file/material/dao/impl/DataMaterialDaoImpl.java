package com.machine.service.data.file.material.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.service.data.file.material.dao.IDataMaterialDao;
import com.machine.service.data.file.material.dao.mapper.DataMaterialMapper;
import com.machine.service.data.file.material.dao.mapper.entity.DataMaterialEntity;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Material.DATA_MATERIAL_URL_KEY;

@Repository
public class DataMaterialDaoImpl implements IDataMaterialDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private DataMaterialMapper materialMapper;

    @Override
    public String insert(DataMaterialEntity entity) {
        materialMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void update(DataMaterialEntity entity) {

        //缓存
        customerRedisCommands.del(DATA_MATERIAL_URL_KEY + entity.getId());

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
