package com.machine.service.data.label.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.label.dto.input.DataLabelOptionQueryListOffsetInputDto;
import com.machine.client.data.label.dto.input.DataLabelOptionQueryPageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.data.label.dao.mapper.entity.LabelOptionEntity;

import java.util.List;
import java.util.Set;

public interface ILabelOptionDao {

    String create(LabelOptionEntity entity);

    int deleteById(String id,
                   String labelId);

    int updateStatus(String id,
                     String labelId,
                     StatusEnum status);

    int update(String labelId,
               LabelOptionEntity entity);


    LabelOptionEntity getById(String id);

    LabelOptionEntity getByLabelIdAndName(String labelId,
                                          String name);

    List<LabelOptionEntity> listByIdSet(Set<String> idSet);

    List<LabelOptionEntity> listByOffset(DataLabelOptionQueryListOffsetInputDto inputDto);

    Page<LabelOptionEntity> selectPage(DataLabelOptionQueryPageInputDto inputDto);

}
