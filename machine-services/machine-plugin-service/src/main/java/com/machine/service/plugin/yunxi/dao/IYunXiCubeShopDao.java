package com.machine.service.plugin.yunxi.dao;

import com.machine.service.plugin.yunxi.dao.mapper.entity.YunXiCubeShopEntity;

import java.util.Collection;
import java.util.List;

public interface IYunXiCubeShopDao {

    List<YunXiCubeShopEntity> listByOffset(Long offset,
                                           int limit);

    YunXiCubeShopEntity getById(Long id);

    YunXiCubeShopEntity getByCode(String code);

    List<YunXiCubeShopEntity> selectByIds(Collection<Long> ids);

}
