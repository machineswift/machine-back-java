package com.machine.service.iam.biam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.user.dto.input.BIamDataUserNotBindOrganizationInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserQueryListOffsetInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserQueryPageInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamCompanyUserQueryPageInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserExportInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamShopUserQueryPageInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamSupplierUserQueryPageInputDto;
import com.machine.sdk.base.envm.biam.auth.BIamAuth2SourceEnum;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface BIamUserMapper extends BaseMapper<BIamUserEntity> {

    int countNotBindOrganization(@Param("inputDto") BIamDataUserNotBindOrganizationInputDto inputDto);

    BIamUserEntity getByThirdPartyUuid(@Param("source") BIamAuth2SourceEnum source,
                                       @Param("thirdPartyUuid") String thirdPartyUuid);

    List<String> listIdByShopIdSet(@Param("shopIdSet") Set<String> shopIdSet);

    List<String> listNotBindOrganization(@Param("inputDto") BIamDataUserNotBindOrganizationInputDto inputDto);

    List<BIamUserEntity> listByOffset(@Param("inputDto") BIamUserQueryListOffsetInputDto inputDto);

    Page<BIamUserEntity> selectPage(@Param("inputDto") BIamUserQueryPageInputDto inputDto,
                                    IPage<BIamUserEntity> page);

    Page<BIamUserEntity> pageCompany(@Param("inputDto") IamCompanyUserQueryPageInputDto inputDto,
                                     IPage<BIamUserEntity> page);

    Page<BIamUserEntity> pageShop(@Param("inputDto") IamShopUserQueryPageInputDto inputDto,
                                  IPage<BIamUserEntity> page);

    Page<BIamUserEntity> pageSupplier(@Param("inputDto") IamSupplierUserQueryPageInputDto inputDto,
                                      IPage<BIamUserEntity> page);

    List<BIamUserEntity> listShopUser4Export(@Param("inputDto") BIamUserExportInputDto inputDto);

}
