package com.machine.starter.web.operateLog.loader.biam;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.user.IBIamUserConfigClient;
import com.machine.client.iam.biam.user.dto.input.BIamUserConfigGetInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserConfigOutputDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.biam.user.BIamUserConfigKeyEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class BIamUserConfigOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IBIamUserConfigClient> userConfigClientProvider;

    public BIamUserConfigOperateLogLoad(OperationLogLoaderRegistry registry,
                                        ObjectProvider<IBIamUserConfigClient> userConfigClientProvider) {
        super(registry);
        this.userConfigClientProvider = userConfigClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IBIamUserConfigClient userConfigClient = userConfigClientProvider.getIfAvailable();
        if (userConfigClient == null) {
            log.debug("未配置 IBIamUserConfigClient，跳过操作日志 BIAM_USER 用户配置快照加载器注册");
            return;
        }
        registry.register(ModuleEntityEnum.BIAM_USER, "save",
                entityId -> load4Save(userConfigClient, entityId));
    }

    private UserConfigSnapshot load4Save(IBIamUserConfigClient userConfigClient,
                                         Object entityId) {
        BIamUserConfigKeyEnum configKey = parseConfigKey(entityId);
        String userId = AppContextHolder.getContext().getUserId();
        if (configKey == null || StrUtil.isBlank(userId)) {
            return null;
        }

        BIamUserConfigGetInputDto inputDto = new BIamUserConfigGetInputDto();
        inputDto.setUserId(userId);
        inputDto.setConfigKey(configKey);
        BIamUserConfigOutputDto outputDto = userConfigClient.getByKey(inputDto);
        if (outputDto == null) {
            return null;
        }

        UserConfigSnapshot snapshot = new UserConfigSnapshot();
        snapshot.setConfigKey(outputDto.getConfigKey());
        snapshot.setConfigValue(flattenConfigValue(outputDto.getConfigValue()));
        return snapshot;
    }

    private BIamUserConfigKeyEnum parseConfigKey(Object entityId) {
        if (entityId == null) {
            return null;
        }
        String configKeyName = StrUtil.trim(String.valueOf(entityId));
        if (StrUtil.isBlank(configKeyName)) {
            return null;
        }
        try {
            return BIamUserConfigKeyEnum.valueOf(configKeyName);
        } catch (IllegalArgumentException error) {
            log.warn("操作日志用户配置快照：无法识别的配置键，configKey={}", configKeyName);
            return null;
        }
    }

    private Map<String, String> flattenConfigValue(String configValue) {
        if (StrUtil.isBlank(configValue)) {
            return null;
        }
        Object parsed;
        try {
            parsed = JSONUtil.parse(configValue);
        } catch (Exception error) {
            log.warn("操作日志用户配置快照：配置值非合法 JSON，按原始值对比，原因: {}", error.getMessage());
            parsed = configValue;
        }
        Map<String, String> flattenMap = new LinkedHashMap<>();
        flattenNode(parsed, null, flattenMap);
        return flattenMap;
    }

    private void flattenNode(Object node,
                             String path,
                             Map<String, String> flattenMap) {
        if (node instanceof JSONObject object) {
            for (Map.Entry<String, Object> entry : object.entrySet()) {
                flattenNode(entry.getValue(), joinPath(path, entry.getKey()), flattenMap);
            }
            return;
        }
        if (node instanceof JSONArray array) {
            for (int index = 0; index < array.size(); index++) {
                String itemPath = path == null ? "[" + index + "]" : path + "[" + index + "]";
                flattenNode(array.get(index), itemPath, flattenMap);
            }
            return;
        }
        flattenMap.put(path == null ? "value" : path, node == null ? "" : String.valueOf(node));
    }

    private String joinPath(String path,
                            String key) {
        return StrUtil.isBlank(path) ? key : path + "." + key;
    }


    @Data
    private static class UserConfigSnapshot {

        @Schema(description = "配置键")
        private BIamUserConfigKeyEnum configKey;

        @Schema(description = "配置值：字段路径 -> 值")
        private Map<String, String> configValue;

    }

}
