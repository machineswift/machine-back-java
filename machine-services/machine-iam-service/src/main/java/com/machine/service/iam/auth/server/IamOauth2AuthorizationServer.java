package com.machine.service.iam.auth.server;

import com.machine.client.iam.auth.IIamOauth2AuthorizationClient;
import com.machine.client.iam.auth.dto.input.OAuth2AuthorizationDto;
import com.machine.client.iam.auth.dto.output.OAuth2AuthorizationOutputDto;
import com.machine.service.iam.auth.service.IOauth2AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/oauth2_authorization")
public class IamOauth2AuthorizationServer implements IIamOauth2AuthorizationClient {

    @Autowired
    private IOauth2AuthorizationService oauth2AuthorizationService;

    @Override
    public int save(OAuth2AuthorizationDto dto) {
        return oauth2AuthorizationService.save(dto);
    }

    @Override
    public int update(OAuth2AuthorizationDto dto) {
        return oauth2AuthorizationService.update(dto);
    }

    @Override
    public OAuth2AuthorizationOutputDto findById(String id) {
        return oauth2AuthorizationService.findById(id);
    }

    @Override
    public OAuth2AuthorizationOutputDto findByToken(OAuth2AuthorizationDto dto) {
        return oauth2AuthorizationService.findByToken(dto);
    }

    @Override
    public void remove(String id) {
         oauth2AuthorizationService.remove(id);

    }
}
