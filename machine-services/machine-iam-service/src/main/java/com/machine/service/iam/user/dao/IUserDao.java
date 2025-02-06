package com.machine.service.iam.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.*;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.iam.user.dao.mapper.entity.UserEntity;

import java.util.List;
import java.util.Set;

public interface IUserDao {

    String insert(UserEntity insertEntity);

    int updateStatus(String userId,
                     StatusEnum status);

    int updatePhone(String userId,
                    String phone);

    int updatePassword(String userId,
                       String password);

    int update(UserEntity entity);

    int countNotBindOrganization(DataUserNotBindOrganizationInputDto inputDto);

    UserEntity getById(String userId);

    UserEntity getByUserName(String userName);

    UserEntity getByCode(String code);

    UserEntity getByPhone(String phone);

    List<String> listNotBindOrganization(DataUserNotBindOrganizationInputDto inputDto);

    List<UserEntity> selectByIdSet(Set<String> idSet);

    List<UserEntity> listByOffset(IamUserQueryListOffsetInputDto inputDto);

    Page<UserEntity> page(IamUserQueryPageInputDto inputDto);

    Page<UserEntity> pageCompany(IamCompanyUserQueryPageInputDto inputDto);

    Page<UserEntity> pageShop(IamShopUserQueryPageInputDto inputDto);

    Page<UserEntity> pageSupplier(IamSupplierUserQueryPageInputDto inputDto);

}
