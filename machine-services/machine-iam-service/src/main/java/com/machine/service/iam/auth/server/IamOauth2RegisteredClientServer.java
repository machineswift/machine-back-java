package com.machine.service.iam.auth.server;

import com.machine.client.iam.auth.IIamOauth2RegisteredClientClient;
import com.machine.client.iam.auth.dto.IamAuthTokenAddDto;
import com.machine.client.iam.auth.dto.IamOAuth2RegisteredClientDto;
import com.machine.client.iam.auth.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import com.machine.service.iam.auth.service.IIamOauth2RegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/oauth2_registered_client")
public class IamOauth2RegisteredClientServer implements IIamOauth2RegisteredClientClient {

    @Autowired
    private IIamOauth2RegisteredClientService registeredClientService;

    @Override
    @PostMapping("add")
    public int add(@RequestBody IamAuthTokenAddDto dto) {
        return registeredClientService.add(dto);
    }

    @Override
    @PostMapping("save")
    public int save(IamOAuth2RegisteredClientDto dto) {
       return registeredClientService.save(dto);
    }

    @Override
    @PostMapping("update")
    public int update(IamOAuth2RegisteredClientDto dto) {
       return registeredClientService.update(dto);
    }

    @Override
    @GetMapping("all_enable_client_id")
    public List<String> allEnableClientId() {
        return registeredClientService.allEnableClientId();
    }

    @Override
    @PostMapping("get_by_id")
    public IamOAuth2RegisteredClientDetailOutputDto getById(@RequestParam("id")  String id) {
       return registeredClientService.findById(id);
    }

    @Override
    @GetMapping("get_by_clientId")
    public IamOAuth2RegisteredClientDetailOutputDto getByClientId(@RequestParam("clientId")  String clientId) {
       return registeredClientService.findByClientId(clientId);
    }
}
