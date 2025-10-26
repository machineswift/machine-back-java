package com.machine.client.data.shop;

import com.machine.client.data.shop.dto.input.DataShopMapByShopIdSetInputDto;
import com.machine.client.data.shop.dto.input.DataShopBindOrganizationInputDto;
import com.machine.client.data.shop.dto.output.DataShopOrganizationRelationListOutputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationShopRelationQueryListInputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/shop_organization_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataShopOrganizationRelationClient {

    @PostMapping("bind_organization")
    void bindOrganization(@RequestBody @Validated DataShopBindOrganizationInputDto inputDto);

    /**
     * 获取组织是否关联的门店信息
     */
    @PostMapping("is_association_shop_by_organizationId")
    Boolean isAssociationShopByOrganizationId(@RequestBody @Validated IdRequest request);

    /**
     * 获取门店和组织的Map<ShopId,OrganizationId>
     */
    @PostMapping("map_by_shopIdSet")
    Map<String, String> mapByShopIdSet(@RequestBody @Validated DataShopMapByShopIdSetInputDto inputDto);

    /**
     * 查询组织关联的门店Id
     */
    @PostMapping("list_shopId_by_organizationIdSet")
    List<String> listShopIdByOrganizationIdSet(@RequestBody @Validated IdSetRequest request);

    /**
     * 查询门店关联的组织信息
     */
    @PostMapping("list_by_shopId")
    List<DataShopOrganizationRelationListOutputDto> listByShopId(@RequestBody @Validated IdRequest request);

    /**
     * 查询门店关联的组织信息
     */
    @PostMapping("list_by_shopIdSet")
    List<DataShopOrganizationRelationListOutputDto> listByShopIdSet(@RequestBody @Validated IdSetRequest request);

    /**
     * 查询组织关联的门店信息
     */
    @PostMapping("list_by_organizationIdSet")
    List<DataShopOrganizationRelationListOutputDto> listByOrganizationIdSet(
            @RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_condition")
    List<DataShopOrganizationRelationListOutputDto> listByCondition(
            @RequestBody @Validated IamOrganizationShopRelationQueryListInputDto inputDto);

}



