package com.machine.service.iam.biam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserOrganizationRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface BIamUserOrganizationRelationMapper extends BaseMapper<BIamUserOrganizationRelationEntity> {

    boolean listUserIdByOrganizationId(@Param("organizationId") String organizationId);

    List<String> listUserIdByOrganizationIdSet(@Param("organizationIdSet") Set<String> organizationIdSet);
}
