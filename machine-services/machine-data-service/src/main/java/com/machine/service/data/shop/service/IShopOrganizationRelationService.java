package com.machine.service.data.shop.service;

import com.machine.client.data.shop.dto.input.DataShopMapByShopIdSetInputDto;
import com.machine.client.data.shop.dto.input.ShopBindOrganizationInputDto;
import com.machine.client.data.shop.dto.output.ShopOrganizationRelationListOutputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationShopRelationQueryListInputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IShopOrganizationRelationService {

    void bindOrganization(ShopBindOrganizationInputDto inputDto);

    Boolean isAssociationShopByOrganizationIdSet(Set<String> organizationIdSet);

    List<String> listShopIdByOrganizationIdSet(IdSetRequest request);

    Map<String, String> mapByShopIdSet(DataShopMapByShopIdSetInputDto inputDto);

    List<ShopOrganizationRelationListOutputDto> listByShopId(IdRequest request);

    List<ShopOrganizationRelationListOutputDto> listByShopIdSet(IdSetRequest request);

    List<ShopOrganizationRelationListOutputDto> listByOrganizationIdSet(IdSetRequest request);

    List<ShopOrganizationRelationListOutputDto> listByCondition(IamOrganizationShopRelationQueryListInputDto request);

}
