package com.machine.service.iam.biam.identity.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.identity.dto.input.*;
import com.machine.client.iam.biam.identity.dto.output.BIamOAuth2RegisteredClientDetailOutputDto;
import com.machine.client.iam.biam.identity.dto.output.BIamOAuth2RegisteredClientListOutputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.biam.identity.BIamAuthorizationGrantTypeEnum;
import com.machine.sdk.base.envm.biam.role.BIamRoleTypeEnum;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.dto.biam.identity.BIamAuth2RegisteredClientSettingDto;
import com.machine.sdk.base.model.dto.biam.identity.BIamAuth2RegisteredTokenSettingDto;
import com.machine.sdk.base.model.dto.biam.identity.BIamOAuth2RegisteredClientDto;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.UUIDv7;
import com.machine.service.iam.biam.identity.dao.IBIamOauth2RegisteredClientDao;
import com.machine.service.iam.biam.identity.dao.mapper.entity.BIamOauth2RegisteredClientEntity;
import com.machine.service.iam.biam.identity.service.IBIamOauth2RegisteredClientService;
import com.machine.service.iam.biam.permission.dao.IBIamPermissionDao;
import com.machine.service.iam.biam.role.dao.IBIamRoleDao;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRoleEntity;
import com.machine.starter.redis.cache.biam.RedisBIamPermissionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BIamOauth2RegisteredClientServiceImpl implements IBIamOauth2RegisteredClientService {

    @Autowired
    private RedisBIamPermissionCache permissionCache;

    @Autowired
    private IBIamRoleDao roleDao;

    @Autowired
    private IBIamPermissionDao permissionDao;

    @Autowired
    private IBIamOauth2RegisteredClientDao oauth2RegisteredClientDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(BIamOAuth2RegisteredClientCreateInputDto inputDto) {
        BIamOauth2RegisteredClientEntity entityByClientName = oauth2RegisteredClientDao.findByClientName(inputDto.getClientName());
        if (Objects.nonNull(entityByClientName)) {
            log.error("认证中心客户端名称已存在，clientName={}", inputDto.getClientName());
            throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.create.clientNameAlreadyExists", "认证中心客户端名称已存在");
        }

        // 校验参数（授权码模式）
        if (BIamAuthorizationGrantTypeEnum.AUTHORIZATION_CODE == inputDto.getAuthorizationGrantType()) {
            if (CollectionUtil.isEmpty(inputDto.getRedirectUris())) {
                throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.create.emptyRedirectUris", "重定向URI不能为空");
            }

            if (CollectionUtil.isEmpty(inputDto.getPostLogoutRedirectUris())) {
                throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.create.emptyPostLogoutRedirectUris", "登出后重定向URI不能为空");
            }
        }

        // 校验参数（客户端模式）
        if (BIamAuthorizationGrantTypeEnum.CLIENT_CREDENTIALS == inputDto.getAuthorizationGrantType()) {
            List<BIamRoleEntity> roleEntityList = roleDao.selectByIdSet(inputDto.getScopes());
            if (inputDto.getScopes().size() != roleEntityList.size()) {
                throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.create.scopesNotExists", "认证中心作用域不存在");
            }

            for (BIamRoleEntity roleEntity : roleEntityList) {
                if (BIamRoleTypeEnum.OPENAPI != roleEntity.getType()) {
                    throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.create.scopesNotSupport", "认证中心作用域不支持");
                }
            }
        }

        BIamOauth2RegisteredClientEntity entity = new BIamOauth2RegisteredClientEntity();
        entity.setAuthorizationGrantType(inputDto.getAuthorizationGrantType());
        entity.setStatus(StatusEnum.ENABLE);
        entity.setClientId(UUIDv7.generateWithoutDashes());
        entity.setClientName(inputDto.getClientName());
        entity.setClientSecret(inputDto.getClientSecret());

        entity.setClientIdIssuedAt(System.currentTimeMillis());
        entity.setClientSecretExpiresAt(Long.MAX_VALUE);
        entity.setClientAuthenticationMethods(JSONUtil.toJsonStr(List.of(
                ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue())));

        if (BIamAuthorizationGrantTypeEnum.CLIENT_CREDENTIALS == inputDto.getAuthorizationGrantType()) {
            entity.setAuthorizationGrantTypes(JSONUtil.toJsonStr(List.of(
                    AuthorizationGrantType.CLIENT_CREDENTIALS.getValue())));
            entity.setRedirectUris(JSONUtil.toJsonStr(List.of()));
            entity.setPostLogoutRedirectUris(JSONUtil.toJsonStr(List.of()));

            entity.setClientSettings(JSONUtil.toJsonStr(new BIamAuth2RegisteredClientSettingDto()));
            entity.setTokenSettings(JSONUtil.toJsonStr(new BIamAuth2RegisteredTokenSettingDto()));
        } else if (BIamAuthorizationGrantTypeEnum.AUTHORIZATION_CODE == inputDto.getAuthorizationGrantType()) {
            entity.setAuthorizationGrantTypes(JSONUtil.toJsonStr(List.of(
                    AuthorizationGrantType.AUTHORIZATION_CODE.getValue(),
                    AuthorizationGrantType.REFRESH_TOKEN.getValue())));
            entity.setRedirectUris(JSONUtil.toJsonStr(inputDto.getRedirectUris()));
            entity.setPostLogoutRedirectUris(JSONUtil.toJsonStr(inputDto.getPostLogoutRedirectUris()));

            BIamAuth2RegisteredClientSettingDto clientSetting = new BIamAuth2RegisteredClientSettingDto();
            clientSetting.setRequireProofKey(true);
            clientSetting.setRequireAuthorizationConsent(true);
            entity.setClientSettings(JSONUtil.toJsonStr(clientSetting));

            entity.setTokenSettings(JSONUtil.toJsonStr(new BIamAuth2RegisteredTokenSettingDto()));
        }

        entity.setScopes(JSONUtil.toJsonStr(inputDto.getScopes()));
        entity.setAllowedIps(JSONUtil.toJsonStr(inputDto.getAllowedIps()));
        return oauth2RegisteredClientDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(String id) {
        BIamOauth2RegisteredClientEntity entityById = oauth2RegisteredClientDao.findById(id);
        if (StatusEnum.ENABLE == entityById.getStatus()) {
            throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.delete.enableStatus", "启用状态不能删除");
        }

        return oauth2RegisteredClientDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(BIamOAuth2RegisteredClientUpdateInputDto inputDto) {
        BIamOauth2RegisteredClientEntity entityById = oauth2RegisteredClientDao.findById(inputDto.getId());
        if (Objects.isNull(entityById)) {
            throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.update.clientNotExists", "认证中心客户端不存在");
        }

        // 校验参数（授权码模式）
        if (BIamAuthorizationGrantTypeEnum.AUTHORIZATION_CODE == entityById.getAuthorizationGrantType()) {
            if (CollectionUtil.isEmpty(inputDto.getRedirectUris())) {
                throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.update.emptyRedirectUris", "重定向URI不能为空");
            }

            if (CollectionUtil.isEmpty(inputDto.getPostLogoutRedirectUris())) {
                throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.update.emptyPostLogoutRedirectUris", "登出后重定向URI不能为空");
            }
        }

        // 校验参数（客户端模式）
        if (BIamAuthorizationGrantTypeEnum.CLIENT_CREDENTIALS == entityById.getAuthorizationGrantType()) {
            List<BIamRoleEntity> roleEntityList = roleDao.selectByIdSet(inputDto.getScopes());
            if (inputDto.getScopes().size() != roleEntityList.size()) {
                throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.update.scopesNotExists", "认证中心作用域不存在");
            }

            for (BIamRoleEntity roleEntity : roleEntityList) {
                if (BIamRoleTypeEnum.OPENAPI != roleEntity.getType()) {
                    throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.update.scopesNotSupport", "认证中心作用域不支持");
                }
            }
        }

        //验证用户名是否存在
        BIamOauth2RegisteredClientEntity entityByClientName = oauth2RegisteredClientDao.findByClientName(inputDto.getClientName());
        if (null != entityByClientName && !entityByClientName.getId().equals(inputDto.getId())) {
            throw new BIamBusinessException("biam.identity.service.oauth2RegisteredClient.update.clientNameAlreadyExists", "认证中心客户端名称已经存在");
        }

        BIamOauth2RegisteredClientEntity entity = new BIamOauth2RegisteredClientEntity();
        entity.setId(inputDto.getId());
        entity.setClientName(inputDto.getClientName());
        if (StrUtil.isNotBlank(inputDto.getClientSecret())) {
            entity.setClientSecret(inputDto.getClientSecret());
        }
        entity.setClientSecret(inputDto.getClientSecret());

        if (BIamAuthorizationGrantTypeEnum.AUTHORIZATION_CODE == entityById.getAuthorizationGrantType()) {
            entity.setRedirectUris(JSONUtil.toJsonStr(inputDto.getRedirectUris()));
            entity.setPostLogoutRedirectUris(JSONUtil.toJsonStr(inputDto.getPostLogoutRedirectUris()));
        }

        entity.setScopes(JSONUtil.toJsonStr(inputDto.getScopes()));
        entity.setAllowedIps(JSONUtil.toJsonStr(inputDto.getAllowedIps()));
        return oauth2RegisteredClientDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(BIamOAuth2RegisteredClientUpdateStatusInputDto inputDto) {
        BIamOauth2RegisteredClientEntity entityById = oauth2RegisteredClientDao.findById(inputDto.getId());
        if (null == entityById || inputDto.getStatus() == entityById.getStatus()) {
            return 0;
        }
        return oauth2RegisteredClientDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateClientSecret(BIamOAuth2RegisteredClientUpdateClientSecretInputDto inputDto) {
        BIamOauth2RegisteredClientEntity entityById = oauth2RegisteredClientDao.findById(inputDto.getId());
        if (null == entityById) {
            return 0;
        }
        return oauth2RegisteredClientDao.updateClientSecret(inputDto.getId(),inputDto.getClientSecret());
    }

    @Override
    public List<String> allEnableClientId() {
        return oauth2RegisteredClientDao.allClientId(StatusEnum.ENABLE);
    }

    @Override
    public BIamOAuth2RegisteredClientDto findByClientId(String clientId) {
        BIamOauth2RegisteredClientEntity entity = oauth2RegisteredClientDao.findByClientId(clientId);
        if (entity == null) {
            return null;
        }
        return convertEntity2ClientDto(entity);
    }

    @Override
    public BIamOAuth2RegisteredClientDetailOutputDto detail(String id) {
        BIamOauth2RegisteredClientEntity entity = oauth2RegisteredClientDao.findById(id);
        if (Objects.isNull(entity)) {
            return null;
        }
        BIamOAuth2RegisteredClientDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamOAuth2RegisteredClientDetailOutputDto.class, true);
        if (StrUtil.isNotBlank(entity.getScopes())) {
            outputDto.setScopes(new HashSet<>(JSONUtil.toList(entity.getScopes(), String.class)));
        }

        if (StrUtil.isNotBlank(entity.getRedirectUris())) {
            outputDto.setRedirectUris(JSONUtil.toList(entity.getRedirectUris(), String.class));
        }

        if (StrUtil.isNotBlank(entity.getPostLogoutRedirectUris())) {
            outputDto.setPostLogoutRedirectUris(JSONUtil.toList(entity.getPostLogoutRedirectUris(), String.class));
        }

        if (StrUtil.isNotBlank(entity.getAllowedIps())) {
            outputDto.setAllowedIps(new TreeSet<>(JSONUtil.toList(entity.getAllowedIps(), String.class)));
        }
        return outputDto;
    }

    @Override
    public Page<BIamOAuth2RegisteredClientListOutputDto> selectPage(BIamOAuth2RegisteredClientPageQueryInputDto inputDto) {
        Page<BIamOauth2RegisteredClientEntity> page = oauth2RegisteredClientDao.selectPage(inputDto);
        Page<BIamOAuth2RegisteredClientListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), BIamOAuth2RegisteredClientListOutputDto.class));
        return pageResult;
    }


    private BIamOAuth2RegisteredClientDto convertEntity2ClientDto(BIamOauth2RegisteredClientEntity entity) {
        BIamOAuth2RegisteredClientDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamOAuth2RegisteredClientDto.class, true);

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

            if (BIamAuthorizationGrantTypeEnum.CLIENT_CREDENTIALS == entity.getAuthorizationGrantType()) {
                //查询用户角色信息
                Set<String> scopeSet = new HashSet<>(JSONUtil.toList(entity.getScopes(), String.class));
                List<BIamRoleEntity> roleEntityList = roleDao.selectByIdSet(scopeSet);

                List<String> roleCodes = new ArrayList<>();
                List<String> permissionIds = new ArrayList<>();

                if (CollectionUtil.isNotEmpty(roleEntityList)) {
                    roleCodes = roleEntityList.stream().map(BIamRoleEntity::getCode).toList();
                }
                if (CollectionUtil.isNotEmpty(roleEntityList)) {
                    permissionIds = permissionDao.selectIdByRoleIds(
                            roleEntityList.stream().map(BIamRoleEntity::getId).collect(Collectors.toList()));
                }

                //查询角色权限信息
                BIamPermissionTreeOutputDto allTreeOutputDto = permissionCache.treeAll();
                Set<BIamPermissionTreeOutputDto> permissionWithParentNodeSet = TreeUtil.getAllParentNodes(allTreeOutputDto, new HashSet<>(permissionIds));

                Set<String> scopes = new HashSet<>();
                for (String roleCode : roleCodes) {
                    scopes.add("ROLE_" + roleCode);
                }
                for (BIamPermissionTreeOutputDto dto : permissionWithParentNodeSet) {
                    scopes.add(dto.getCode());
                }
                outputDto.setScopes(scopes);
            } else {
                outputDto.setScopes(new HashSet<>(JSONUtil.toList(entity.getScopes(), String.class)));
            }
        }

        if (StrUtil.isNotBlank(entity.getClientSettings())) {
            outputDto.setClientSettings(JSONUtil.toBean(entity.getClientSettings(), BIamAuth2RegisteredClientSettingDto.class));
        }

        if (StrUtil.isNotBlank(entity.getTokenSettings())) {
            outputDto.setTokenSettings(JSONUtil.toBean(entity.getTokenSettings(), BIamAuth2RegisteredTokenSettingDto.class));
        }

        if (StrUtil.isNotBlank(entity.getAllowedIps())) {
            outputDto.setAllowedIps(new TreeSet<>(JSONUtil.toList(entity.getAllowedIps(), String.class)));
        }

        return outputDto;
    }
}
