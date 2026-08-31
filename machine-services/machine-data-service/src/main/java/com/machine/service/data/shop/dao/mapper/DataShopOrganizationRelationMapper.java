package com.machine.service.data.shop.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationShopRelationQueryListInputDto;
import com.machine.service.data.shop.dao.mapper.entity.DataShopOrganizationRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface DataShopOrganizationRelationMapper extends BaseMapper<DataShopOrganizationRelationEntity> {

    Boolean isAssociationShop(@Param("organizationId") String organizationId);

    List<String> listShopIdByOrganizationIdSet(@Param("organizationIdSet") Set<String> organizationIdSet);

    List<DataShopOrganizationRelationEntity> listByCondition(@Param("inputDto") BIamOrganizationShopRelationQueryListInputDto inputDto);
}
