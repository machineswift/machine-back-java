package com.machine.app.openapi.iam.dictionary.controller;

import com.machine.app.openapi.iam.dictionary.business.IOpenApiEnumBusiness;
import com.machine.app.openapi.iam.dictionary.controller.request.OpenApiEnumQueryEnumInfoRequestVo;
import com.machine.app.openapi.iam.dictionary.controller.response.OpenApiEnumInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "【IAM】字典模块")
@Slf4j
@RestController
@RequestMapping("openapi/iam/dictionary")
public class OpenApiEnumController {

    @Autowired
    private IOpenApiEnumBusiness enumBusiness;

    @PermitAll
    @Operation(summary = "查询枚举信息")
    @PostMapping("queryEnumInfo")
    public List<OpenApiEnumInfoResponse> queryEnumInfo(@RequestBody @Validated OpenApiEnumQueryEnumInfoRequestVo request) {
        return enumBusiness.queryEnumInfo(request);
    }
}
