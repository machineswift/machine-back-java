package com.machine.starter.security.service.repository;

import cn.hutool.core.util.StrUtil;
import com.machine.client.iam.biam.identity.IBIamOauth2RegisteredClientClient;
import com.machine.client.iam.biam.identity.dto.input.BIamOAuth2RegisteredClientUpdateClientSecretInputDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.dto.biam.identity.BIamOAuth2RegisteredClientDto;
import com.machine.starter.redis.caffeine.CaffeineCacheRegisteredClient;
import com.machine.starter.security.service.convert.OAuth2Dto2RegisteredClientConverter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.*;

import static com.machine.sdk.base.constant.ContextConstant.SYSTEM_USER_ID;

public class MachineRegisteredClientRepository implements RegisteredClientRepository {

    private final IBIamOauth2RegisteredClientClient oauth2RegisteredClient;
    private final CaffeineCacheRegisteredClient registeredClient;


    public MachineRegisteredClientRepository(IBIamOauth2RegisteredClientClient oauth2RegisteredClient,
                                             CaffeineCacheRegisteredClient registeredClient) {
        this.oauth2RegisteredClient = oauth2RegisteredClient;
        this.registeredClient = registeredClient;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        if (StrUtil.isNotEmpty(registeredClient.getClientSecret())) {
            BIamOAuth2RegisteredClientUpdateClientSecretInputDto inputDto = new BIamOAuth2RegisteredClientUpdateClientSecretInputDto();
            inputDto.setId(registeredClient.getId());
            inputDto.setClientSecret(registeredClient.getClientSecret());
            oauth2RegisteredClient.updateClientSecret(inputDto);
        } else {
            throw new BIamBusinessException("biam.identity.repository.save.notSupportedSave", "认证中心客户端不支持自动创建");
        }
    }

    @Override
    public RegisteredClient findById(String id) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        throw new BIamBusinessException("biam.identity.repository.findById.notSupportedFindById", "认证中心客户端不支持id查询");
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        BIamOAuth2RegisteredClientDto clientDto = registeredClient.getByClientId(clientId);
        if (Objects.isNull(clientDto)) {
            return null;
        }
        return OAuth2Dto2RegisteredClientConverter.convert2Client(clientDto);
    }

}
