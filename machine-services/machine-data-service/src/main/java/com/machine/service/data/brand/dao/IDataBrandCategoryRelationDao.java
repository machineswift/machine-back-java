package com.machine.service.data.brand.dao;

import com.machine.service.data.brand.dao.mapper.entity.DataBrandCategoryRelationEntity;

import java.util.List;
import java.util.Set;

public interface IDataBrandCategoryRelationDao {

    void insert(DataBrandCategoryRelationEntity entity);

    void deleteByBrandId(String brandId);

    Long selectCountByCategoryId(String categoryId);

    Long selectCountByCategoryIdSet(Set<String> categoryIdSet);

    List<DataBrandCategoryRelationEntity> listByBrandId(String brandId);

    List<DataBrandCategoryRelationEntity> listByBrandIdSet(Set<String> brandIdSet);
}
