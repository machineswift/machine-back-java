package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.userbk.dto.input.IamCompanyUserQueryPageInputDto;
import com.machine.client.iam.user.dto.input.IamUserExportInputDto;
import com.machine.client.iam.userbk.dto.input.IamShopUserQueryPageInputDto;
import com.machine.client.iam.userbk.dto.input.IamSupplierUserQueryPageInputDto;
import com.machine.sdk.common.envm.iam.auth.IamAuth2SourceEnum;
import com.machine.service.iam.user.dao.mapper.entity.IamUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface IamUserMapper extends BaseMapper<IamUserEntity> {

    int countNotBindOrganization(@Param("inputDto") IamDataUserNotBindOrganizationInputDto inputDto);

    IamUserEntity getByThirdPartyUuid(@Param("source") IamAuth2SourceEnum source,
                                      @Param("thirdPartyUuid") String thirdPartyUuid);

    List<String> listIdByShopIdSet(@Param("inputDto") Set<String> shopIdSet);

    List<String> listNotBindOrganization(@Param("inputDto") IamDataUserNotBindOrganizationInputDto inputDto);

    List<IamUserEntity> listByOffset(@Param("inputDto") IamUserQueryListOffsetInputDto inputDto);

    Page<IamUserEntity> selectPage(@Param("inputDto") IamUserQueryPageInputDto inputDto,
                                   IPage<IamUserEntity> page);

    Page<IamUserEntity> pageCompany(@Param("inputDto") IamCompanyUserQueryPageInputDto inputDto,
                                    IPage<IamUserEntity> page);

    Page<IamUserEntity> pageShop(@Param("inputDto") IamShopUserQueryPageInputDto inputDto,
                                 IPage<IamUserEntity> page);

    Page<IamUserEntity> pageSupplier(@Param("inputDto") IamSupplierUserQueryPageInputDto inputDto,
                                     IPage<IamUserEntity> page);

    List<IamUserEntity> listShopUser4Export(@Param("inputDto") IamUserExportInputDto inputDto);

}
