package com.machine.app.iam.auth.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.auth.business.IIamAuth2Business;
import com.machine.app.iam.auth.controller.vo.request.IamAuthCreateClientRequestVo;
import com.machine.sdk.common.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "【IAM】auth2.0 认证模块")
@RequestMapping("iam/auth2")
public class IamAuth2Controller {

    @Autowired
    private IIamAuth2Business auth2Business;

    @Operation(summary = "gitee")
    @GetMapping("render/gitee")
    public void gitee(HttpServletResponse response) {
        auth2Business.gitee(response);
    }

    @Operation(summary = "飞书")
    @GetMapping("render/fei_shu")
    public void feiShu(HttpServletResponse response) {
        auth2Business.feishu(response);
    }

    @Operation(summary = "创建客户端")
    @PreAuthorize("hasAnyRole('ROOT','ADMIN')")
    @PostMapping("create_client")
    public IdResponse<String> createSecret(@RequestBody IamAuthCreateClientRequestVo request) {
        log.info("Auth2.0 创建客户端，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(auth2Business.createClient(request));
    }
}
