package com.machine.service.iam.biam.log.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogDeleteInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserAccessLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserAccessLogListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.iam.biam.log.dao.IBIamUserAccessLogDao;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamUserAccessLogEntity;
import com.machine.service.iam.biam.log.service.IBIamUserAccessLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BIamUserAccessLogServiceImpl implements IBIamUserAccessLogService {

    @Autowired
    private IBIamUserAccessLogDao userAccessLogDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(BIamUserAccessLogCreateInputDto inputDto) {
        BIamUserAccessLogEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), BIamUserAccessLogEntity.class);
        return userAccessLogDao.insert(insertEntity);
    }

    @Override
    public BIamUserAccessLogDetailOutputDto detail(IdRequest request) {
        BIamUserAccessLogEntity entity = userAccessLogDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamUserAccessLogDetailOutputDto.class);
    }

    @Override
    public Page<BIamUserAccessLogListOutputDto> page(BIamUserAccessLogQueryPageInputDto inputDto) {
        Page<BIamUserAccessLogEntity> page = userAccessLogDao.page(inputDto);
        Page<BIamUserAccessLogListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return pageResult;
        }
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), BIamUserAccessLogListOutputDto.class));
        return pageResult;
    }

    @Override
    public int deleteByCreateTimeBefore(BIamUserAccessLogDeleteInputDto inputDto) {
        return userAccessLogDao.deleteByCreateTimeBefore(inputDto.getBeforeCreateTime());
    }
}
