package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.user.dao.mapper.entity.IamUserOrganizationRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface IamUserOrganizationRelationMapper extends BaseMapper<IamUserOrganizationRelationEntity> {

    boolean listUserIdByOrganizationId(@Param("organizationId") String organizationId);

    List<String> listUserIdByOrganizationIdSet(@Param("organizationIdSet") Set<String> organizationIdSet);
}
