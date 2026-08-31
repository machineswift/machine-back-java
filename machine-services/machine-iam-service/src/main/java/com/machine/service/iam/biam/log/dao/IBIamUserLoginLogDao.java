package com.machine.service.iam.biam.log.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryPageInputDto;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamUserLoginLogEntity;

import java.util.List;

public interface IBIamUserLoginLogDao {

    String insert(BIamUserLoginLogEntity insertEntity);

    BIamUserLoginLogEntity getById(String id);

    BIamUserLoginLogEntity getLoginSuccessByUserId(String userId);

    BIamUserLoginLogEntity getLoginSuccessByAccessTokenId(String accessTokenId);

    List<BIamUserLoginLogEntity> selectAvailableToken(BIamUserLoginLogQueryAvailableInputDto inputDto);

    Page<BIamUserLoginLogEntity> page(BIamUserLoginLogQueryPageInputDto inputDto);

}
