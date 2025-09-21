package com.machine.app.iam.auth.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.auth.business.IIamAuth2Business;
import com.machine.app.iam.auth.business.IIamAuthServerBusiness;
import com.machine.app.iam.auth.controller.vo.request.IamAuthCreateClientRequestVo;
import com.machine.sdk.common.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "【IAM】authServer模块")
@RequestMapping("iam/auth_server")
public class IamAuthServerController {

    @Autowired
    private IIamAuthServerBusiness authServerBusiness;

    @Operation(summary = "创建客户端")
    @PreAuthorize("hasAnyRole('ROOT')")
    @PostMapping("create_client")
    public IdResponse<String> createSecret(@RequestBody IamAuthCreateClientRequestVo request) {
        log.info("Auth2.0 创建客户端，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(authServerBusiness.createClient(request));
    }
}
