package com.machine.service.iam.biam.auth.server;

import com.machine.client.iam.biam.auth.IBIamOauth2AuthorizationConsentClient;
import com.machine.client.iam.biam.auth.dto.input.BIamOauth2AuthorizationConsentInputDto;
import com.machine.client.iam.biam.auth.dto.output.BIamOauth2AuthorizationConsentOutputDto;
import com.machine.service.iam.biam.auth.service.IBIamOauth2AuthorizationConsentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/oauth2_authorization_consent")
public class BIamOauth2AuthorizationConsentServer implements IBIamOauth2AuthorizationConsentClient {

    @Autowired
    private IBIamOauth2AuthorizationConsentService authorizationConsentService;

    @Override
    @PostMapping("update")
    public void update(@RequestBody BIamOauth2AuthorizationConsentInputDto dto) {
        authorizationConsentService.update(dto);
    }

    @Override
    @PostMapping("save")
    public void save(@RequestBody BIamOauth2AuthorizationConsentInputDto dto) {
        authorizationConsentService.save(dto);
    }

    @Override
    @PostMapping("remove")
    public void remove(@RequestBody BIamOauth2AuthorizationConsentInputDto dto) {
        authorizationConsentService.remove(dto);
    }

    @Override
    @PostMapping("findById")
    public BIamOauth2AuthorizationConsentOutputDto findById(@RequestBody BIamOauth2AuthorizationConsentInputDto dto) {
        return authorizationConsentService.findById(dto);
    }
}
