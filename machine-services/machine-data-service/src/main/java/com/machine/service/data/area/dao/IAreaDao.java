package com.machine.service.data.area.dao;

import com.machine.service.data.area.dao.mapper.entity.AreaEntity;

import java.util.List;

public interface IAreaDao {

    List<AreaEntity> selectAll();
}
