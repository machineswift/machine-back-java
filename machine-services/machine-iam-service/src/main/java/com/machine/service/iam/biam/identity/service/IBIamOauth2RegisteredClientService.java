package com.machine.service.iam.biam.identity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.identity.dto.input.*;
import com.machine.client.iam.biam.identity.dto.output.BIamOAuth2RegisteredClientDetailOutputDto;
import com.machine.client.iam.biam.identity.dto.output.BIamOAuth2RegisteredClientListOutputDto;
import com.machine.sdk.base.model.dto.biam.identity.BIamOAuth2RegisteredClientDto;

import java.util.List;

public interface IBIamOauth2RegisteredClientService {

    String create(BIamOAuth2RegisteredClientCreateInputDto inputDto);

    int delete(String id);

    int update(BIamOAuth2RegisteredClientUpdateInputDto inputDto);

    int updateStatus(BIamOAuth2RegisteredClientUpdateStatusInputDto inputDto);

    int updateClientSecret(BIamOAuth2RegisteredClientUpdateClientSecretInputDto inputDto);

    List<String> allEnableClientId();

    BIamOAuth2RegisteredClientDto findByClientId(String clientId);

    BIamOAuth2RegisteredClientDetailOutputDto detail(String id);

    Page<BIamOAuth2RegisteredClientListOutputDto> selectPage(BIamOAuth2RegisteredClientPageQueryInputDto inputDto);

}
