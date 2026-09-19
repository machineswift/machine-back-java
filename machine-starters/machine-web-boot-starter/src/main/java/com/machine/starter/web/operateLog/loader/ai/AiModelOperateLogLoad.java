package com.machine.starter.web.operateLog.loader.ai;

import com.machine.client.ai.resource.model.IAiResourceModelClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 AI 模块-模型 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class AiModelOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IAiResourceModelClient> aiResourceModelClientProvider;

    public AiModelOperateLogLoad(OperationLogLoaderRegistry registry,
                                 ObjectProvider<IAiResourceModelClient> aiResourceModelClientProvider) {
        super(registry);
        this.aiResourceModelClientProvider = aiResourceModelClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IAiResourceModelClient aiResourceModelClient = aiResourceModelClientProvider.getIfAvailable();
        if (aiResourceModelClient != null) {
            registry.register(ModuleEntityEnum.AI_MODEL, "update",
                    entityId -> loadDetail(entityId, aiResourceModelClient::detail));
            // 删除模型：删除前加载模型详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.AI_MODEL, "delete",
                    entityId -> loadDetail(entityId, aiResourceModelClient::detail));
        } else {
            log.debug("未配置 IAiResourceModelClient，跳过操作日志 AI_MODEL 快照加载器注册");
        }
    }
}
