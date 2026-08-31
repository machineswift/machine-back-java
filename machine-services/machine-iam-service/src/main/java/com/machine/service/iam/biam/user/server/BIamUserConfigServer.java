package com.machine.service.iam.biam.user.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.user.IBIamUserConfigClient;
import com.machine.client.iam.biam.user.dto.input.BIamUserConfigGetInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserConfigSaveInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserConfigOutputDto;
import com.machine.service.iam.biam.user.service.IBIamUserConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/user_config")
public class BIamUserConfigServer implements IBIamUserConfigClient {

    @Autowired
    private IBIamUserConfigService userConfigService;

    @Override
    @PostMapping("get_by_key")
    public BIamUserConfigOutputDto getByKey(@RequestBody @Validated BIamUserConfigGetInputDto inputDto) {
        log.info("查询用户配置，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userConfigService.getByKey(inputDto);
    }

    @Override
    @PostMapping("save")
    public void save(@RequestBody @Validated BIamUserConfigSaveInputDto inputDto) {
        log.info("保存用户配置，inputDto={}", JSONUtil.toJsonStr(inputDto));
        userConfigService.save(inputDto);
    }
}
