package com.machine.service.data.shop.dao;

import com.machine.client.iam.organization.dto.input.IamOrganizationShopRelationQueryListInputDto;
import com.machine.sdk.common.envm.iam.organization.OrganizationTypeEnum;
import com.machine.service.data.shop.dao.mapper.entity.ShopOrganizationRelationEntity;

import java.util.List;
import java.util.Set;

public interface IShopOrganizationRelationDao {

    int insert(ShopOrganizationRelationEntity entity);

    int deleteOneByUk(String shopId,
                      OrganizationTypeEnum organizationType);

    int update(ShopOrganizationRelationEntity entity);

    Boolean isAssociationShopByOrganizationId(String organizationId);

    ShopOrganizationRelationEntity selectOneByUk(String shopId,
                                                 String organizationId);

    ShopOrganizationRelationEntity selectOneByUk(String shopId,
                                                 OrganizationTypeEnum organizationType);

    List<String> listShopIdByOrganizationIdSet(Set<String> organizationIdSet);

    List<ShopOrganizationRelationEntity> listByShopId(String shopId);

    List<ShopOrganizationRelationEntity> listByOrganizationIdSet(Set<String> organizationIdSet);

    List<ShopOrganizationRelationEntity> listByShopIdSet(Set<String> shopIdSet);

    List<ShopOrganizationRelationEntity> listByShopIdSet(OrganizationTypeEnum organizationType,
                                                         Set<String> shopIdSet);

    List<ShopOrganizationRelationEntity> listByCondition(IamOrganizationShopRelationQueryListInputDto inputDto);

}
