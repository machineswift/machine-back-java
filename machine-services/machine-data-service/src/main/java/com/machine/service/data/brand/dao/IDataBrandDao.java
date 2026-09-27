package com.machine.service.data.brand.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQuerySimplePageInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.service.data.brand.dao.mapper.entity.DataBrandEntity;

import java.util.List;
import java.util.Set;

public interface IDataBrandDao {

    String insert(DataBrandEntity entity);

    int delete(String id);

    int update(DataBrandEntity updateEntity);

    int updateStatus(String id,
                     StatusEnum status);

    int updateLogoAttachmentId(String id,
                               String logoAttachmentId);

    int updateParent(String id,
                     String parentId);

    DataBrandEntity getById(String id);

    boolean exists(String id);

    DataBrandEntity getByName(String name);

    boolean existsByParentId(String parentId);

    boolean existsEnabledByParentId(String parentId);

    Set<String> selectHasChildrenIdSet(Set<String> idSet);

    List<DataBrandEntity> selectAncestorList(String id);

    List<DataBrandEntity> selectByIdSet(Set<String> idSet);

    Page<DataBrandEntity> selectPage(DataBrandQueryPageInputDto inputDto);

    Page<DataBrandEntity> selectChildrenPage(DataBrandQueryPageInputDto inputDto);

    Page<DataBrandEntity> selectSimplePage(DataBrandQuerySimplePageInputDto inputDto);
}
