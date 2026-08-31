package com.machine.service.data.shop.dao;

import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationShopRelationQueryListInputDto;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.service.data.shop.dao.mapper.entity.DataShopOrganizationRelationEntity;

import java.util.List;
import java.util.Set;

public interface IDataShopOrganizationRelationDao {

    int insert(DataShopOrganizationRelationEntity entity);

    int deleteOneByUk(String shopId,
                      BIamOrganizationTypeEnum organizationType);

    int update(DataShopOrganizationRelationEntity entity);

    Boolean isAssociationShopByOrganizationId(String organizationId);

    DataShopOrganizationRelationEntity selectOneByUk(String shopId,
                                                     String organizationId);

    DataShopOrganizationRelationEntity selectOneByUk(String shopId,
                                                     BIamOrganizationTypeEnum organizationType);

    List<String> listShopIdByOrganizationIdSet(Set<String> organizationIdSet);

    List<DataShopOrganizationRelationEntity> listByShopId(String shopId);

    List<DataShopOrganizationRelationEntity> listByOrganizationIdSet(Set<String> organizationIdSet);

    List<DataShopOrganizationRelationEntity> listByShopIdSet(Set<String> shopIdSet);

    List<DataShopOrganizationRelationEntity> listByShopIdSet(BIamOrganizationTypeEnum organizationType,
                                                             Set<String> shopIdSet);

    List<DataShopOrganizationRelationEntity> listByCondition(BIamOrganizationShopRelationQueryListInputDto inputDto);

}
