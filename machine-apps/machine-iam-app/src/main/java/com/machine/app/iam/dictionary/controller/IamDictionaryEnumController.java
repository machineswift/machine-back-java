package com.machine.app.iam.dictionary.controller;

import com.machine.app.iam.dictionary.business.IIamEnumBusiness;
import com.machine.app.iam.dictionary.controller.request.IamDictionaryEnumRequestVo;
import com.machine.app.iam.dictionary.controller.response.IamDictionaryEnumInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "【字典】枚举模块")
@Slf4j
@RestController
@RequestMapping("iam/dictionary/enum")
public class IamDictionaryEnumController {

    @Autowired
    private IIamEnumBusiness enumBusiness;

    @Operation(summary = "查询枚举信息")
    @PostMapping("queryEnumInfo")
    public List<IamDictionaryEnumInfoResponse> queryEnumInfo(@RequestBody @Validated IamDictionaryEnumRequestVo request) {
        return enumBusiness.queryEnumInfo(request);
    }
}
