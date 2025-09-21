package com.machine.starter.security.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.Platform;
import cn.hutool.http.useragent.UserAgentUtil;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogAvailableOutputDto;
import com.machine.starter.redis.function.CustomerRedisCommands;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Auth.IAM_AUTH_TOKEN_ID;

public class LoginLogUtil {

    public static IamUserLoginLogCreateInputDto getUserLoginLogCreateInputDto(IamUserDetailOutputDto userSimple) {
        IamUserLoginLogCreateInputDto inputDto = new IamUserLoginLogCreateInputDto();
        inputDto.setUserId(userSimple.getId());
        inputDto.setUsername(userSimple.getUsername());
        inputDto.setCode(userSimple.getCode());
        inputDto.setPhone(userSimple.getPhone());
        inputDto.setName(userSimple.getName());
        return inputDto;
    }

    public static void setUserAgentInfo(HttpServletRequest request,
                                        IamUserLoginLogCreateInputDto inputDto) {
        inputDto.setIpAddress(getIpAddress(request));
        String userAgentStr = request.getHeader("User-Agent");
        Platform platform = UserAgentUtil.parse(userAgentStr).getPlatform();
        inputDto.setUserAgent(userAgentStr);
        inputDto.setPlatform(platform.getName());
    }


    public static List<String> blackAllAvailableToken(MachineJwtUtil machineJwtUtil,
                                                      String currentUserId,
                                                      IIamUserLoginLogClient loginLogClient,
                                                      CustomerRedisCommands customerRedisCommands) {
        //查询当前用户的可用 AuthToken
        List<IamUserLoginLogAvailableOutputDto> outputDtoList = loginLogClient.selectAvailableToken(
                new IamUserLoginLogQueryAvailableInputDto(Collections.singletonList(currentUserId))
        );

        //退出登录把所有可用的 AccessToken 和 RefreshToken 都加入黑名单
        Set<String> hasProcessTokenSet = new HashSet<>();
        List<String> hasProcessLoginLogList = new ArrayList<>();
        for (IamUserLoginLogAvailableOutputDto outputDto : outputDtoList) {
            long currentTimeMillis = System.currentTimeMillis() + 10;

            if (outputDto.getAccessTokenExpire().compareTo(currentTimeMillis) > 0) {
                String accessToken = outputDto.getAccessToken();
                Claims claimAuthToken = machineJwtUtil.getClaimsByToken(accessToken);

                if (StrUtil.isBlank(customerRedisCommands.get(IAM_AUTH_TOKEN_ID + claimAuthToken.getId()))) {
                    customerRedisCommands.set(IAM_AUTH_TOKEN_ID + claimAuthToken.getId(),
                            claimAuthToken.get(USER_ID_KEY).toString(),
                            (claimAuthToken.getExpiration().getTime() - System.currentTimeMillis()) / 1000);
                    hasProcessTokenSet.add(claimAuthToken.getId());
                    hasProcessLoginLogList.add(outputDto.getId());
                }
            }

            currentTimeMillis = System.currentTimeMillis() + 10;
            if (outputDto.getRefreshTokenExpire().compareTo(currentTimeMillis) > 0) {
                String refreshToken = outputDto.getRefreshToken();
                Claims claimRefreshToken = machineJwtUtil.getClaimsByToken(refreshToken);
                if (hasProcessTokenSet.contains(claimRefreshToken.getId())) {
                    continue;
                }

                if (StrUtil.isBlank(customerRedisCommands.get(IAM_AUTH_TOKEN_ID + claimRefreshToken.getId()))) {
                    customerRedisCommands.set(IAM_AUTH_TOKEN_ID + claimRefreshToken.getId(),
                            claimRefreshToken.get(USER_ID_KEY).toString(),
                            (claimRefreshToken.getExpiration().getTime() - System.currentTimeMillis()) / 1000);

                    if (!hasProcessLoginLogList.contains(claimRefreshToken.getId())) {
                        hasProcessLoginLogList.add(outputDto.getId());
                    }
                }
            }
        }
        return hasProcessLoginLogList;
    }

    private static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

}
