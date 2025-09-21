package com.machine.service.plugin.xgj.dao;

import com.machine.service.plugin.xgj.dao.mapper.entity.XgjUserEntity;

import java.util.List;

public interface IXgjUserDao {

    List<XgjUserEntity> listByOffset(Long offset,
                                     int limit);
}
