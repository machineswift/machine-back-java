package com.machine.service.data.franchisee.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.self.domain.data.franchisee.DataFranchiseeBindShopDto;
import com.machine.sdk.self.domain.data.franchisee.DataFranchiseeUnbindShopDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.franchisee.dao.IDataFranchiseeShopRelationDao;
import com.machine.service.data.franchisee.dao.mapper.FranchiseeShopRelationMapper;
import com.machine.service.data.franchisee.dao.mapper.entity.DataFranchiseeShopRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataFranchiseeShopRelationDaoImpl implements IDataFranchiseeShopRelationDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private FranchiseeShopRelationMapper franchiseeShopRelationMapper;

    @Override
    public int insert( String shopCode,
                       DataFranchiseeShopRelationEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_FRANCHISEE_BIND_SHOP,
                new DataFranchiseeBindShopDto(entity.getFranchiseeId(), shopCode));

        return franchiseeShopRelationMapper.insert(entity);
    }

    @Override
    public void deleteByUk(String franchiseeId,
                           String shopId,
                           String shopCode) {
        Wrapper<DataFranchiseeShopRelationEntity> deleteWrapper = new LambdaQueryWrapper<DataFranchiseeShopRelationEntity>()
                .eq(DataFranchiseeShopRelationEntity::getFranchiseeId, franchiseeId)
                .eq(DataFranchiseeShopRelationEntity::getShopId, shopId);
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_FRANCHISEE_UNBIND_SHOP,
                new DataFranchiseeUnbindShopDto(franchiseeId, shopCode));

        franchiseeShopRelationMapper.delete(deleteWrapper);
    }

    @Override
    public DataFranchiseeShopRelationEntity getByShopId(String shopId) {
        Wrapper<DataFranchiseeShopRelationEntity> queryWrapper = new LambdaQueryWrapper<DataFranchiseeShopRelationEntity>()
                .eq(DataFranchiseeShopRelationEntity::getShopId, shopId);
        return franchiseeShopRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DataFranchiseeShopRelationEntity> getByFranchiseeId(String franchiseeId) {
        Wrapper<DataFranchiseeShopRelationEntity> queryWrapper = new LambdaQueryWrapper<DataFranchiseeShopRelationEntity>()
                .eq(DataFranchiseeShopRelationEntity::getFranchiseeId, franchiseeId);
        return franchiseeShopRelationMapper.selectList(queryWrapper);
    }
}
