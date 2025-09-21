package com.machine.service.data.brand.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.data.brand.dao.mapper.entity.DataBrandEntity;

import java.util.List;
import java.util.Set;

public interface IDataBrandDao {

    String insert(DataBrandEntity entity);

    int delete(String id);

    int update(DataBrandEntity updateEntity);

    int updateStatus(String id,
                     StatusEnum status);

    DataBrandEntity getById(String id);

    DataBrandEntity getByName(String name);

    List<DataBrandEntity> selectByIdSet(Set<String> idSet);

    Page<DataBrandEntity> selectPage(DataBrandQueryPageInputDto inputDto);
}
