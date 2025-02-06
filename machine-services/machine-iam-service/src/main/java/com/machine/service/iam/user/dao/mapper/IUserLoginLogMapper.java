package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.service.iam.user.dao.mapper.entity.UserLoginLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IUserLoginLogMapper extends BaseMapper<UserLoginLogEntity> {

    UserLoginLogEntity getLoginSuccessByUserId(@Param("userId") String userId);

    UserLoginLogEntity getLoginSuccessByAccessTokenId(@Param("accessTokenId") String accessTokenId);

    List<UserLoginLogEntity> selectAvailableToken(@Param("inputDto") IamUserLoginLogQueryAvailableInputDto inputDto);

    Page<UserLoginLogEntity> page(@Param("inputDto") IamUserLoginLogQueryPageInputDto inputDto,
                                            IPage<UserLoginLogEntity> page);

}
