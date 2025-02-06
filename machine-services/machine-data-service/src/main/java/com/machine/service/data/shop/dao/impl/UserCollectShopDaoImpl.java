package com.machine.service.data.shop.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.shop.dao.IUserCollectShopDao;
import com.machine.service.data.shop.dao.mapper.IUserCollectShopMapper;
import com.machine.service.data.shop.dao.mapper.entity.UserCollectShopEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserCollectShopDaoImpl implements IUserCollectShopDao {

    @Autowired
    private IUserCollectShopMapper userCollectShopMapper;

    @Override
    public int deleteByShopIdSet(String userId,
                                 Set<String> unCollectShopIdSet) {
        Wrapper<UserCollectShopEntity> deleteWrapper = new LambdaQueryWrapper<UserCollectShopEntity>()
                .eq(UserCollectShopEntity::getUserId, userId)
                .in(UserCollectShopEntity::getShopId, unCollectShopIdSet);
        return userCollectShopMapper.delete(deleteWrapper);
    }

    @Override
    public void insertByShopIdSet(String userId,
                                  Set<String> unCollectShopIdSet) {
        for (String shopId : unCollectShopIdSet) {
            UserCollectShopEntity entity = new UserCollectShopEntity();
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
