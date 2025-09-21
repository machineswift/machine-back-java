package com.machine.app.xxljob.job.webhook;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.input.IamUserQueryListOffsetInputDto;
import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.machine.sdk.common.constant.ContextConstant.SYSTEM_USER_ID;

@Slf4j
@Component
public class WebhookManualUserXxJob {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @XxlJob("WebhookUserJobHandler")
    public void webhookUserJobHandler() {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        String param = XxlJobHelper.getJobParam();
        XxlJobHelper.log("XXL-JOB, Webhook抛出用户创建事件开始");

        String offset = null;

        while (true) {
            IamUserQueryListOffsetInputDto inputDto = new IamUserQueryListOffsetInputDto();
            inputDto.setOffset(offset);
            inputDto.setSize(100);
            List<IamUserListOutputDto> outputDtoList = userClient.listByOffset(inputDto);
            if (CollectionUtil.isEmpty(outputDtoList)) {
                break;
            }

            for (IamUserListOutputDto outputDto : outputDtoList) {
                //判断是否是全部
                if (StrUtil.isBlank(param)) {
                    return;
                } else if ("ALL".equals(param)) {
                    customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_USER_CREATE, new IdDto(outputDto.getId()));
                } else {
                    customerStreamBridge.sendWebHookEvent(param, EventTypeEnum.IAM_USER_CREATE, new IdDto(outputDto.getId()));
                }
            }

            offset = outputDtoList.getLast().getId();
        }
        XxlJobHelper.log("XXL-JOB, Webhook抛出用户创建事件结束");
        AppContext.getContext().clear();
    }
}