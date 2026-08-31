package com.machine.service.iam.biam.log.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogQueryPageInputDto;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamOperationLogEntity;

public interface IBIamOperationLogDao {

    /**
     * 新增操作日志
     */
    String insert(BIamOperationLogEntity insertEntity);

    /**
     * 分页查询
     */
    Page<BIamOperationLogEntity> page(BIamOperationLogQueryPageInputDto inputDto);

    /**
     * 根据ID查询
     */
    BIamOperationLogEntity getById(String id);
}
