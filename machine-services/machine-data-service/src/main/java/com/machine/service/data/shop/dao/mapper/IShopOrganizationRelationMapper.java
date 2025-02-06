package com.machine.service.data.shop.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.iam.organization.dto.input.IamOrganizationShopRelationQueryListInputDto;
import com.machine.service.data.shop.dao.mapper.entity.ShopOrganizationRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface IShopOrganizationRelationMapper extends BaseMapper<ShopOrganizationRelationEntity> {

    Boolean isAssociationShop(@Param("organizationIdSet") Set<String> organizationIdSet);

    List<String> listShopIdByOrganizationIdSet(@Param("organizationIdSet") Set<String> organizationIdSet);

    List<ShopOrganizationRelationEntity> listByCondition(@Param("inputDto") IamOrganizationShopRelationQueryListInputDto inputDto);
}
