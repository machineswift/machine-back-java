package com.machine.service.iam.biam.role.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.role.dto.input.BIamRoleListSubInputDto;
import com.machine.client.iam.biam.role.dto.input.BIamRoleQueryPageInputDto;
import com.machine.sdk.base.envm.biam.role.BIamRoleTypeEnum;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BIamRoleMapper extends BaseMapper<BIamRoleEntity> {

    List<String> listAllCode();

    List<String> listSubId(@Param("inputDto") BIamRoleListSubInputDto inputDto);

    List<String> listIdByType(@Param("type") BIamRoleTypeEnum type);

    List<BIamRoleEntity> listSub(@Param("inputDto") BIamRoleListSubInputDto inputDto);

    List<BIamRoleEntity> selectByUserId(@Param("userId") String userId);

    Page<BIamRoleEntity> selectPage(@Param("inputDto") BIamRoleQueryPageInputDto inputDto,
                                    IPage<BIamRoleEntity> page);

}
