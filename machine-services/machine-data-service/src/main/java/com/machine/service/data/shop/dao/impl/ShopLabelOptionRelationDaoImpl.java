package com.machine.service.data.shop.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.model.dto.IdCountDto;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.shop.dao.IShopLabelOptionRelationDao;
import com.machine.service.data.shop.dao.mapper.IShopLabelOptionRelationMapper;
import com.machine.service.data.shop.dao.mapper.entity.ShopLabelOptionRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ShopLabelOptionRelationDaoImpl implements IShopLabelOptionRelationDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IShopLabelOptionRelationMapper shopLabelOptionRelationMapper;

    @Override
    public int deleteByShopId(String shopId) {
        Wrapper<ShopLabelOptionRelationEntity> deleteWrapper = new LambdaQueryWrapper<ShopLabelOptionRelationEntity>()
                .eq(ShopLabelOptionRelationEntity::getShopId, shopId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_UPDATE_SHOP_LABEL, new IdDto(shopId));

        return shopLabelOptionRelationMapper.delete(deleteWrapper);
    }

    @Override
    public void batchInsert(String shopId,
                            Set<String> labelOptionIdSet) {
        for (String labelOptionId : labelOptionIdSet) {
            ShopLabelOptionRelationEntity entity = new ShopLabelOptionRelationEntity();
            entity.setShopId(shopId);
            entity.setLabelOptionId(labelOptionId);
            shopLabelOptionRelationMapper.insert(entity);
        }

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_UPDATE_SHOP_LABEL, new IdDto(shopId));
    }

    @Override
    public int batchUpdateLabel(String labelOptionIdId,
                                Set<String> shopIdSet) {
        for (String shopId : shopIdSet) {
            ShopLabelOptionRelationEntity entity = new ShopLabelOptionRelationEntity();
            entity.setShopId(shopId);
            entity.setLabelOptionId(labelOptionIdId);
            shopLabelOptionRelationMapper.insert(entity);
            //事件
            customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_UPDATE_SHOP_LABEL, new IdDto(shopId));
        }
        return shopIdSet.size();
    }

    @Override
    public List<String> listLabelOptionIdByShopId(String shopId) {
        return shopLabelOptionRelationMapper.listLabelOptionIdByShopId(shopId);
    }


    @Override
    public List<String> listShopIdByLabelOptionId(String labelOptionId) {
        return shopLabelOptionRelationMapper.listShopIdByLabelOptionId(labelOptionId);
    }

    @Override
    public List<String> listShopIdByLabelOptionIds(Set<String> labelOptionIdSet) {
        return shopLabelOptionRelationMapper.listShopIdByLabelOptionIds(labelOptionIdSet);
    }

    @Override
    public List<IdCountDto> countShopByLabelOptionIds(Set<String> labelOptionIdSet) {
        if (CollectionUtil.isEmpty(labelOptionIdSet)) {
            return List.of();
        }
        return shopLabelOptionRelationMapper.countShopByLabelOptionIds(labelOptionIdSet);
    }

    @Override
    public List<ShopLabelOptionRelationEntity> listByLabelOptionId(String labelOptionId) {
        Wrapper<ShopLabelOptionRelationEntity> queryWrapper = new LambdaQueryWrapper<ShopLabelOptionRelationEntity>()
                .eq(ShopLabelOptionRelationEntity::getLabelOptionId, labelOptionId);
        return shopLabelOptionRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<ShopLabelOptionRelationEntity> listByShopIds(Set<String> shopIdSet) {
        Wrapper<ShopLabelOptionRelationEntity> queryWrapper = new LambdaQueryWrapper<ShopLabelOptionRelationEntity>()
                .in(ShopLabelOptionRelationEntity::getShopId, shopIdSet);
        return shopLabelOptionRelationMapper.selectList(queryWrapper);
    }

}
