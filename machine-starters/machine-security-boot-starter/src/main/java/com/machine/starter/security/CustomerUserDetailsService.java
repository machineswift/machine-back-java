package com.machine.starter.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.UserAuthDetailOutputDto;
import com.machine.client.iam.user.dto.UserDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.starter.redis.cache.RedisCacheIamFunctionPermission;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.machine.starter.redis.constant.RedisConstant.REDIS_CACHE_PENETRATION_PREVENT;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.User.IAM_USER_BASE_KEY;

@Service
public class CustomerUserDetailsService {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisCacheIamFunctionPermission redisCacheIamFunctionPermission;

    @Autowired
    private IIamUserClient userClient;

    public UserDetails loadUserDetails() throws UsernameNotFoundException {
        //查询功能权限信息
        UserAuthDetailOutputDto userDetailDto = redisCacheIamFunctionPermission.functionPermission();
        return new CustomerUserDetails(userDetailDto);
    }

    public UserDto loadUserInCache() {
        String userId = AppContext.getContext().getUserId();
        String value = customerRedisCommands.get(IAM_USER_BASE_KEY + userId);
        if (StrUtil.isNotEmpty(value)) {
            if (REDIS_CACHE_PENETRATION_PREVENT.equals(value)) {
                return null;
            }

            JSONObject jsonObject = JSONUtil.parseObj(value);
            UserDto dto = new UserDto();
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

    public UserDto loadUserByUsername(String username) {
        return userClient.getByUserName(username);
    }

    public UserDto loadUserByPhone(String phone) {
        return userClient.getByPhone(phone);
    }
}
