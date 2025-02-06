package com.machine.service.data.franchisee.dao;

import com.machine.service.data.franchisee.dao.mapper.entity.FranchiseeShopRelationEntity;

import java.util.List;

public interface IFranchiseeShopRelationDao {

    int insert( String shopCode,
                FranchiseeShopRelationEntity entity);

    void deleteByUk(String franchiseeId,
                    String shopId,
                    String shopCode);

    FranchiseeShopRelationEntity getByShopId(String shopId);

    List<FranchiseeShopRelationEntity> getByFranchiseeId(String franchiseeId);

}
