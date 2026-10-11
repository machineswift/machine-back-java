package com.machine.service.data.brand.dao;

import com.machine.service.data.brand.dao.mapper.entity.DataBrandCategoryEntity;

import java.util.List;

public interface IDataBrandCategoryDao {

    String insert(DataBrandCategoryEntity entity);

    int delete(String id);

    int update(DataBrandCategoryEntity entity);

    DataBrandCategoryEntity getById(String id);

    boolean existsByParentId(String parentId);

    DataBrandCategoryEntity getByParentIdAndName(String parentId, String name);

    List<DataBrandCategoryEntity> listAll();
}
