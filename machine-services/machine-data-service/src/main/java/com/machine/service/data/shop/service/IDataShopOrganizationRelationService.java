package com.machine.service.data.shop.service;

import com.machine.client.data.shop.dto.input.DataShopMapByShopIdSetInputDto;
import com.machine.client.data.shop.dto.input.DataShopBindOrganizationInputDto;
import com.machine.client.data.shop.dto.output.DataShopOrganizationRelationListOutputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationShopRelationQueryListInputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IDataShopOrganizationRelationService {

    void bindOrganization(DataShopBindOrganizationInputDto inputDto);

    Boolean isAssociationShopByOrganizationId(IdRequest request);

    List<String> listShopIdByOrganizationIdSet(IdSetRequest request);

    Map<String, String> mapByShopIdSet(DataShopMapByShopIdSetInputDto inputDto);

    List<DataShopOrganizationRelationListOutputDto> listByShopId(IdRequest request);

    List<DataShopOrganizationRelationListOutputDto> listByShopIdSet(IdSetRequest request);

    List<DataShopOrganizationRelationListOutputDto> listByOrganizationIdSet(IdSetRequest request);

    List<DataShopOrganizationRelationListOutputDto> listByCondition(IamOrganizationShopRelationQueryListInputDto request);

}
