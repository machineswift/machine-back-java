package com.machine.service.iam.auth.service;

import com.machine.client.iam.auth.dto.AuthTokenAddDto;
import com.machine.client.iam.auth.dto.OAuth2RegisteredClientDto;
import com.machine.client.iam.auth.dto.output.OAuth2RegisteredClientDetailOutputDto;

import java.util.List;

public interface IOauth2RegisteredClientService {

    int add(AuthTokenAddDto dto);

    int save(OAuth2RegisteredClientDto dto);

    int update(OAuth2RegisteredClientDto dto);

    List<String> allEnableClientId();

    OAuth2RegisteredClientDetailOutputDto findById(String id);

    OAuth2RegisteredClientDetailOutputDto findByClientId(String clientId);

}
