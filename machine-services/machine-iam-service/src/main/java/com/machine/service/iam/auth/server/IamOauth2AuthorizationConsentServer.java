package com.machine.service.iam.auth.server;

import com.machine.client.iam.auth.IIamOauth2AuthorizationConsentClient;
import com.machine.client.iam.auth.dto.input.IamOauth2AuthorizationConsentInputDto;
import com.machine.client.iam.auth.dto.output.IamOauth2AuthorizationConsentOutputDto;
import com.machine.service.iam.auth.service.IIamOauth2AuthorizationConsentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/oauth2_authorization_consent")
public class IamOauth2AuthorizationConsentServer implements IIamOauth2AuthorizationConsentClient {

    @Autowired
    private IIamOauth2AuthorizationConsentService authorizationConsentService;

    @Override
    @PostMapping("update")
    public void update(@RequestBody IamOauth2AuthorizationConsentInputDto dto) {
        authorizationConsentService.update(dto);
    }

    @Override
    @PostMapping("save")
    public void save(@RequestBody IamOauth2AuthorizationConsentInputDto dto) {
        authorizationConsentService.save(dto);
    }

    @Override
    @PostMapping("remove")
    public void remove(@RequestBody IamOauth2AuthorizationConsentInputDto dto) {
        authorizationConsentService.remove(dto);
    }

    @Override
    @PostMapping("findById")
    public IamOauth2AuthorizationConsentOutputDto findById(@RequestBody IamOauth2AuthorizationConsentInputDto dto) {
        return authorizationConsentService.findById(dto);
    }
}
