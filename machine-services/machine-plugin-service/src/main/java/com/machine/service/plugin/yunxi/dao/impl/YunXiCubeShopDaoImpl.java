package com.machine.service.plugin.yunxi.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.plugin.yunxi.dao.IYunXiCubeShopDao;
import com.machine.service.plugin.yunxi.dao.mapper.IYunXiCubeShopMapper;
import com.machine.service.plugin.yunxi.dao.mapper.entity.YunXiCubeShopEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class YunXiCubeShopDaoImpl implements IYunXiCubeShopDao {

    @Autowired
    private IYunXiCubeShopMapper cubeShopMapper;

    @Override
    public List<YunXiCubeShopEntity> listByOffset(Long offset,
                                                  int limit) {
        return cubeShopMapper.listByOffset(offset, limit);
    }

    @Override
    public YunXiCubeShopEntity getById(Long id) {
        return cubeShopMapper.selectById(id);
    }

    @Override
    public YunXiCubeShopEntity getByCode(String code) {
        Wrapper<YunXiCubeShopEntity> queryWrapper = new LambdaQueryWrapper<YunXiCubeShopEntity>()
                .eq(YunXiCubeShopEntity::getCode, code)
                .eq(YunXiCubeShopEntity::getDr, 0);
        return cubeShopMapper.selectOne(queryWrapper);
    }

    @Override
    public List<YunXiCubeShopEntity> selectByIds(Collection<Long> ids) {
        Wrapper<YunXiCubeShopEntity> queryWrapper = new LambdaQueryWrapper<YunXiCubeShopEntity>()
                .in(YunXiCubeShopEntity::getId, ids);
        return cubeShopMapper.selectList(queryWrapper);
    }
}
