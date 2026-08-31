package com.machine.service.iam.biam.user.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.user.IBIamThirdPartyUserClient;
import com.machine.client.iam.biam.user.dto.input.BIamThirdPartyUserBindInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamThirdPartyUserCreateInputDto;
import com.machine.service.iam.biam.user.service.IBIamThirdPartyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/third_party_user")
public class BIamThirdPartyUserServer implements IBIamThirdPartyUserClient {

    @Autowired
    private IBIamThirdPartyUserService thirdPartyUserService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated BIamThirdPartyUserCreateInputDto inputDto) {
        log.info("创建第三方用户， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return thirdPartyUserService.create(inputDto);
    }

    @Override
    @PostMapping("bind")
    public void bind(@RequestBody @Validated BIamThirdPartyUserBindInputDto inputDto) {
        log.info("绑定第三方用户， inputDto={}", JSONUtil.toJsonStr(inputDto));
        thirdPartyUserService.bind(inputDto);
    }
}
