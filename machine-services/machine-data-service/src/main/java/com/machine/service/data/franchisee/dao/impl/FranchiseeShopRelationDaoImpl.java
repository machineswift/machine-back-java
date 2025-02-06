package com.machine.service.data.franchisee.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.self.domain.data.franchisee.DataFranchiseeBindShopDto;
import com.machine.sdk.self.domain.data.franchisee.DataFranchiseeUnbindShopDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.franchisee.dao.IFranchiseeShopRelationDao;
import com.machine.service.data.franchisee.dao.mapper.FranchiseeShopRelationMapper;
import com.machine.service.data.franchisee.dao.mapper.entity.FranchiseeShopRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FranchiseeShopRelationDaoImpl implements IFranchiseeShopRelationDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private FranchiseeShopRelationMapper franchiseeShopRelationMapper;

    @Override
    public int insert( String shopCode,
                       FranchiseeShopRelationEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_FRANCHISEE_BIND_SHOP,
                new DataFranchiseeBindShopDto(entity.getFranchiseeId(), shopCode));

        return franchiseeShopRelationMapper.insert(entity);
    }

    @Override
    public void deleteByUk(String franchiseeId,
                           String shopId,
                           String shopCode) {
        Wrapper<FranchiseeShopRelationEntity> deleteWrapper = new LambdaQueryWrapper<FranchiseeShopRelationEntity>()
                .eq(FranchiseeShopRelationEntity::getFranchiseeId, franchiseeId)
                .eq(FranchiseeShopRelationEntity::getShopId, shopId);
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_FRANCHISEE_UNBIND_SHOP,
                new DataFranchiseeUnbindShopDto(franchiseeId, shopCode));

        franchiseeShopRelationMapper.delete(deleteWrapper);
    }

    @Override
    public FranchiseeShopRelationEntity getByShopId(String shopId) {
        Wrapper<FranchiseeShopRelationEntity> queryWrapper = new LambdaQueryWrapper<FranchiseeShopRelationEntity>()
                .eq(FranchiseeShopRelationEntity::getShopId, shopId);
        return franchiseeShopRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<FranchiseeShopRelationEntity> getByFranchiseeId(String franchiseeId) {
        Wrapper<FranchiseeShopRelationEntity> queryWrapper = new LambdaQueryWrapper<FranchiseeShopRelationEntity>()
                .eq(FranchiseeShopRelationEntity::getFranchiseeId, franchiseeId);
        return franchiseeShopRelationMapper.selectList(queryWrapper);
    }
}
