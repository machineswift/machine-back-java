package com.machine.service.data.area.dao.impl;

import com.machine.service.data.area.dao.IAreaDao;
import com.machine.service.data.area.dao.mapper.AreaMapper;
import com.machine.service.data.area.dao.mapper.entity.AreaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AreaDaoImpl implements IAreaDao {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<AreaEntity> selectAll() {
        return areaMapper.selectList(null);
    }
}
