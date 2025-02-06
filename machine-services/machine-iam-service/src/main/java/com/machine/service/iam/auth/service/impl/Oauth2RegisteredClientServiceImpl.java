package com.machine.service.iam.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.auth.dto.AuthTokenAddDto;
import com.machine.client.iam.auth.dto.OAuth2RegisteredClientDto;
import com.machine.client.iam.auth.dto.output.OAuth2RegisteredClientDetailOutputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.dto.data.WebHookInfoDto;
import com.machine.service.iam.auth.dao.IOauth2RegisteredClientDao;
import com.machine.service.iam.auth.dao.mapper.entity.Oauth2RegisteredClientEntity;
import com.machine.service.iam.auth.service.IOauth2RegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class Oauth2RegisteredClientServiceImpl implements IOauth2RegisteredClientService {

    @Autowired
    private IOauth2RegisteredClientDao oauth2RegisteredClientDao;

    @Override
    public int add(AuthTokenAddDto dto) {
        return oauth2RegisteredClientDao.add(dto);
    }

    @Override
    public int save(OAuth2RegisteredClientDto dto) {
        Oauth2RegisteredClientEntity entity = new Oauth2RegisteredClientEntity();
        BeanUtil.copyProperties(dto, entity);
        saveAdminInfo(entity);
        return oauth2RegisteredClientDao.save(entity);
    }

    private static void saveAdminInfo(Oauth2RegisteredClientEntity entity) {
        entity.setUpdateBy("admin");
        entity.setUpdateTime(System.currentTimeMillis());
        entity.setCreateBy("admin");
        entity.setCreateTime(System.currentTimeMillis());
    }

    @Override
    public int update(OAuth2RegisteredClientDto dto) {
        Oauth2RegisteredClientEntity entity = new Oauth2RegisteredClientEntity();
        BeanUtil.copyProperties(dto, entity);
        saveAdminInfo(entity);
        return oauth2RegisteredClientDao.update(entity);
    }

    @Override
    public List<String> allEnableClientId() {
        return oauth2RegisteredClientDao.allClientId(StatusEnum.ENABLE);
    }

    @Override
    public OAuth2RegisteredClientDetailOutputDto findById(String id) {
        Oauth2RegisteredClientEntity entity = oauth2RegisteredClientDao.findById(id);
        if (Objects.isNull(entity)) {
            return null;
        }
        OAuth2RegisteredClientDetailOutputDto dto = new OAuth2RegisteredClientDetailOutputDto();
        BeanUtil.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public OAuth2RegisteredClientDetailOutputDto findByClientId(String clientId) {
        Oauth2RegisteredClientEntity entity = oauth2RegisteredClientDao.findByClientId(clientId);
        if (entity == null) {
            return null;
        }
        OAuth2RegisteredClientDetailOutputDto outputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(entity), OAuth2RegisteredClientDetailOutputDto.class, true);
        if (StrUtil.isNotBlank(entity.getWebHookInfo())) {
            outputDto.setWebHookInfo(JSONUtil.toBean(JSONUtil.toJsonStr(entity.getWebHookInfo()), WebHookInfoDto.class));
        }
        return outputDto;
    }


}
