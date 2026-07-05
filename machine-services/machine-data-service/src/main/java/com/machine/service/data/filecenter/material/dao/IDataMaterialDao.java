package com.machine.service.data.filecenter.material.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.filecenter.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.service.data.filecenter.material.dao.mapper.entity.DataMaterialEntity;

import java.util.List;
import java.util.Set;

public interface IDataMaterialDao {

    String insert(DataMaterialEntity entity);

    void update(DataMaterialEntity entity);

    DataMaterialEntity getById(String id);

    List<DataMaterialEntity> selectByIdSet(Set<String> idSet);

    Page<DataMaterialEntity> selectPage(DataMaterialQueryPageInputDto inputDto);
}
