package com.machine.service.iam.user.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.user.IIamThirdPartyUserClient;
import com.machine.client.iam.user.dto.input.IamThirdPartyUserBindInputDto;
import com.machine.client.iam.user.dto.input.IamThirdPartyUserCreateInputDto;
import com.machine.service.iam.user.service.IIamThirdPartyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/iam/third_party_user")
public class IamThirdPartyUserServer implements IIamThirdPartyUserClient {

    @Autowired
    private IIamThirdPartyUserService thirdPartyUserService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated IamThirdPartyUserCreateInputDto inputDto) {
        log.info("创建第三方用户， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return thirdPartyUserService.create(inputDto);
    }

    @Override
    @PostMapping("bind")
    public void bind(@RequestBody @Validated IamThirdPartyUserBindInputDto inputDto) {
        log.info("绑定第三方用户， inputDto={}", JSONUtil.toJsonStr(inputDto));
        thirdPartyUserService.bind(inputDto);
    }
}
