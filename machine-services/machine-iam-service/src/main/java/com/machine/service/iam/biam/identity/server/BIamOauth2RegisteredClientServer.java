package com.machine.service.iam.biam.identity.server;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.identity.IBIamOauth2RegisteredClientClient;
import com.machine.client.iam.biam.identity.dto.input.*;
import com.machine.client.iam.biam.identity.dto.output.BIamOAuth2RegisteredClientDetailOutputDto;
import com.machine.client.iam.biam.identity.dto.output.BIamOAuth2RegisteredClientListOutputDto;
import com.machine.sdk.base.model.dto.biam.identity.BIamOAuth2RegisteredClientDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.iam.biam.identity.service.IBIamOauth2RegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/oauth2_registered_client")
public class BIamOauth2RegisteredClientServer implements IBIamOauth2RegisteredClientClient {

    @Autowired
    private IBIamOauth2RegisteredClientService registeredClientService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated BIamOAuth2RegisteredClientCreateInputDto inputDto) {
        log.info("认证中心创建OAuth2客户端，inputDto={}", inputDto);
        return registeredClientService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除OAuth2客户端，id={}", request.getId());
        return registeredClientService.delete(request.getId());
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated BIamOAuth2RegisteredClientUpdateInputDto inputDto) {
        log.info("认证中心修改OAuth2客户端，inputDto={}", inputDto);
        return registeredClientService.update(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated BIamOAuth2RegisteredClientUpdateStatusInputDto inputDto) {
        log.info("修改OAuth2客户端状态，inputDto={}", inputDto);
        return registeredClientService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("update_clientSecret")
    public int updateClientSecret(@RequestBody @Validated BIamOAuth2RegisteredClientUpdateClientSecretInputDto inputDto) {
        log.info("修改OAuth2客户端密钥，inputDto={}", inputDto);
        return registeredClientService.updateClientSecret(inputDto);
    }

    @Override
    @GetMapping("all_enable_client_id")
    public List<String> allEnableClientId() {
        return registeredClientService.allEnableClientId();
    }

    @Override
    @GetMapping("get_by_clientId")
    public BIamOAuth2RegisteredClientDto getByClientId(@RequestParam("clientId")  String clientId) {
       return registeredClientService.findByClientId(clientId);
    }

    @Override
    @PostMapping("detail")
    public BIamOAuth2RegisteredClientDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return registeredClientService.detail(request.getId());
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<BIamOAuth2RegisteredClientListOutputDto> selectPage(@RequestBody BIamOAuth2RegisteredClientPageQueryInputDto inputDto) {
        Page<BIamOAuth2RegisteredClientListOutputDto> pageResult = registeredClientService.selectPage(inputDto);
        return new PageResponse<>(pageResult.getCurrent(), pageResult.getSize(), pageResult.getTotal(), pageResult.getRecords());
    }
}
