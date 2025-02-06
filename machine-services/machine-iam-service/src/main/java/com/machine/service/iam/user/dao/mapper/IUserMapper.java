package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.*;
import com.machine.service.iam.user.dao.mapper.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IUserMapper extends BaseMapper<UserEntity> {

    int countNotBindOrganization(@Param("inputDto") DataUserNotBindOrganizationInputDto inputDto);

    List<String> listNotBindOrganization(@Param("inputDto") DataUserNotBindOrganizationInputDto inputDto);

    List<UserEntity> listByOffset(@Param("inputDto") IamUserQueryListOffsetInputDto inputDto);

    Page<UserEntity> page(@Param("inputDto") IamUserQueryPageInputDto inputDto,
                          IPage<UserEntity> page);

    Page<UserEntity> pageCompany(@Param("inputDto") IamCompanyUserQueryPageInputDto inputDto,
                                 IPage<UserEntity> page);

    Page<UserEntity> pageShop(@Param("inputDto") IamShopUserQueryPageInputDto inputDto,
                              IPage<UserEntity> page);

    Page<UserEntity> pageSupplier(@Param("inputDto") IamSupplierUserQueryPageInputDto inputDto,
                                  IPage<UserEntity> page);

}
