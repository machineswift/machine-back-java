package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogAvailableOutputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.iam.user.dao.IIamUserLoginLogDao;
import com.machine.service.iam.user.dao.mapper.entity.IamUserLoginLogEntity;
import com.machine.service.iam.user.service.IIamUserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class IamUserLoginLogServiceImpl implements IIamUserLoginLogService {

    @Autowired
    private IIamUserLoginLogDao userLoginLogDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(IamUserLoginLogCreateInputDto inputDto) {
        IamUserLoginLogEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), IamUserLoginLogEntity.class);
        return userLoginLogDao.insert(insertEntity);
    }

    @Override
    public IamUserLoginLogDetailOutputDto detail(IdRequest request) {
        IamUserLoginLogEntity entity = userLoginLogDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamUserLoginLogDetailOutputDto.class);
    }

    @Override
    public IamUserLoginLogDetailOutputDto getLoginSuccessByUserId(String userId) {
        IamUserLoginLogEntity entity = userLoginLogDao.getLoginSuccessByUserId(userId);
        if(null==entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamUserLoginLogDetailOutputDto.class);
    }

    @Override
    public IamUserLoginLogDetailOutputDto getLoginSuccessByAccessTokenId(String accessTokenId) {
        IamUserLoginLogEntity entity = userLoginLogDao.getLoginSuccessByAccessTokenId(accessTokenId);
        if(null==entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamUserLoginLogDetailOutputDto.class);
    }

    @Override
    public List<IamUserLoginLogAvailableOutputDto> selectAvailableToken(IamUserLoginLogQueryAvailableInputDto inputDto) {
        List<IamUserLoginLogEntity> entityList = userLoginLogDao.selectAvailableToken(inputDto);
        if(CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserLoginLogAvailableOutputDto.class);
    }

    @Override
    public Page<IamUserLoginLogListOutputDto> page(IamUserLoginLogQueryPageInputDto inputDto) {
        Page<IamUserLoginLogEntity> page = userLoginLogDao.page(inputDto);
        Page<IamUserLoginLogListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        if(CollectionUtil.isEmpty(page.getRecords())) {
           return pageResult;
        }
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), IamUserLoginLogListOutputDto.class));
        return pageResult;
    }
}
