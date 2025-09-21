package com.machine.service.iam.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.auth.dto.IamAuthTokenAddDto;
import com.machine.client.iam.auth.dto.IamOAuth2RegisteredClientDto;
import com.machine.client.iam.auth.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.dto.data.WebHookInfoDto;
import com.machine.service.iam.auth.dao.IIamOauth2RegisteredClientDao;
import com.machine.service.iam.auth.dao.mapper.entity.IamOauth2RegisteredClientEntity;
import com.machine.service.iam.auth.service.IIamOauth2RegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class IamOauth2RegisteredClientServiceImpl implements IIamOauth2RegisteredClientService {

    @Autowired
    private IIamOauth2RegisteredClientDao oauth2RegisteredClientDao;

    @Override
    public int add(IamAuthTokenAddDto dto) {
        return oauth2RegisteredClientDao.add(dto);
    }

    @Override
    public int save(IamOAuth2RegisteredClientDto dto) {
        IamOauth2RegisteredClientEntity entity = new IamOauth2RegisteredClientEntity();
        BeanUtil.copyProperties(dto, entity);
        saveAdminInfo(entity);
        return oauth2RegisteredClientDao.save(entity);
    }

    private static void saveAdminInfo(IamOauth2RegisteredClientEntity entity) {
        entity.setUpdateBy("admin");
        entity.setUpdateTime(System.currentTimeMillis());
        entity.setCreateBy("admin");
        entity.setCreateTime(System.currentTimeMillis());
    }

    @Override
    public int update(IamOAuth2RegisteredClientDto dto) {
        IamOauth2RegisteredClientEntity entity = new IamOauth2RegisteredClientEntity();
        BeanUtil.copyProperties(dto, entity);
        saveAdminInfo(entity);
        return oauth2RegisteredClientDao.update(entity);
    }

    @Override
    public List<String> allEnableClientId() {
        return oauth2RegisteredClientDao.allClientId(StatusEnum.ENABLE);
    }

    @Override
    public IamOAuth2RegisteredClientDetailOutputDto findById(String id) {
        IamOauth2RegisteredClientEntity entity = oauth2RegisteredClientDao.findById(id);
        if (Objects.isNull(entity)) {
            return null;
        }
        IamOAuth2RegisteredClientDetailOutputDto dto = new IamOAuth2RegisteredClientDetailOutputDto();
        BeanUtil.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public IamOAuth2RegisteredClientDetailOutputDto findByClientId(String clientId) {
        IamOauth2RegisteredClientEntity entity = oauth2RegisteredClientDao.findByClientId(clientId);
        if (entity == null) {
            return null;
        }
        IamOAuth2RegisteredClientDetailOutputDto outputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamOAuth2RegisteredClientDetailOutputDto.class, true);
        if (StrUtil.isNotBlank(entity.getWebHookInfo())) {
            outputDto.setWebHookInfo(JSONUtil.toBean(JSONUtil.toJsonStr(entity.getWebHookInfo()), WebHookInfoDto.class));
        }
        return outputDto;
    }


}
