package com.machine.service.iam.biam.log.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogAvailableOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.iam.biam.log.dao.IBIamUserLoginLogDao;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamUserLoginLogEntity;
import com.machine.service.iam.biam.log.service.IBIamUserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BIamUserLoginLogServiceImpl implements IBIamUserLoginLogService {

    @Autowired
    private IBIamUserLoginLogDao userLoginLogDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(BIamUserLoginLogCreateInputDto inputDto) {
        BIamUserLoginLogEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), BIamUserLoginLogEntity.class);
        return userLoginLogDao.insert(insertEntity);
    }

    @Override
    public BIamUserLoginLogDetailOutputDto detail(IdRequest request) {
        BIamUserLoginLogEntity entity = userLoginLogDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamUserLoginLogDetailOutputDto.class);
    }

    @Override
    public BIamUserLoginLogDetailOutputDto getLoginSuccessByUserId(String userId) {
        BIamUserLoginLogEntity entity = userLoginLogDao.getLoginSuccessByUserId(userId);
        if(null==entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamUserLoginLogDetailOutputDto.class);
    }

    @Override
    public BIamUserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(String accessTokenId) {
        BIamUserLoginLogEntity entity = userLoginLogDao.getLoginSuccessByAccessTokenId(accessTokenId);
        if(null==entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamUserLoginLogDetailOutputDto.class);
    }

    @Override
    public List<BIamUserLoginLogAvailableOutputDto> selectAvailableToken(BIamUserLoginLogQueryAvailableInputDto inputDto) {
        List<BIamUserLoginLogEntity> entityList = userLoginLogDao.selectAvailableToken(inputDto);
        if(CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamUserLoginLogAvailableOutputDto.class);
    }

    @Override
    public Page<BIamUserLoginLogListOutputDto> page(BIamUserLoginLogQueryPageInputDto inputDto) {
        Page<BIamUserLoginLogEntity> page = userLoginLogDao.page(inputDto);
        Page<BIamUserLoginLogListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        if(CollectionUtil.isEmpty(page.getRecords())) {
           return pageResult;
        }
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), BIamUserLoginLogListOutputDto.class));
        return pageResult;
    }
}
