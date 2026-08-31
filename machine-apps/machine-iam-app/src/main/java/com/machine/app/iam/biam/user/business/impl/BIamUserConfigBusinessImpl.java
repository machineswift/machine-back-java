package com.machine.app.iam.biam.user.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.user.business.IBIamUserConfigBusiness;
import com.machine.app.iam.biam.user.controller.vo.request.BIamUserConfigGetRequestVo;
import com.machine.app.iam.biam.user.controller.vo.request.BIamUserConfigSaveRequestVo;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserConfigResponseVo;
import com.machine.client.iam.biam.user.IBIamUserConfigClient;
import com.machine.client.iam.biam.user.dto.input.BIamUserConfigGetInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserConfigSaveInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserConfigOutputDto;
import com.machine.sdk.base.context.AppContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BIamUserConfigBusinessImpl implements IBIamUserConfigBusiness {

    @Autowired
    private IBIamUserConfigClient userConfigClient;

    @Override
    public void save(BIamUserConfigSaveRequestVo request) {
        BIamUserConfigSaveInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request),
                BIamUserConfigSaveInputDto.class);
        inputDto.setUserId(AppContextHolder.getContext().getUserId());
        userConfigClient.save(inputDto);
    }

    @Override
    public BIamUserConfigResponseVo getByKey(BIamUserConfigGetRequestVo request) {
        BIamUserConfigGetInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request),
                BIamUserConfigGetInputDto.class);
        inputDto.setUserId(AppContextHolder.getContext().getUserId());

        BIamUserConfigOutputDto outputDto = userConfigClient.getByKey(inputDto);
        if (outputDto == null) {
            return null;
        }

        BIamUserConfigResponseVo responseVo = new BIamUserConfigResponseVo();
        responseVo.setConfigKey(outputDto.getConfigKey());
        responseVo.setConfigValue(outputDto.getConfigValue());
        return responseVo;
    }

}
