package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserOrganizationRelationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserOrganizationRelationMapper extends BaseMapper<UserOrganizationRelationEntity> {
}
