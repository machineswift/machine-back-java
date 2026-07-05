package com.machine.service.data.filecenter.attachment.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.filecenter.attachment.dao.IDataFileTempDao;
import com.machine.service.data.filecenter.attachment.dao.mapper.DataFileTempMapper;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataFileTempEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class DataFileTempDaoImpl implements IDataFileTempDao {

    @Autowired
    private DataFileTempMapper dataFileTempMapper;

    @Override
    public String insert(DataFileTempEntity entity) {
        dataFileTempMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int deleteById(String id) {
        if (StrUtil.isBlank(id)) {
            return 0;
        }
        return dataFileTempMapper.deleteById(id);
    }

    @Override
    public DataFileTempEntity getById(String id) {
        if (StrUtil.isBlank(id)) {
            return null;
        }
        return dataFileTempMapper.selectById(id);
    }

    @Override
    public List<DataFileTempEntity> selectByIds(Collection<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return List.of();
        }
        return dataFileTempMapper.selectByIds(ids);
    }

    @Override
    public List<DataFileTempEntity> listTempBeforeTime(Long deadline) {
        if (deadline == null) {
            return List.of();
        }
        Wrapper<DataFileTempEntity> wrapper = new LambdaQueryWrapper<DataFileTempEntity>()
                .lt(DataFileTempEntity::getExpireTime, deadline)
                .orderByAsc(DataFileTempEntity::getExpireTime);
        return dataFileTempMapper.selectList(wrapper);
    }
}
