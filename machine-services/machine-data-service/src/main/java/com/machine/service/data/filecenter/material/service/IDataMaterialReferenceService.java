package com.machine.service.data.filecenter.material.service;

import com.machine.client.data.filecenter.material.dto.output.DataMaterialReferenceDetailOutputDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDataMaterialReferenceService {

    List<DataMaterialReferenceDetailOutputDto> listByMaterialId(String materialId);

    Map<String, List<DataMaterialReferenceDetailOutputDto>> mapByMaterialIdSet(Set<String> materialIdSet);

    List<DataMaterialReferenceDetailOutputDto> listByEntity(String entity, String entityId);
}
