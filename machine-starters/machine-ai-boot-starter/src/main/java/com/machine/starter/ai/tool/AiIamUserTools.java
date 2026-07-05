package com.machine.starter.ai.tool;

import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.IamUserDto;
import com.machine.sdk.base.context.AppContextHolder;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AiIamUserTools {

    @Autowired
    private IIamUserClient userClient;

    @Tool(description = "获取当前用户信息", returnDirect = true)
    IamUserDto getCurrentUserInfo() {
        String userId = AppContextHolder.getContext().getUserId();
        IamUserDto userDto = userClient.getByUserId(userId);
        userDto.setPassword(null);
        return userDto;
    }

}
