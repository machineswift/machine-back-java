package com.machine.service.data.tag.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.tag.dto.input.DataTagQueryPageInputDto;
import com.machine.service.data.tag.dao.mapper.entity.DataTagEntity;

import java.util.List;

public interface IDataTagDao {

    String insert(DataTagEntity entity);

    int delete(String id);

    int update(DataTagEntity entity);

    DataTagEntity getById(String id);

    DataTagEntity getByCode(String code);

    DataTagEntity getByCategoryIdAndName(String categoryId,
                                         String name);

    List<DataTagEntity> selectByCategoryId(String categoryId);

    Page<DataTagEntity> selectPage(DataTagQueryPageInputDto inputDto);

}
