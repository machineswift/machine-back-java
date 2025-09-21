package com.machine.service.data.franchisee.dao;

import com.machine.service.data.franchisee.dao.mapper.entity.DataFranchiseeShopRelationEntity;

import java.util.List;

public interface IDataFranchiseeShopRelationDao {

    int insert( String shopCode,
                DataFranchiseeShopRelationEntity entity);

    void deleteByUk(String franchiseeId,
                    String shopId,
                    String shopCode);

    DataFranchiseeShopRelationEntity getByShopId(String shopId);

    List<DataFranchiseeShopRelationEntity> getByFranchiseeId(String franchiseeId);

}
