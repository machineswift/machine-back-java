package com.machine.service.iam.biam.identity.server;

import com.machine.client.iam.biam.identity.IBIamOauth2AuthorizationClient;
import com.machine.client.iam.biam.identity.dto.BIamOAuth2AuthorizationDto;
import com.machine.service.iam.biam.identity.service.IBIamOauth2AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/oauth2_authorization")
public class BIamOauth2AuthorizationServer implements IBIamOauth2AuthorizationClient {

    @Autowired
    private IBIamOauth2AuthorizationService oauth2AuthorizationService;

    @Override
    @PostMapping("save")
    public int save(@RequestBody BIamOAuth2AuthorizationDto dto) {
        return oauth2AuthorizationService.save(dto);
    }

    @Override
    @GetMapping("remove")
    public void remove(@RequestParam("id") String id) {
        oauth2AuthorizationService.remove(id);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody BIamOAuth2AuthorizationDto dto) {
        return oauth2AuthorizationService.update(dto);
    }

    @Override
    @GetMapping("existsById")
    public boolean existsById(@RequestParam("id") String id) {
        return oauth2AuthorizationService.existsById(id);
    }

    @Override
    @GetMapping("findById")
    public BIamOAuth2AuthorizationDto findById(@RequestParam("id") String id) {
        return oauth2AuthorizationService.findById(id);
    }

    @Override
    @PostMapping("findByToken")
    public BIamOAuth2AuthorizationDto findByToken(@RequestBody BIamOAuth2AuthorizationDto dto) {
        return oauth2AuthorizationService.findByToken(dto);
    }

}
