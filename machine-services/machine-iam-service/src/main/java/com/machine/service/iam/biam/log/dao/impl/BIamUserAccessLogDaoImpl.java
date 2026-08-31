package com.machine.service.iam.biam.log.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogQueryPageInputDto;
import com.machine.service.iam.biam.log.dao.IBIamUserAccessLogDao;
import com.machine.service.iam.biam.log.dao.mapper.BIamUserAccessLogMapper;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamUserAccessLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BIamUserAccessLogDaoImpl implements IBIamUserAccessLogDao {

    @Autowired
    private BIamUserAccessLogMapper userAccessLogMapper;

    @Override
    public String insert(BIamUserAccessLogEntity insertEntity) {
        userAccessLogMapper.insert(insertEntity);
        return insertEntity.getId();
    }

    @Override
    public int deleteByCreateTimeBefore(Long beforeCreateTime) {
        return userAccessLogMapper.deleteByCreateTimeBefore(beforeCreateTime);
    }

    @Override
    public BIamUserAccessLogEntity getById(String id) {
        return userAccessLogMapper.selectById(id);
    }

    @Override
    public Page<BIamUserAccessLogEntity> page(BIamUserAccessLogQueryPageInputDto inputDto) {
        IPage<BIamUserAccessLogEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return userAccessLogMapper.selectPage(inputDto, page);
    }

}
