package com.machine.app.openapi.dictionary.controller;

import com.machine.app.openapi.dictionary.business.IOpenApiEnumBusiness;
import com.machine.app.openapi.dictionary.controller.request.OpenApiEnumQueryEnumInfoRequestVo;
import com.machine.app.openapi.dictionary.controller.response.OpenApiEnumInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "【枚举模块】")
@Slf4j
@RestController
@RequestMapping("openapi/dictionary/enum")
public class OpenApiEnumController {

    @Autowired
    private IOpenApiEnumBusiness enumBusiness;

    @Operation(summary = "查询枚举信息")
    @PostMapping("queryEnumInfo")
    @PermitAll
    public List<OpenApiEnumInfoResponse> queryEnumInfo(@RequestBody @Validated OpenApiEnumQueryEnumInfoRequestVo request) {
        return enumBusiness.queryEnumInfo(request);
    }
}
