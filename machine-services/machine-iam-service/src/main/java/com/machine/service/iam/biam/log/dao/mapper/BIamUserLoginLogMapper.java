package com.machine.service.iam.biam.log.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryPageInputDto;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamUserLoginLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BIamUserLoginLogMapper extends BaseMapper<BIamUserLoginLogEntity> {

    BIamUserLoginLogEntity getLoginSuccessByUserId(@Param("userId") String userId);

    BIamUserLoginLogEntity getLoginSuccessByAccessTokenId(@Param("accessTokenId") String accessTokenId);

    List<BIamUserLoginLogEntity> selectAvailableToken(@Param("inputDto") BIamUserLoginLogQueryAvailableInputDto inputDto);

    Page<BIamUserLoginLogEntity> selectPage(@Param("inputDto") BIamUserLoginLogQueryPageInputDto inputDto,
                                            IPage<BIamUserLoginLogEntity> page);

}
