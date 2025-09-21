package com.machine.service.iam.auth.service;

import com.machine.client.iam.auth.dto.IamAuthTokenAddDto;
import com.machine.client.iam.auth.dto.IamOAuth2RegisteredClientDto;
import com.machine.client.iam.auth.dto.output.IamOAuth2RegisteredClientDetailOutputDto;

import java.util.List;

public interface IIamOauth2RegisteredClientService {

    int add(IamAuthTokenAddDto dto);

    int save(IamOAuth2RegisteredClientDto dto);

    int update(IamOAuth2RegisteredClientDto dto);

    List<String> allEnableClientId();

    IamOAuth2RegisteredClientDetailOutputDto findById(String id);

    IamOAuth2RegisteredClientDetailOutputDto findByClientId(String clientId);

}
