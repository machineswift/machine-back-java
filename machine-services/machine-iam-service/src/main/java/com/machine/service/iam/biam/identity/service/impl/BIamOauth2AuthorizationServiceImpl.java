package com.machine.service.iam.biam.identity.service.impl;

import com.machine.client.iam.biam.identity.dto.BIamOAuth2AuthorizationDto;
import com.machine.service.iam.biam.identity.dao.IBIamOauth2AuthorizationDao;
import com.machine.service.iam.biam.identity.dao.mapper.entity.BIamOauth2AuthorizationEntity;
import com.machine.service.iam.biam.identity.service.IBIamOauth2AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
public class BIamOauth2AuthorizationServiceImpl implements IBIamOauth2AuthorizationService {

    @Autowired
    private IBIamOauth2AuthorizationDao authorizationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(BIamOAuth2AuthorizationDto dto) {
        return authorizationDao.save(toEntity(dto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(String id) {
        authorizationDao.remove(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(BIamOAuth2AuthorizationDto dto) {
        return authorizationDao.update(toEntity(dto));
    }

    @Override
    public boolean existsById(String id) {
        return authorizationDao.existsById(id);
    }

    @Override
    public BIamOAuth2AuthorizationDto findById(String id) {
        BIamOauth2AuthorizationEntity entity = authorizationDao.findById(id);
        if (Objects.isNull(entity)) {
            return null;
        }
       return toDto(entity);
    }

    @Override
    public BIamOAuth2AuthorizationDto findByToken(BIamOAuth2AuthorizationDto dto) {
        BIamOauth2AuthorizationEntity entity = authorizationDao.findByToken(toEntity(dto));
        if (Objects.isNull(entity)) {
            return null;
        }
       return toDto(entity);
    }

    private BIamOAuth2AuthorizationDto toDto(BIamOauth2AuthorizationEntity entity) {
        if (entity == null) {
            return null;
        }

        BIamOAuth2AuthorizationDto dto = new BIamOAuth2AuthorizationDto();
        dto.setId(entity.getId());
        dto.setRegisteredClientId(entity.getRegisteredClientId());
        dto.setPrincipalName(entity.getPrincipalName());
        dto.setAuthorizationGrantType(entity.getAuthorizationGrantType());
        dto.setAuthorizedScopes(entity.getAuthorizedScopes());
        dto.setAttributes(entity.getAttributes());
        dto.setState(entity.getState());
        dto.setAuthorizationCodeValue(entity.getAuthorizationCodeValue());
        dto.setAuthorizationCodeIssuedAt(entity.getAuthorizationCodeIssuedAt());
        dto.setAuthorizationCodeExpiresAt(entity.getAuthorizationCodeExpiresAt());
        dto.setAuthorizationCodeMetadata(entity.getAuthorizationCodeMetadata());
        dto.setAccessTokenValue(entity.getAccessTokenValue());
        dto.setAccessTokenIssuedAt(entity.getAccessTokenIssuedAt());
        dto.setAccessTokenExpiresAt(entity.getAccessTokenExpiresAt());
        dto.setAccessTokenMetadata(entity.getAccessTokenMetadata());
        dto.setAccessTokenType(entity.getAccessTokenType());
        dto.setAccessTokenScopes(entity.getAccessTokenScopes());
        dto.setOidcIdTokenValue(entity.getOidcIdTokenValue());
        dto.setOidcIdTokenIssuedAt(entity.getOidcIdTokenIssuedAt());
        dto.setOidcIdTokenExpiresAt(entity.getOidcIdTokenExpiresAt());
        dto.setOidcIdTokenMetadata(entity.getOidcIdTokenMetadata());
        dto.setRefreshTokenValue(entity.getRefreshTokenValue());
        dto.setRefreshTokenIssuedAt(entity.getRefreshTokenIssuedAt());
        dto.setRefreshTokenExpiresAt(entity.getRefreshTokenExpiresAt());
        dto.setRefreshTokenMetadata(entity.getRefreshTokenMetadata());
        dto.setUserCodeValue(entity.getUserCodeValue());
        dto.setUserCodeIssuedAt(entity.getUserCodeIssuedAt());
        dto.setUserCodeExpiresAt(entity.getUserCodeExpiresAt());
        dto.setUserCodeMetadata(entity.getUserCodeMetadata());
        dto.setDeviceCodeValue(entity.getDeviceCodeValue());
        dto.setDeviceCodeIssuedAt(entity.getDeviceCodeIssuedAt());
        dto.setDeviceCodeExpiresAt(entity.getDeviceCodeExpiresAt());
        dto.setDeviceCodeMetadata(entity.getDeviceCodeMetadata());
        return dto;
    }

    private BIamOauth2AuthorizationEntity toEntity(BIamOAuth2AuthorizationDto dto) {
        if (dto == null) {
            return null;
        }

        BIamOauth2AuthorizationEntity entity = new BIamOauth2AuthorizationEntity();
        entity.setId(dto.getId());
        entity.setRegisteredClientId(dto.getRegisteredClientId());
        entity.setPrincipalName(dto.getPrincipalName());
        entity.setAuthorizationGrantType(dto.getAuthorizationGrantType());
        entity.setAuthorizedScopes(dto.getAuthorizedScopes());
        entity.setAttributes(dto.getAttributes());
        entity.setState(dto.getState());
        entity.setAuthorizationCodeValue(dto.getAuthorizationCodeValue());
        entity.setAuthorizationCodeIssuedAt(dto.getAuthorizationCodeIssuedAt());
        entity.setAuthorizationCodeExpiresAt(dto.getAuthorizationCodeExpiresAt());
        entity.setAuthorizationCodeMetadata(dto.getAuthorizationCodeMetadata());
        entity.setAccessTokenValue(dto.getAccessTokenValue());
        entity.setAccessTokenIssuedAt(dto.getAccessTokenIssuedAt());
        entity.setAccessTokenExpiresAt(dto.getAccessTokenExpiresAt());
        entity.setAccessTokenMetadata(dto.getAccessTokenMetadata());
        entity.setAccessTokenType(dto.getAccessTokenType());
        entity.setAccessTokenScopes(dto.getAccessTokenScopes());
        entity.setOidcIdTokenValue(dto.getOidcIdTokenValue());
        entity.setOidcIdTokenIssuedAt(dto.getOidcIdTokenIssuedAt());
        entity.setOidcIdTokenExpiresAt(dto.getOidcIdTokenExpiresAt());
        entity.setOidcIdTokenMetadata(dto.getOidcIdTokenMetadata());
        entity.setRefreshTokenValue(dto.getRefreshTokenValue());
        entity.setRefreshTokenIssuedAt(dto.getRefreshTokenIssuedAt());
        entity.setRefreshTokenExpiresAt(dto.getRefreshTokenExpiresAt());
        entity.setRefreshTokenMetadata(dto.getRefreshTokenMetadata());
        entity.setUserCodeValue(dto.getUserCodeValue());
        entity.setUserCodeIssuedAt(dto.getUserCodeIssuedAt());
        entity.setUserCodeExpiresAt(dto.getUserCodeExpiresAt());
        entity.setUserCodeMetadata(dto.getUserCodeMetadata());
        entity.setDeviceCodeValue(dto.getDeviceCodeValue());
        entity.setDeviceCodeIssuedAt(dto.getDeviceCodeIssuedAt());
        entity.setDeviceCodeExpiresAt(dto.getDeviceCodeExpiresAt());
        entity.setDeviceCodeMetadata(dto.getDeviceCodeMetadata());
        return entity;
    }
}
