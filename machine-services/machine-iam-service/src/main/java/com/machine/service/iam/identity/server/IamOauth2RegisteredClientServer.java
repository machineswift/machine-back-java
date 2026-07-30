package com.machine.service.iam.identity.server;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.identity.IIamOauth2RegisteredClientClient;
import com.machine.client.iam.identity.dto.input.*;
import com.machine.client.iam.identity.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import com.machine.client.iam.identity.dto.output.IamOAuth2RegisteredClientListOutputDto;
import com.machine.sdk.base.model.dto.iam.identity.IamOAuth2RegisteredClientDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.iam.identity.service.IIamOauth2RegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/oauth2_registered_client")
public class IamOauth2RegisteredClientServer implements IIamOauth2RegisteredClientClient {

    @Autowired
    private IIamOauth2RegisteredClientService registeredClientService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated IamOAuth2RegisteredClientCreateInputDto inputDto) {
        log.info("认证中心创建OAuth2客户端，inputDto={}", inputDto);
        return registeredClientService.create(inputDto);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated IamOAuth2RegisteredClientUpdateInputDto inputDto) {
        log.info("认证中心修改OAuth2客户端，inputDto={}", inputDto);
        return registeredClientService.update(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated IamOAuth2RegisteredClientUpdateStatusInputDto inputDto) {
        log.info("修改OAuth2客户端状态，inputDto={}", inputDto);
        return registeredClientService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除OAuth2客户端，id={}", request.getId());
        return registeredClientService.delete(request.getId());
    }

    @Override
    @GetMapping("all_enable_client_id")
    public List<String> allEnableClientId() {
        return registeredClientService.allEnableClientId();
    }

    @Override
    @PostMapping("get_by_id")
    public IamOAuth2RegisteredClientDto getById(@RequestParam("id")  String id) {
       return registeredClientService.findById(id);
    }

    @Override
    @GetMapping("get_by_clientId")
    public IamOAuth2RegisteredClientDto getByClientId(@RequestParam("clientId")  String clientId) {
       return registeredClientService.findByClientId(clientId);
    }


    @Override
    @PostMapping("detail")
    public IamOAuth2RegisteredClientDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return registeredClientService.detail(request.getId());
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<IamOAuth2RegisteredClientListOutputDto> selectPage(@RequestBody IamOAuth2RegisteredClientPageQueryInputDto inputDto) {
        Page<IamOAuth2RegisteredClientListOutputDto> pageResult = registeredClientService.selectPage(inputDto);
        return new PageResponse<>(pageResult.getCurrent(), pageResult.getSize(), pageResult.getTotal(), pageResult.getRecords());
    }
}
