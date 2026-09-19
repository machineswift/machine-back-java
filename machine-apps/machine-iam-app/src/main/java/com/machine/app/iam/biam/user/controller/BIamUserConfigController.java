package com.machine.app.iam.biam.user.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.user.business.IBIamUserConfigBusiness;
import com.machine.app.iam.biam.user.controller.vo.request.BIamUserConfigGetRequestVo;
import com.machine.app.iam.biam.user.controller.vo.request.BIamUserConfigSaveRequestVo;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserConfigResponseVo;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.operateLog.annotation.WebOperationLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【BIAM】用户偏好配置模块")
@RestController
@RequestMapping("iam/biam/user_config")
public class BIamUserConfigController {

    @Autowired
    private IBIamUserConfigBusiness userConfigBusiness;

    @Operation(summary = "保存用户配置")
    @PostMapping("save")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "保存用户配置",
            moduleEntityId = "#request.configKey",
            content = "'保存用户配置：' + #request.configKey")
    public void save(@RequestBody @Validated BIamUserConfigSaveRequestVo request) {
        log.info("保存用户配置，request={}", JSONUtil.toJsonStr(request));
        userConfigBusiness.save(request);
    }

    @Operation(summary = "查询用户配置")
    @PostMapping("get_by_key")
    public BIamUserConfigResponseVo getByKey(@RequestBody @Validated BIamUserConfigGetRequestVo request) {
        return userConfigBusiness.getByKey(request);
    }

}
