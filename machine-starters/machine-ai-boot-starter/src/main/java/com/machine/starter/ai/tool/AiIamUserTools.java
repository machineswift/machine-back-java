package com.machine.starter.ai.tool;

import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.BIamUserDto;
import com.machine.sdk.base.context.AppContextHolder;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AiIamUserTools {

    @Autowired
    private IBIamUserClient userClient;

    @Tool(description = "获取当前用户信息", returnDirect = true)
    BIamUserDto getCurrentUserInfo() {
        String userId = AppContextHolder.getContext().getUserId();
        BIamUserDto userDto = userClient.getByUserId(userId);
        userDto.setPassword(null);
        return userDto;
    }

}
