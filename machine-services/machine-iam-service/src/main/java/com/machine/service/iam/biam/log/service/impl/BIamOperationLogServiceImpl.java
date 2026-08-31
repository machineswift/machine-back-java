package com.machine.service.iam.biam.log.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamOperationLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamOperationLogListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.iam.biam.log.dao.IBIamOperationLogDao;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamOperationLogEntity;
import com.machine.service.iam.biam.log.service.IBIamOperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BIamOperationLogServiceImpl implements IBIamOperationLogService {

    @Autowired
    private IBIamOperationLogDao operationLogDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(BIamOperationLogCreateInputDto inputDto) {
        BIamOperationLogEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), BIamOperationLogEntity.class);
        return operationLogDao.insert(insertEntity);
    }

    @Override
    public BIamOperationLogDetailOutputDto detail(IdRequest request) {
        BIamOperationLogEntity entity = operationLogDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamOperationLogDetailOutputDto.class);
    }

    @Override
    public Page<BIamOperationLogListOutputDto> page(BIamOperationLogQueryPageInputDto inputDto) {
        Page<BIamOperationLogEntity> page = operationLogDao.page(inputDto);
        Page<BIamOperationLogListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return pageResult;
        }
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), BIamOperationLogListOutputDto.class));
        return pageResult;
    }
}
