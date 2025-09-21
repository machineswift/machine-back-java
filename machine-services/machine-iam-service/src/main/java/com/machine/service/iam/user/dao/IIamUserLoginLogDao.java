package com.machine.service.iam.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.service.iam.user.dao.mapper.entity.IamUserLoginLogEntity;

import java.util.List;

public interface IIamUserLoginLogDao {

    String insert(IamUserLoginLogEntity insertEntity);

    IamUserLoginLogEntity getById(String id);

    IamUserLoginLogEntity getLoginSuccessByUserId(String userId);

    IamUserLoginLogEntity getLoginSuccessByAccessTokenId(String accessTokenId);

    List<IamUserLoginLogEntity> selectAvailableToken(IamUserLoginLogQueryAvailableInputDto inputDto);

    Page<IamUserLoginLogEntity> page(IamUserLoginLogQueryPageInputDto inputDto);

}
