package com.machine.starter.web.operateLog.loader.ai;

import com.machine.client.ai.resource.model.IAiResourceProviderClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 AI 模块-厂商 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class AiProviderOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IAiResourceProviderClient> aiResourceProviderClientProvider;

    public AiProviderOperateLogLoad(OperationLogLoaderRegistry registry,
                                    ObjectProvider<IAiResourceProviderClient> aiResourceProviderClientProvider) {
        super(registry);
        this.aiResourceProviderClientProvider = aiResourceProviderClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IAiResourceProviderClient aiResourceProviderClient = aiResourceProviderClientProvider.getIfAvailable();
        if (aiResourceProviderClient != null) {
            registry.register(ModuleEntityEnum.AI_PROVIDER, "update",
                    entityId -> loadDetail(entityId, aiResourceProviderClient::detail));
            // 删除厂商：删除前加载厂商详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.AI_PROVIDER, "delete",
                    entityId -> loadDetail(entityId, aiResourceProviderClient::detail));
        } else {
            log.debug("未配置 IAiResourceProviderClient，跳过操作日志 AI_PROVIDER 快照加载器注册");
        }
    }
}
