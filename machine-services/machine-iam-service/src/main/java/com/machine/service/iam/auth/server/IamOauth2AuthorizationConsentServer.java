package com.machine.service.iam.auth.server;

import com.machine.client.iam.auth.IIamOauth2AuthorizationConsentClient;
import com.machine.client.iam.auth.dto.input.Oauth2AuthorizationConsentInputDto;
import com.machine.client.iam.auth.dto.output.Oauth2AuthorizationConsentOutputDto;
import com.machine.service.iam.auth.service.IOauth2AuthorizationConsentService;
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
    private IOauth2AuthorizationConsentService authorizationConsentService;

    @Override
    @PostMapping("update")
    public void update(@RequestBody Oauth2AuthorizationConsentInputDto dto) {
        authorizationConsentService.update(dto);
    }

    @Override
    @PostMapping("save")
    public void save(@RequestBody Oauth2AuthorizationConsentInputDto dto) {
        authorizationConsentService.save(dto);
    }

    @Override
    @PostMapping("remove")
    public void remove(@RequestBody Oauth2AuthorizationConsentInputDto dto) {
        authorizationConsentService.remove(dto);
    }

    @Override
    @PostMapping("findById")
    public Oauth2AuthorizationConsentOutputDto findById(@RequestBody Oauth2AuthorizationConsentInputDto dto) {
        return authorizationConsentService.findById(dto);
    }
}
