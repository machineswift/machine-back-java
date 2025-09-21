package com.machine.service.data.shop.dao;

import com.machine.client.iam.organization.dto.input.IamOrganizationShopRelationQueryListInputDto;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.service.data.shop.dao.mapper.entity.DataShopOrganizationRelationEntity;

import java.util.List;
import java.util.Set;

public interface IDataShopOrganizationRelationDao {

    int insert(DataShopOrganizationRelationEntity entity);

    int deleteOneByUk(String shopId,
                      IamOrganizationTypeEnum organizationType);

    int update(DataShopOrganizationRelationEntity entity);

    Boolean isAssociationShopByOrganizationId(String organizationId);

    DataShopOrganizationRelationEntity selectOneByUk(String shopId,
                                                     String organizationId);

    DataShopOrganizationRelationEntity selectOneByUk(String shopId,
                                                     IamOrganizationTypeEnum organizationType);

    List<String> listShopIdByOrganizationIdSet(Set<String> organizationIdSet);

    List<DataShopOrganizationRelationEntity> listByShopId(String shopId);

    List<DataShopOrganizationRelationEntity> listByOrganizationIdSet(Set<String> organizationIdSet);

    List<DataShopOrganizationRelationEntity> listByShopIdSet(Set<String> shopIdSet);

    List<DataShopOrganizationRelationEntity> listByShopIdSet(IamOrganizationTypeEnum organizationType,
                                                             Set<String> shopIdSet);

    List<DataShopOrganizationRelationEntity> listByCondition(IamOrganizationShopRelationQueryListInputDto inputDto);

}
