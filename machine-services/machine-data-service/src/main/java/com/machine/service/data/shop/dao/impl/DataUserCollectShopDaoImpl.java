package com.machine.service.data.shop.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.shop.dao.IDataUserCollectShopDao;
import com.machine.service.data.shop.dao.mapper.DataUserCollectShopMapper;
import com.machine.service.data.shop.dao.mapper.entity.DataUserCollectShopEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataUserCollectShopDaoImpl implements IDataUserCollectShopDao {

    @Autowired
    private DataUserCollectShopMapper userCollectShopMapper;

    @Override
    public int deleteByShopIdSet(String userId,
                                 Set<String> unCollectShopIdSet) {
        Wrapper<DataUserCollectShopEntity> deleteWrapper = new LambdaQueryWrapper<DataUserCollectShopEntity>()
                .eq(DataUserCollectShopEntity::getUserId, userId)
                .in(DataUserCollectShopEntity::getShopId, unCollectShopIdSet);
        return userCollectShopMapper.delete(deleteWrapper);
    }

    @Override
    public void insertByShopIdSet(String userId,
                                  Set<String> unCollectShopIdSet) {
        for (String shopId : unCollectShopIdSet) {
            DataUserCollectShopEntity entity = new DataUserCollectShopEntity();
            entity.setUserId(userId);
            entity.setShopId(shopId);
            userCollectShopMapper.insert(entity);
        }
    }

    @Override
    public List<String> listCollectedIdByShopIdSet(String userId,
                                                   Set<String> shopIdSet) {
        return userCollectShopMapper.listCollectedIdByShopIdSet(userId, shopIdSet);
    }
}
