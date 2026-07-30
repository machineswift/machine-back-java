package com.machine.service.iam.identity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.identity.dto.input.*;
import com.machine.client.iam.identity.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import com.machine.client.iam.identity.dto.output.IamOAuth2RegisteredClientListOutputDto;
import com.machine.sdk.base.model.dto.iam.identity.IamOAuth2RegisteredClientDto;

import java.util.List;

public interface IIamOauth2RegisteredClientService {

    String create(IamOAuth2RegisteredClientCreateInputDto inputDto);

    int update(IamOAuth2RegisteredClientUpdateInputDto inputDto);

    int updateStatus(IamOAuth2RegisteredClientUpdateStatusInputDto inputDto);

    int delete(String id);

    List<String> allEnableClientId();

    IamOAuth2RegisteredClientDto findById(String id);

    IamOAuth2RegisteredClientDto findByClientId(String clientId);


    IamOAuth2RegisteredClientDetailOutputDto detail(String id);

    Page<IamOAuth2RegisteredClientListOutputDto> selectPage(IamOAuth2RegisteredClientPageQueryInputDto inputDto);

}
