package com.machine.service.iam.biam.log.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogQueryPageInputDto;
import com.machine.service.iam.biam.log.dao.IBIamOperationLogDao;
import com.machine.service.iam.biam.log.dao.mapper.BIamOperationLogMapper;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamOperationLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BIamOperationLogDaoImpl implements IBIamOperationLogDao {

    @Autowired
    private BIamOperationLogMapper operationLogMapper;

    @Override
    public String insert(BIamOperationLogEntity insertEntity) {
        operationLogMapper.insert(insertEntity);
        return insertEntity.getId();
    }

    @Override
    public Page<BIamOperationLogEntity> page(BIamOperationLogQueryPageInputDto inputDto) {
        IPage<BIamOperationLogEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return operationLogMapper.selectPage(inputDto, page);
    }

    @Override
    public BIamOperationLogEntity getById(String id) {
        return operationLogMapper.selectById(id);
    }
}
