package com.machine.service.iam.biam.log.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogQueryPageInputDto;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamUserAccessLogEntity;

public interface IBIamUserAccessLogDao {

    String insert(BIamUserAccessLogEntity insertEntity);

    int deleteByCreateTimeBefore(Long beforeCreateTime);

    BIamUserAccessLogEntity getById(String id);

    Page<BIamUserAccessLogEntity> page(BIamUserAccessLogQueryPageInputDto inputDto);

}
