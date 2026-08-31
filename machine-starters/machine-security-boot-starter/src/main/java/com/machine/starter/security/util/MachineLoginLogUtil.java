package com.machine.starter.security.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.Platform;
import cn.hutool.http.useragent.UserAgentUtil;
import com.machine.client.iam.biam.log.IBIamUserLoginLogClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryAvailableInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogAvailableOutputDto;
import com.machine.sdk.base.tool.ClientEnvironmentUtil;
import com.machine.starter.redis.command.CustomerRedisCommands;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Auth.BIAM_AUTH_TOKEN_ID;

public class MachineLoginLogUtil {

    public static BIamUserLoginLogCreateInputDto getUserLoginLogCreateInputDto(BIamUserDetailOutputDto userSimple) {
        BIamUserLoginLogCreateInputDto inputDto = new BIamUserLoginLogCreateInputDto();
        inputDto.setUserId(userSimple.getId());
        inputDto.setUsername(userSimple.getUsername());
        inputDto.setPhone(userSimple.getPhone());
        inputDto.setRealName(userSimple.getName());
        return inputDto;
    }

    public static void setUserAgentInfo(HttpServletRequest request,
                                        BIamUserLoginLogCreateInputDto inputDto) {
        inputDto.setIpAddress(ClientEnvironmentUtil.getIpAddress(request));
        String userAgentStr = request.getHeader("User-Agent");
        Platform platform = UserAgentUtil.parse(userAgentStr).getPlatform();
        inputDto.setUserAgent(userAgentStr);
        inputDto.setPlatform(platform.getName());
    }


    public static List<String> blackAllAvailableToken(String currentUserId,
                                                      IBIamUserLoginLogClient loginLogClient,
                                                      CustomerRedisCommands customerRedisCommands) {
        //查询当前用户的可用 AuthToken
        List<BIamUserLoginLogAvailableOutputDto> outputDtoList = loginLogClient.selectAvailableToken(
                new BIamUserLoginLogQueryAvailableInputDto(Collections.singletonList(currentUserId))
        );

        Set<String> hasProcessTokenSet = new HashSet<>();
        List<String> hasProcessLoginLogList = new ArrayList<>();
        for (BIamUserLoginLogAvailableOutputDto outputDto : outputDtoList) {
            long currentTimeMillis = System.currentTimeMillis() + 10;

            if (outputDto.getAccessTokenExpire().compareTo(currentTimeMillis) > 0) {
                String accessTokenId = outputDto.getAccessTokenId();
                if (StrUtil.isBlank(customerRedisCommands.get(BIAM_AUTH_TOKEN_ID + accessTokenId))) {
                    long ttlSeconds = Math.max(1, Duration.between(Instant.now(),
                            Instant.ofEpochMilli(outputDto.getAccessTokenExpire())).getSeconds());
                    customerRedisCommands.setex(BIAM_AUTH_TOKEN_ID + accessTokenId, currentUserId, ttlSeconds);
                    hasProcessTokenSet.add(accessTokenId);
                    hasProcessLoginLogList.add(outputDto.getId());
                }
            }

            currentTimeMillis = System.currentTimeMillis() + 10;
            if (outputDto.getRefreshTokenExpire().compareTo(currentTimeMillis) > 0) {
                String refreshTokenId = outputDto.getRefreshTokenId();
                if (hasProcessTokenSet.contains(refreshTokenId)) {
                    continue;
                }

                if (StrUtil.isBlank(customerRedisCommands.get(BIAM_AUTH_TOKEN_ID + refreshTokenId))) {
                    long ttlSeconds = Math.max(1, Duration.between(Instant.now(),
                            Instant.ofEpochMilli(outputDto.getRefreshTokenExpire())).getSeconds());
                    customerRedisCommands.setex(BIAM_AUTH_TOKEN_ID + refreshTokenId, currentUserId, ttlSeconds);

                    if (!hasProcessLoginLogList.contains(refreshTokenId)) {
                        hasProcessLoginLogList.add(outputDto.getId());
                    }
                }
            }
        }
        return hasProcessLoginLogList;
    }
}
