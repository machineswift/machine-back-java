package com.machine.service.iam.organization.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.organization.dao.mapper.entity.OrganizationEntity;
import com.machine.service.iam.organization.dao.mapper.entity.OrganizationUserRelationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IOrganizationUserRelationMapper extends BaseMapper<OrganizationUserRelationEntity> {

}
