package com.machine.service.iam.role.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.role.dto.input.IamRoleListSubInputDto;
import com.machine.client.iam.role.dto.input.IamRoleQueryPageInputDto;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import com.machine.service.iam.role.dao.mapper.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IRoleMapper extends BaseMapper<RoleEntity> {

    List<String> listAllCode();

    List<String> listSubId(@Param("inputDto") IamRoleListSubInputDto inputDto);

    List<String> listIdByType(@Param("type") RoleTypeEnum type);

    List<RoleEntity> listSub(@Param("inputDto") IamRoleListSubInputDto inputDto);

    List<RoleEntity> selectByUserId(@Param("userId") String userId);

    Page<RoleEntity> selectPage(@Param("inputDto") IamRoleQueryPageInputDto inputDto,
                                IPage<RoleEntity> page);

}
