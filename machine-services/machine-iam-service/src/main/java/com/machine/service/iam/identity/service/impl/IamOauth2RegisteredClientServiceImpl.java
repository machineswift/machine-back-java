package com.machine.service.iam.identity.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.identity.dto.input.*;
import com.machine.client.iam.identity.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import com.machine.client.iam.identity.dto.output.IamOAuth2RegisteredClientListOutputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.exception.iam.IamBusinessException;
import com.machine.sdk.base.model.dto.iam.identity.IamAuth2RegisteredClientSettingDto;
import com.machine.sdk.base.model.dto.iam.identity.IamAuth2RegisteredTokenSettingDto;
import com.machine.sdk.base.model.dto.iam.identity.IamOAuth2RegisteredClientDto;
import com.machine.sdk.base.tool.UUIDv7;
import com.machine.service.iam.identity.dao.IIamOauth2RegisteredClientDao;
import com.machine.service.iam.identity.dao.mapper.entiry.IamOauth2RegisteredClientEntity;
import com.machine.service.iam.identity.service.IIamOauth2RegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class IamOauth2RegisteredClientServiceImpl implements IIamOauth2RegisteredClientService {

    @Autowired
    private IIamOauth2RegisteredClientDao oauth2RegisteredClientDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(IamOAuth2RegisteredClientCreateInputDto inputDto) {
        IamOauth2RegisteredClientEntity entityByClientName = oauth2RegisteredClientDao.findByClientName(inputDto.getClientName());
        if (Objects.nonNull(entityByClientName)) {
            log.error("认证中心客户端名称已存在，clientName={}", inputDto.getClientName());
            throw new IamBusinessException("iam.identity.service.create.clientNameAlreadyExists", "认证中心客户端名称已存在");
        }

        IamOauth2RegisteredClientEntity entity = new IamOauth2RegisteredClientEntity();
        entity.setStatus(StatusEnum.ENABLE);
        entity.setClientId(UUIDv7.generateWithoutDashes());
        entity.setClientName(inputDto.getClientName());
        entity.setClientSecret(inputDto.getClientSecret());

        entity.setClientIdIssuedAt(System.currentTimeMillis());
        entity.setClientSecretExpiresAt(Long.MAX_VALUE);

        entity.setClientAuthenticationMethods(JSONUtil.toJsonStr(List.of(
                ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue())));
        entity.setAuthorizationGrantTypes(JSONUtil.toJsonStr(List.of(
                AuthorizationGrantType.CLIENT_CREDENTIALS.getValue(),
                AuthorizationGrantType.REFRESH_TOKEN.getValue())));

        entity.setRedirectUris(JSONUtil.toJsonStr(List.of()));
        entity.setPostLogoutRedirectUris(JSONUtil.toJsonStr(List.of()));

        entity.setScopes(JSONUtil.toJsonStr(inputDto.getScopes()));

        entity.setClientSettings(JSONUtil.toJsonStr(new IamAuth2RegisteredClientSettingDto()));
        entity.setTokenSettings(JSONUtil.toJsonStr(new IamAuth2RegisteredTokenSettingDto()));
        return oauth2RegisteredClientDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(IamOAuth2RegisteredClientUpdateInputDto inputDto) {
        IamOauth2RegisteredClientEntity entityById = oauth2RegisteredClientDao.findById(inputDto.getId());
        if (Objects.nonNull(entityById)) {
            throw new IamBusinessException("iam.identity.service.update.clientNotExists", "认证中心客户端不存在");
        }

        //验证用户名是否存在
        IamOauth2RegisteredClientEntity entityByClientName = oauth2RegisteredClientDao.findByClientName(inputDto.getClientName());
        if (null != entityByClientName && !entityByClientName.getId().equals(inputDto.getId())) {
            throw new IamBusinessException("iam.identity.service.update.clientNameAlreadyExists", "认证中心客户端名称已经存在");
        }

        IamOauth2RegisteredClientEntity entity = new IamOauth2RegisteredClientEntity();
        entity.setId(inputDto.getId());
        entity.setClientName(inputDto.getClientName());
        if (StrUtil.isNotBlank(inputDto.getClientSecret())) {
            entity.setClientSecret(inputDto.getClientSecret());
        }
        entity.setClientSecret(inputDto.getClientSecret());
        entity.setScopes(JSONUtil.toJsonStr(inputDto.getScopes()));
        return oauth2RegisteredClientDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(IamOAuth2RegisteredClientUpdateStatusInputDto inputDto) {
        IamOauth2RegisteredClientEntity entityById = oauth2RegisteredClientDao.findById(inputDto.getId());
        if (null == entityById || inputDto.getStatus() == entityById.getStatus()) {
            return 0;
        }
        return oauth2RegisteredClientDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(String id) {
        return oauth2RegisteredClientDao.deleteById(id);
    }


    @Override
    public List<String> allEnableClientId() {
        return oauth2RegisteredClientDao.allClientId(StatusEnum.ENABLE);
    }

    @Override
    public IamOAuth2RegisteredClientDto findById(String id) {
        IamOauth2RegisteredClientEntity entity = oauth2RegisteredClientDao.findById(id);
        if (Objects.isNull(entity)) {
            return null;
        }
        return convertEntity2ClientDto(entity);
    }

    @Override
    public IamOAuth2RegisteredClientDto findByClientId(String clientId) {
        IamOauth2RegisteredClientEntity entity = oauth2RegisteredClientDao.findByClientId(clientId);
        if (entity == null) {
            return null;
        }
        return convertEntity2ClientDto(entity);
    }

    @Override
    public IamOAuth2RegisteredClientDetailOutputDto detail(String id) {
        IamOauth2RegisteredClientEntity entity = oauth2RegisteredClientDao.findById(id);
        if (Objects.isNull(entity)) {
            return null;
        }
        IamOAuth2RegisteredClientDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamOAuth2RegisteredClientDetailOutputDto.class, true);
        if (StrUtil.isNotBlank(entity.getScopes())) {
            outputDto.setScopes(new HashSet<>(JSONUtil.toList(entity.getScopes(), String.class)));
        }
        return outputDto;
    }

    @Override
    public Page<IamOAuth2RegisteredClientListOutputDto> selectPage(IamOAuth2RegisteredClientPageQueryInputDto inputDto) {
        Page<IamOauth2RegisteredClientEntity> page = oauth2RegisteredClientDao.selectPage(inputDto);
        Page<IamOAuth2RegisteredClientListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), IamOAuth2RegisteredClientListOutputDto.class));
        return pageResult;
    }


    private IamOAuth2RegisteredClientDto convertEntity2ClientDto(IamOauth2RegisteredClientEntity entity) {
        IamOAuth2RegisteredClientDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamOAuth2RegisteredClientDto.class, true);

        if (StrUtil.isNotBlank(entity.getClientAuthenticationMethods())) {
            outputDto.setClientAuthenticationMethods(JSONUtil.toList(entity.getClientAuthenticationMethods(), String.class));
        }

        if (StrUtil.isNotBlank(entity.getAuthorizationGrantTypes())) {
            outputDto.setAuthorizationGrantTypes(JSONUtil.toList(entity.getAuthorizationGrantTypes(), String.class));
        }

        if (StrUtil.isNotBlank(entity.getRedirectUris())) {
            outputDto.setRedirectUris(JSONUtil.toList(entity.getRedirectUris(), String.class));
        }

        if (StrUtil.isNotBlank(entity.getPostLogoutRedirectUris())) {
            outputDto.setPostLogoutRedirectUris(JSONUtil.toList(entity.getPostLogoutRedirectUris(), String.class));
        }

        if (StrUtil.isNotBlank(entity.getScopes())) {
            outputDto.setScopes(new HashSet<>(JSONUtil.toList(entity.getScopes(), String.class)));
        }

        if (StrUtil.isNotBlank(entity.getClientSettings())) {
            outputDto.setClientSettings(JSONUtil.toBean(entity.getClientSettings(), IamAuth2RegisteredClientSettingDto.class));
        }

        if (StrUtil.isNotBlank(entity.getTokenSettings())) {
            outputDto.setTokenSettings(JSONUtil.toBean(entity.getTokenSettings(), IamAuth2RegisteredTokenSettingDto.class));
        }

        return outputDto;
    }
}
