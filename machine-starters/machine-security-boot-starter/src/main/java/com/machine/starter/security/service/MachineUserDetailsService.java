package com.machine.starter.security.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserAuthDetailOutputDto;
import com.machine.client.iam.biam.user.dto.BIamUserDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.starter.redis.cache.biam.RedisBIamFunctionPermissionCache;
import com.machine.starter.redis.command.CustomerRedisCommands;
import com.machine.starter.security.service.model.MachineUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.machine.sdk.base.constant.CommonConstant.EMPTY_OBJECT;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.User.BIAM_USER_BASE_KEY;

@Service
public class MachineUserDetailsService {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisBIamFunctionPermissionCache redisIamFunctionPermissionCache;

    @Autowired
    private IBIamUserClient userClient;

    public UserDetails loadUserDetails() throws UsernameNotFoundException {
        //查询功能权限信息
        BIamUserAuthDetailOutputDto userDetailDto = redisIamFunctionPermissionCache.functionPermission();
        return new MachineUserDetails(userDetailDto);
    }

    public BIamUserDto loadUserInCache() {
        String userId = AppContextHolder.getContext().getUserId();
        String value = customerRedisCommands.get(BIAM_USER_BASE_KEY + userId);
        if (StrUtil.isNotEmpty(value)) {
            if (EMPTY_OBJECT.equals(value)) {
                return null;
            }

            JSONObject jsonObject = JSONUtil.parseObj(value);
            BIamUserDto dto = new BIamUserDto();
            dto.setUserId(jsonObject.getStr("id"));
            dto.setPassword(jsonObject.getStr("password"));
            String status = jsonObject.getStr("status");
            if (StatusEnum.ENABLE.getName().equals(status)) {
                dto.setEnabled(true);
            }
            return dto;
        }

        return userClient.getByUserId(userId);
    }

    public BIamUserDto loadUserByUsername(String username) {
        return userClient.getByUserName(username);
    }

    public BIamUserDto loadUserByPhone(String phone) {
        return userClient.getByPhone(phone);
    }
}
