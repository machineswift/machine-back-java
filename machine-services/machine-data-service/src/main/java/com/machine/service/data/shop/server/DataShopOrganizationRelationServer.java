package com.machine.service.data.shop.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.data.shop.dto.input.DataShopMapByShopIdSetInputDto;
import com.machine.client.data.shop.dto.input.DataShopBindOrganizationInputDto;
import com.machine.client.data.shop.dto.output.DataShopOrganizationRelationListOutputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationShopRelationQueryListInputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.shop.service.IDataShopOrganizationRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/data/shop_organization_relation")
public class DataShopOrganizationRelationServer implements IDataShopOrganizationRelationClient {

    @Autowired
    private IDataShopOrganizationRelationService shopOrganizationRelationService;

    @Override
    @PostMapping("bind_organization")
    public void bindOrganization(@RequestBody @Validated DataShopBindOrganizationInputDto inputDto) {
        log.info("门店绑定组织，inputDto={}", JSONUtil.toJsonStr(inputDto));
        shopOrganizationRelationService.bindOrganization(inputDto);
    }

    @Override
    @PostMapping("is_association_shop_by_organizationId")
    public Boolean isAssociationShopByOrganizationId(@RequestBody @Validated IdRequest request) {
        return shopOrganizationRelationService.isAssociationShopByOrganizationId(request);
    }

    @Override
    @PostMapping("map_by_shopIdSet")
    public Map<String, String> mapByShopIdSet(@RequestBody @Validated DataShopMapByShopIdSetInputDto inputDto) {
        return shopOrganizationRelationService.mapByShopIdSet(inputDto);
    }

    @Override
    @PostMapping("list_shopId_by_organizationIdSet")
    public List<String> listShopIdByOrganizationIdSet(@RequestBody @Validated IdSetRequest request) {
        return shopOrganizationRelationService.listShopIdByOrganizationIdSet(request);
    }

    @Override
    @PostMapping("list_by_shopId")
    public List<DataShopOrganizationRelationListOutputDto> listByShopId(@RequestBody @Validated IdRequest request) {
        return shopOrganizationRelationService.listByShopId(request);
    }

    @Override
    @PostMapping("list_by_shopIdSet")
    public List<DataShopOrganizationRelationListOutputDto> listByShopIdSet(@RequestBody @Validated IdSetRequest request) {
        return shopOrganizationRelationService.listByShopIdSet(request);
    }

    @Override
    @PostMapping("list_by_organizationIdSet")
    public List<DataShopOrganizationRelationListOutputDto> listByOrganizationIdSet(
            @RequestBody @Validated IdSetRequest request) {
        return shopOrganizationRelationService.listByOrganizationIdSet(request);
    }

    @Override
    @PostMapping("list_by_condition")
    public List<DataShopOrganizationRelationListOutputDto> listByCondition(
            @RequestBody @Validated IamOrganizationShopRelationQueryListInputDto inputDto) {
        return shopOrganizationRelationService.listByCondition(inputDto);
    }
}
