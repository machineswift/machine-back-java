package com.machine.service.iam.auth.server;

import com.machine.client.iam.auth.IIamOauth2AuthorizationClient;
import com.machine.client.iam.auth.dto.input.IamOAuth2AuthorizationDto;
import com.machine.client.iam.auth.dto.output.IamOAuth2AuthorizationOutputDto;
import com.machine.service.iam.auth.service.IIamOauth2AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/oauth2_authorization")
public class IamOauth2AuthorizationServer implements IIamOauth2AuthorizationClient {

    @Autowired
    private IIamOauth2AuthorizationService oauth2AuthorizationService;

    @Override
    public int save(IamOAuth2AuthorizationDto dto) {
        return oauth2AuthorizationService.save(dto);
    }

    @Override
    public int update(IamOAuth2AuthorizationDto dto) {
        return oauth2AuthorizationService.update(dto);
    }

    @Override
    public IamOAuth2AuthorizationOutputDto findById(String id) {
        return oauth2AuthorizationService.findById(id);
    }

    @Override
    public IamOAuth2AuthorizationOutputDto findByToken(IamOAuth2AuthorizationDto dto) {
        return oauth2AuthorizationService.findByToken(dto);
    }

    @Override
    public void remove(String id) {
         oauth2AuthorizationService.remove(id);

    }
}
